package com.video.manager.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VideoScanner {
    /**待扫描的目录*/
    Queue<File> scanQueen=new LinkedList<>();
    public List<Video> scanUnderPath(String path){
        if(path==null||path.isEmpty())return null;
        File dir=new File(path);
        if(!dir.exists()||!dir.isDirectory()){
            return null;
        }
        scanQueen.offer(dir);
        File scanning;
        List<Video> list=new ArrayList<>();
        while (!scanQueen.isEmpty()){
            scanning=scanQueen.poll();
            List<Video> videos = scan(scanning);
            if(videos!=null)list.addAll(videos);
        }
        System.out.println(list.size());
        return list;
    }
    private List<Video> scan(File dir){
        File[] files = dir.listFiles();
        if(files==null)return null;
        List<Video> list=new ArrayList<>();
        for(File file:files){
            if(isVideo(file))list.add(new Video(file));
            else if(file.isDirectory()){
                scanQueen.add(file);
            }
        }
        return list;
    }
    static final String[] videoFix={"mp4","rmvb","mov", "wmv","avi","mkv"};
    private boolean isVideo(File file){
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        if(dot==-1||dot==name.length())return false;
        String fileFix=name.substring(dot+1);
        for(String fix:videoFix){
            if(fileFix.equalsIgnoreCase(fix))return true;
        }
        return false;
    }

}
