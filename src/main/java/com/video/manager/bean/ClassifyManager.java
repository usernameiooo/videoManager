package com.video.manager.bean;

import com.video.manager.bean.classify.Classify;
import com.video.manager.util.PropertyUtil;
import com.video.manager.util.TxtHandler;

import java.util.ArrayList;
import java.util.List;

/**分类管理器
 * 管理类别和视频在各类别的可能取值*/
public class ClassifyManager {
    List<Classify> classifies=new ArrayList<>();
    private ClassifyManager(){
        loadFrom(PropertyUtil.ClassifyFile);
    }
    static final ClassifyManager instance=new ClassifyManager();
    public static ClassifyManager getInstance(){
        return instance;
    }
    /**从文件获取分类信息
     * 文件的一行：分类名 分类取值...*/
    private void loadFrom(String pathname){
        String text = TxtHandler.read(pathname);
        if(text==null)return;
        List<Classify> newClassifies=new ArrayList<>();
        String[] split = text.split("\n");
        for(String line:split){
            if(line==null||line.isEmpty())continue;
            String[] lineSpilt = line.split(" ");
            int index=0;
            Classify classify=null;
            for (String s : lineSpilt) {
                if (s == null || s.isEmpty()) continue;
                if (index == 0) classify = new Classify(lineSpilt[0]);
                else classify.addClassifyValue(s);
                index++;
            }
                if(classify!=null)newClassifies.add(classify);
        }
        this.classifies=newClassifies;
    }
    private void saveInto(String pathname){
        String text="";
        for(Classify classify:classifies){
            text+=classify.getName();
            List<String> values = classify.getClassifyValues();
            for(String value:values){
                text+=" "+value;
            }
            text+="\n";
        }
        TxtHandler.write(pathname,text);
    }
    public void save(){
        saveInto(PropertyUtil.ClassifyFile);
    }
    public List<Classify> getClassifies() {
        return classifies;
    }
    public Classify getClassify(String name){
       if(name==null||name.isEmpty())return null;
       for(Classify classify:classifies){
           if(classify.getName().equalsIgnoreCase(name))return classify;
       }
       return null;
    }
    public void addClassify(String name){
        if(name==null||name.isEmpty())return;
        name=name.trim();
        for(Classify c:classifies){
            if(c.getName().equals(name)){
                return;
            }
        }
        classifies.add(new Classify(name));
    }
    public boolean hasClassify(String name){
        if(name==null||name.isEmpty())return false;
        for(Classify c:classifies){
            if(c.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public boolean hasClassifyValue(String name,String value){
        Classify classify = getClassify(name);
        if(classify==null)return false;
        return classify.hasValue(value);
    }
    public void addClassifyValue(String name,String value){
        Classify classify = getClassify(name);
        if(classify==null)return;
        classify.addClassifyValue(value);
    }
    public void addIfNoExist(String name,String value){
        addClassify(name);
        addClassifyValue(name,value);
    }
}
