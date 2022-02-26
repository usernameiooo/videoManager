package com.video.manager.bean;

import com.video.manager.bean.classify.Classify;
import com.video.manager.bean.classify.ClassifyValue;
import com.video.manager.util.PropertyUtil;
import com.video.manager.util.TxtHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoManager {
    ClassifyManager classifyManager=ClassifyManager.getInstance();
    List<Video> videos=new ArrayList<>();
    /**在构造函数中加载所有视频*/
    private VideoManager(){
        reload();
    }
    /**加载视频
     * 1.加载分类-由ClassifyManager加载时进行
     * 2.扫描视频
     * 3.加载视频的分类值*/
    private void reload(){
        List<Video> videos = new VideoScanner().scanUnderPath(PropertyUtil.SrcPath);
        this.videos=videos;
        assignClassifyValueForVideos(PropertyUtil.VideoClassifyFile);
    }
    /**加载视频的分类值
     * */
    private void assignClassifyValueForVideos(String pathname){
        String text = TxtHandler.read(pathname);
        if (text==null)return;
        String[] split = text.split("\\{|}");
        for(String videoStr:split){
            if(videoStr==null||videoStr.isEmpty())continue;
            String[] lines = videoStr.split("\n");
            Video video=null;
            for(String line:lines){
                if(line==null||line.isEmpty())continue;
                if(line.startsWith("video:")){
                    String realPath = line.substring("video:".length()).trim();
                    video = getVideo(realPath);
                    if(video==null)break;
                }else {
                    if(video==null)break;
                    int assign=line.indexOf("=");
                    if(assign==-1)continue;
                    String classifyName=line.substring(0,assign).trim();
                    String classifyValues=line.substring(assign+1);
                    if(classifyName.isEmpty())continue;
                    Classify classify=classifyManager.getClassify(classifyName);
                    if(classify==null)break;
                    String[] values = classifyValues.split(",");
                    if(values.length!=0){
                        List<String> vs=new ArrayList<>();
                        for(String v:values){
                            if(v!=null&&!v.isEmpty()){
                                vs.add(v.trim());
                            }
                        }
                        ClassifyValue cv=new ClassifyValue(classifyName,vs);
                        video.addClassifyValue(cv);
                    }
                }
            }
        }
    }
    private void saveClassifyValueForVideos(String pathname){
        String text="";
        for(Video video:videos){
            List<ClassifyValue> classifyValues = video.getClassifyValues();
            if(classifyValues==null)continue;
            text+="{\n";
            text+="video:"+video.getRealPath()+"\n";
            for(ClassifyValue cv:classifyValues){
                List<String> value = cv.getValue();
                if(value==null||value.size()==0)continue;
                text+=cv.getClassifyName()+"=";
                int index=0;
                for(String v:value){
                    if(index==0)text+=v;
                    else text+=","+v;
                    index++;
                }
                text+="\n";
            }
            text+="}\n";
        }
        System.out.println("save :text="+text);
        TxtHandler.write(pathname,text);
    }
    public void save(){
        classifyManager.save();
        this.saveClassifyValueForVideos(PropertyUtil.VideoClassifyFile);
    }
    static final VideoManager instance=new VideoManager();
    public static VideoManager getInstance(){
        return instance;
    }
    /**根据视频的实际路径获取视频
     * 视频的实际路径是唯一的*/
    public Video getVideo(String realPath){
        if(realPath==null||realPath.isEmpty())return null;
        for(Video video:videos){
            if(video.realPath.equals(realPath))return video;
        }
        return null;
    }
    public List<Video> getVideos() {
        return videos;
    }
    public void classifyVideo(String realPath,String classify,String value){
        Video video = getVideo(realPath);
        if(video==null)return;
        classifyManager.addIfNoExist(classify,value);
        video.addClassifyValue(classify,value);
    }
    public void removeVideoClassifyValue(String realPath,String classify,String value){
        Video video = getVideo(realPath);
        if(video==null)return;
        video.removeClassifyValue(classify,value);
    }
    /**获取满足分类条件的视频列表
     * 返回的视频，在每个要求的分类至少满足一个要求值*/
    public List<Video> getVideosInCondition(List<ClassifyValue> conditions){
        if(conditions==null||conditions.size()==0)return videos;
        List<Video> result=new ArrayList<>();
        for(Video video:videos){
            boolean added=false;
            for(ClassifyValue cv:conditions){
                List<String> vs = cv.getValue();
                if(vs==null||vs.size()==0)continue;
                for(String v:vs){
                if(video.hasClassifyValue(cv.getClassifyName(),v)){
                    result.add(video);
                    added=true;break;
                }
                }
                if(added)break;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        List<Video> videos = getInstance().getVideos();
        for(Video v:videos){
            System.out.println(v.realPath);
        }
        Video video = getInstance().getVideo("F:\\resource\\图\\R18MMD LoveMeIfYouCan Iwara.mp4");
        System.out.println(video);
        System.out.println("F:\\resource\\图\\R18MMD LoveMeIfYouCan  Iwara.mp4"
                   .equals("F:\\resource\\图\\R18MMD LoveMeIfYouCan Iwara.mp4"));
    }
}
