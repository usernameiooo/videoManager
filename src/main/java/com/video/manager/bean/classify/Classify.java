package com.video.manager.bean.classify;

import java.util.ArrayList;
import java.util.List;

/**分类*/
public class Classify {
    String name;
    /**@param name 该分类角度的名称*/
    public Classify(String name){
        this.name=name;
    }
    /**从这个分类角度出发，分类的可能取值*/
    List<String> classifyValues=new ArrayList<>();
    /**添加分类取值*/
    public void addClassifyValue(String value){
        if(value!=null&&!value.isEmpty()&&!classifyValues.contains(value)&&!value.contains(" "))
            classifyValues.add(value);
    }
    public List<String> getClassifyValues() {
        return classifyValues;
    }
    public boolean hasValue(String value){
        return classifyValues.contains(value);
    }
    public String getName() {
        return name;
    }
}
