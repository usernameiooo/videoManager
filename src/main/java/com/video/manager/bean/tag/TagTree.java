package com.video.manager.bean.tag;

import java.util.ArrayList;
import java.util.List;

/**标签树，以树型结构保存所有标签*/
public class TagTree {
    TagTree parent=null;
    Tag tag;//自身节点的Tag
    List<TagTree> nodes;//下属节点
    TagTree(Tag tag){
        this.tag=tag;
    }
    public void addNode(TagTree node){
        if(node==null)return;
        if(nodes==null)nodes=new ArrayList<>();
        if(!nodes.contains(node)){
            nodes.add(node);
            node.parent=this;
        }
    }
    public boolean hasNode(TagTree node){
        if(nodes==null)return false;
        return nodes.contains(node);
    }
    /**复制节点，不复制树结构*/
    public static TagTree copyOf(TagTree node){
        return new TagTree(node.tag);
    }
    /**获取从根节点到某些节点的子树*/
    public TagTree getSubTree(List<TagTree> nodes){
        if(nodes==null)return null;
        return null;
    }
}
