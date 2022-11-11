package com.adfontes.bio.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MetaboliteBean
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "Query ID")
    private String queryId;
    /** 服务器ID */
    @Excel(name = "HMDB ID")
    private String hmdbId;

    /** 服务器别名 */
    private List<String> otherHMDBId;

    @Excel(name = "Name",width = 20)
    private String name;

    @Excel(name = "ChemicalFormula")
    private String chemicalFormula;

    @Excel(name = "AverageMolecularWeight")
    private String averageMolecularWeight;

    @Excel(name = "DirectParent",width = 35)
    private String directParent;

    @Excel(name = "Kingdom",width = 35)
    private String kingdom;

    @Excel(name = "SuperClass",width = 35)
    private String superClass;

    @Excel(name = "ClassName",width = 35)
    private String className;

    @Excel(name = "SubClass",width = 35)
    private String subClass;

    private DescendantNode source;
    @Excel(name = "Source",width = 35)
    private List<String> sourceStrList = new ArrayList<>();

    private DescendantNode healthEffect;
    @Excel(name = "Health Effect",width = 35)
    private List<String> healthEffectStrList = new ArrayList<>();


    private Map<String,List<String>> referenceMap = new HashMap<>();
    @Excel(name = "Reference",width = 60)
    private List<String> referenceStrList = new ArrayList<>();

    @Excel(name = "PathWay",height = 50,width = 35)
    private List<String> pathWay;

    public List<String> getSourceStrList() {
        return sourceStrList;
    }

    public void setSourceStrList(List<String> sourceStrList) {
        this.sourceStrList = sourceStrList;
    }

    public List<String> getHealthEffectStrList() {
        return healthEffectStrList;
    }

    public void setHealthEffectStrList(List<String> healthEffectStrList) {
        this.healthEffectStrList = healthEffectStrList;
    }

    public String getHmdbId() {
        return hmdbId;
    }

    public void setHmdbId(String hmdbId) {
        this.hmdbId = hmdbId;
    }

    public List<String> getOtherHMDBId() {
        return otherHMDBId;
    }

    public void setOtherHMDBId(List<String> otherHMDBId) {
        this.otherHMDBId = otherHMDBId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getAverageMolecularWeight() {
        return averageMolecularWeight;
    }

    public void setAverageMolecularWeight(String averageMolecularWeight) {
        this.averageMolecularWeight = averageMolecularWeight;
    }

    public String getDirectParent() {
        return directParent;
    }

    public void setDirectParent(String directParent) {
        this.directParent = directParent;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public Map<String, List<String>> getReferenceMap() {
        return referenceMap;
    }

    public void setReferenceMap(Map<String, List<String>> referenceMap) {
        this.referenceMap = referenceMap;
    }

    public List<String> getPathWay() {
        return pathWay;
    }

    public void setPathWay(List<String> pathWay) {
        this.pathWay = pathWay;
    }

    public DescendantNode getSource() {
        return source;
    }

    public void setSource(DescendantNode source) {
        this.source = source;
    }

    public DescendantNode getHealthEffect() {
        return healthEffect;
    }

    public void setHealthEffect(DescendantNode healthEffect) {
        this.healthEffect = healthEffect;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public List<String> getReferenceStrList() {
        return referenceStrList;
    }

    public void setReferenceStrList(List<String> referenceStrList) {
        this.referenceStrList = referenceStrList;
    }
}
