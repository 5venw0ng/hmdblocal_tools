package com.adfontes.bio.service.impl;

import com.adfontes.bio.domain.DescendantNode;
import com.adfontes.bio.domain.MetaboliteBean;
import com.adfontes.bio.util.MongoDBUtil;
import com.ruoyi.common.utils.GoodMap;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Meta;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HMDBService {


    /**
     * 根据id查询
     * @param hmdbIds
     * @return
     */
    public List<MetaboliteBean> findHMDBDataById(Set<String> hmdbIds){
        List<Map> list = new ArrayList<>();
        hmdbIds.stream().forEach(id->{
            Criteria c = new Criteria().orOperator(Criteria.where( "metabolite.accession").is(id),Criteria.where("metabolite.secondary_accessions.accession").is(id));
            Map map = new MongoDBUtil().getOne(Query.query(c),Map.class,"hmdb2210" );
            if(map==null) {
                map = new HashMap();
            }
            map.put("queryId", id);
            list.add(map);
        });

        /*
        Criteria c = Criteria.where( "metabolite.accession").in(hmdbIds);
        MongoDBUtil mongoDBUtil = new MongoDBUtil();

        List<Map> list = mongoDBUtil.list(Query.query(c), Map.class, "hmdb2210");

        c = Criteria.where("metabolite.secondary_accessions.accession").in(hmdbIds);

        list.addAll(mongoDBUtil.list(Query.query(c), Map.class, "hmdb2210"));*/

        return parseHMDBData(list);
    }

    public List<MetaboliteBean> parseHMDBData(List<Map> list){
        List<MetaboliteBean> newlist = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (Map map : list) {
            MetaboliteBean mb = new MetaboliteBean();
            mb.setQueryId((String)map.get("queryId"));
            if(!map.containsKey("metabolite")){
                continue;
            }
            map = (Map)map.get("metabolite");
            GoodMap gm = GoodMap.toGoodMap(map);


            mb.setHmdbId(gm.getString("accession"));
            ids.add(mb.getHmdbId());
            mb.setChemicalFormula(gm.getString("chemical_formula"));
            mb.setName(gm.getString("name"));
            mb.setAverageMolecularWeight(gm.getString("average_molecular_weight"));
            if(gm.get("taxonomy") != null) {
                mb.setDirectParent(gm.getMap("taxonomy").getString("direct_parent"));
                mb.setKingdom(gm.getMap("taxonomy").getString("kingdom"));
                mb.setSubClass(gm.getMap("taxonomy").getString("sub_class"));
                mb.setClassName(gm.getMap("taxonomy").getString("class"));
                mb.setSuperClass(gm.getMap("taxonomy").getString("super_class"));
            }

            //处理别名
            if(gm.get("secondary_accessions") == null){}else {
                if (gm.getMap("secondary_accessions").get("accession") instanceof String) {
                    mb.setOtherHMDBId(List.of(gm.getMap("secondary_accessions").getString("accession")));
                } else {
                    mb.setOtherHMDBId(gm.getMap("secondary_accessions").getList("accession"));
                }
            }

            //处理Reference
            Map<String,List<String>> referenceMap = new HashMap<>();
            if(gm.get("diseases") == null){}else {
                List diseases = new ArrayList();
                if (gm.getMap("diseases").get("disease") instanceof Map) {
                    diseases.add(gm.getMap("diseases").getMap("disease"));
                } else {
                    diseases.addAll(gm.getMap("diseases").getList("disease"));
                }
                for (Object o : diseases) {
                    Map<String, Object> diseaseMap = (Map<String, Object>) o;

                    GoodMap disMap = GoodMap.toGoodMap(diseaseMap);
                    String referenceName = disMap.getString("name");
                    if (disMap.getMap("references").get("reference") instanceof Map) {
                        if (StringUtils.isNotEmpty(disMap.getMap("references").getMap("reference").getString("pubmed_id"))) {
                            referenceMap.put(referenceName, List.of(disMap.getMap("references").getMap("reference").getString("pubmed_id")));
                        } else {
                            referenceMap.put(referenceName, List.of(""));
                        }
                    } else {
                        referenceMap.put(referenceName, (List<String>) disMap.getMap("references").getList("reference").stream().map(it -> ((Map<String, String>) it).get("pubmed_id")).collect(Collectors.toList()));
//                        referenceMap.put(referenceName, List.of((String) ((Map)disMap.getMap("references").getList("reference").get(0)).get("pubmed_id")));
                    }
                }
            }
            mb.setReferenceMap(referenceMap);

            //现在之需要一个pubmedId
            mb.getReferenceMap().forEach((k,v)->{
                mb.getReferenceStrList().add(k+"(Pubmed:" + v.get(0) + ")");
            });

            //处理pathway
            List<String> pathWays = new ArrayList<>();
            //LinkedHashMap pathwayslist = (LinkedHashMap) ((Map)gm.get("biological_properties")).get("pathways");
            if(gm.getMap("biological_properties").get("pathways") == null){}else {
                for (Object o : gm.getMap("biological_properties").getMap("pathways").getList("pathway")) {
                    Map<String, Object> pathWay = (Map<String, Object>) o;
                    pathWays.add((String) pathWay.get("name"));
                }
            }
            mb.setPathWay(pathWays);

            //处理source和health
            getSourceAndHealthEffectDoc(gm.getMap("ontology"),mb);
            //把source的第三层提取出来
            for (DescendantNode node : mb.getSource().getNodes()) {
                if("Source,Endogenous,Biological".contains(node.getTermId())){
                    mb.getSourceStrList().add(node.getTermId());
                }
            }

            //把healthEffect第4层提取出来, get(0) = health condition
            if(mb.getHealthEffect() != null && mb.getHealthEffect().getNodes().get(0) != null){
                for (DescendantNode node : mb.getHealthEffect().getNodes().get(0).getNodes()) {
                    mb.getHealthEffectStrList().add(node.getTermId());
                }
            }


            newlist.add(mb);
        }

        return newlist;
    }

    /**
     * 解析source 和 health 文档
     */
    private void getSourceAndHealthEffectDoc(Map ontology,MetaboliteBean mb){
        GoodMap gm = GoodMap.toGoodMap(ontology);
        if(gm.get("root") == null){
            return;
        }
        List rootList = new ArrayList();
        if(gm.get("root") instanceof Map){
            rootList.add(gm.get("root"));
        }else{
            rootList.addAll(gm.getList("root"));
        }

        for (Object root : rootList) {
            GoodMap rootMap = GoodMap.toGoodMap((Map)root);
            if("physiological effect".equalsIgnoreCase(rootMap.getString("term"))){
                GoodMap heMap = rootMap.getMap("descendants").getMap("descendant");
                if("health effect".equalsIgnoreCase(heMap.getString("term"))){
                    DescendantNode dNode =  new DescendantNode("health effect",Integer.valueOf(heMap.getString("level")),null);
                    dNode.setNodes( recursionDesc( heMap.getMap("descendants"),dNode) );
                    mb.setHealthEffect(dNode);
                }
            }else if("Disposition".equalsIgnoreCase(rootMap.getString("term"))){
                for (Object o : rootMap.getMap("descendants").getList("descendant")) {
                    GoodMap sourceMap = GoodMap.toGoodMap((Map)o);
                    if("Source".equalsIgnoreCase(sourceMap.getString("term"))){
                        DescendantNode dNode =  new DescendantNode("Source",Integer.valueOf(sourceMap.getString("level")),null);
                        dNode.setNodes( recursionDesc( sourceMap.getMap("descendants"),dNode) );
                        mb.setSource(dNode);
                        break;
                    }
                }

            }
        }
    }


    public List<DescendantNode> recursionDesc(Map descendant,DescendantNode node){
        GoodMap dMap = GoodMap.toGoodMap(descendant);
        List<DescendantNode> descList = new ArrayList<>();
        if(dMap.get("descendant") instanceof Map){
            //单分支
            Map map = dMap.getMap("descendant");
            DescendantNode dNode = new DescendantNode((String)map.get("term"),Integer.valueOf((String)map.get("level")),node.getTermId());
            if(map.containsKey("descendants")) {
                dNode.setNodes(recursionDesc((Map) map.get("descendants"), dNode));
            }
            descList.add(dNode);
        }else{
            //多列表
            for (Object o : dMap.getList("descendant")) {
                Map oMap = (Map)o;
                DescendantNode dNode = new DescendantNode((String)oMap.get("term"),Integer.valueOf((String)oMap.get("level")),node.getTermId());
                if(oMap.containsKey("descendants")) {
                    dNode.setNodes(recursionDesc((Map) oMap.get("descendants"), dNode));
                }
                descList.add(dNode);
            }
        }
        return descList;
    }
}
