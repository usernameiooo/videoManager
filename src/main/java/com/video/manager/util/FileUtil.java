package com.video.manager.util;

public class FileUtil {
    static String realDir=PropertyUtil.getProperty("src-path");
    static String virtualDir=PropertyUtil.getProperty("map-path");
    public static String realToVirtual(String realPtah){
        if(realPtah==null||realPtah.isEmpty())return null;
        return realPtah.replace(realDir,virtualDir);
    }

    public static void main(String[] args) {
        System.out.println(realDir);
        System.out.println(virtualDir);
        System.out.println(realToVirtual("F:\\resource\\MMD\\精品MMD2\\短裙Haku.mp4"));
    }
}
