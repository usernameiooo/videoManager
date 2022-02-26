package com.video.manager.bean.classify;

import com.video.manager.bean.ClassifyManager;

import java.util.ArrayList;
import java.util.List;

/**视频在分类上的取值*/
public class ClassifyValue {
    String name;
    List<String> classifyValue;
    public ClassifyValue(String name){
        this.name=name;
    }
    public ClassifyValue(String name,List<String> values){
        this.name=name;
        this.classifyValue=values;
    }
    public ClassifyValue(String name,String value){
        this.name=name;
        classifyValue=new ArrayList<>();
        classifyValue.add(value);
    }

    public List<String> getValue() {
        return classifyValue;
    }

    public String getClassifyName() {
        return name;
    }
    public boolean isSamClassifyWith(ClassifyValue cv){
        return this.name.equals(cv.getClassifyName());
    }
    public void addValue(String value){
        if(value==null||value.isEmpty())return;
        if(classifyValue==null)classifyValue=new ArrayList<>();
        classifyValue.add(value);
    }
    public void addValues(List<String> value){
        for(String v:value){
            addValue(v);
        }
    }
}
