package com.video.manager.bean;

import com.video.manager.bean.classify.Classify;
import com.video.manager.bean.classify.ClassifyValue;
import com.video.manager.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Video {
    String realPath;
    String type;
    String name;
    List<ClassifyValue> classifyValues;
    public Video(File file) {
         this.realPath=file.getAbsolutePath();
         this.name=file.getName();
    }
    public void addClassifyValue(ClassifyValue cv){
        if(cv==null)return;
        if(classifyValues==null)classifyValues=new ArrayList<>();
        for(ClassifyValue added:classifyValues){
            if(added.isSamClassifyWith(cv)){
                added.addValues(cv.getValue());
            }
        }
        if(!classifyValues.contains(cv))classifyValues.add(cv);
    }
    public void addClassifyValue(String name,String value){
        if(name==null||name.isEmpty())return;
        if(value==null||value.isEmpty())return;
        if(classifyValues==null)classifyValues=new ArrayList<>();
        for(ClassifyValue added:classifyValues){
           if(added.getClassifyName().equals(name)){
               added.addValue(value);
               return;
           }
        }
        ClassifyManager.getInstance().addIfNoExist(name,value);
        classifyValues.add(new ClassifyValue(name,value));
    }

    public String getVirtualPath(){
        return FileUtil.realToVirtual(realPath);
    }
    @Override
    public String toString() {
        return "Video{" +
                "realPath='" + realPath + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getRealPath() {
        return realPath;
    }

    public String getName() {
        return name;
    }

    public List<ClassifyValue> getClassifyValues() {
        return classifyValues;
    }
    public boolean hasClassifyValue(String name,String value){
        if(name==null||value==null)return false;
        if(classifyValues==null)return false;
        for(ClassifyValue cv:classifyValues){
            if(cv.getClassifyName().equals(name)){
                if(cv.getValue()==null)return false;
                for(String v:cv.getValue()){
                    if(v.equals(value))return true;
                }
            }
        }
        return false;
    }
    public void removeClassifyValue(String name,String value){
        if(name==null||value==null)return;
        if(classifyValues==null)return ;
        for(ClassifyValue cv:classifyValues){
            if(cv.getClassifyName().equals(name)){
                if(cv.getValue()==null)return;
                cv.getValue().remove(value);
                return;
            }
        }
    }
}
