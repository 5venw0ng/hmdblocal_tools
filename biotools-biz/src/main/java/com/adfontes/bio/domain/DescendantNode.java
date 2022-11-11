package com.adfontes.bio.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.ibatis.javassist.runtime.Desc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DescendantNode
{
    private static final long serialVersionUID = 1L;

    private String termId;

    private int level;

    private String parentId;

    private List<DescendantNode> nodes;

    public DescendantNode(String termId,int level,String parentId){
        this.termId = termId;
        this.level = level;
        this.parentId = parentId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<DescendantNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<DescendantNode> nodes) {
        this.nodes = nodes;
    }
}
