package com.video.manager.controller;
import com.video.manager.bean.ClassifyManager;
import com.video.manager.bean.Video;
import com.video.manager.bean.VideoManager;
import com.video.manager.bean.classify.Classify;
import com.video.manager.bean.classify.ClassifyValue;
import com.video.manager.util.PropertyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class VideoController {
    VideoManager videoManager=VideoManager.getInstance();
    ClassifyManager classifyManager=ClassifyManager.getInstance();
    private Object getPara(HttpServletRequest request,String name){
        return getPara(request, name,false);
    }
    private Object getPara(HttpServletRequest request,String name,boolean deleteOthersOnFindingNew){
        HttpSession session = request.getSession();
        Object value = request.getParameter(name);
        if(value!=null){
            if(deleteOthersOnFindingNew){
                //清空session
                Enumeration<String> e=session.getAttributeNames();
                while(e.hasMoreElements()){
                    String sessionName=e.nextElement();
                    session.removeAttribute(sessionName); }
            }
            session.setAttribute(name,value);
            return value;
        }
        value = session.getAttribute(name);
        return value;
    }
    private int castToInt(Object obj,int def){
        if(obj==null)return def;
        try{
        if(obj instanceof Integer){
            return (int) obj;
        }else if(obj instanceof String){
            return Integer.parseInt((String) obj);
        }}catch (Exception e){}
        return def;
    }
    private int getPageCount(int items,int itemPerPage){
        if(items%itemPerPage==0)return items/itemPerPage;
        return items/itemPerPage+1;
    }
    /**从字符串获取查询条件
     * {c1 v1 v2...}...*/
    private List<ClassifyValue> getCondition(String str){
        if(str==null||str.isEmpty())return null;
        String[] split = str.split("\\{|}");
        if(split.length == 0)return null;
        List<ClassifyValue> condition=new ArrayList<>();
        //cvStr : 'c1 v1 v2...'
        for(String cvStr:split){
           if(cvStr==null||cvStr.isEmpty())continue;
            String[] ss = cvStr.split(" ");
            if(ss.length<=1)continue;
            String name=null;
            List<String> values=new ArrayList<>();
            for(String s:ss){
                if(s==null||s.isEmpty())continue;
                if(name==null)name=s;
                else values.add(s);
            }
            ClassifyValue cv=new ClassifyValue(name,values);
            condition.add(cv);
        }
        return condition;
    }
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/index");
        mav.addObject("path",PropertyUtil.SrcPath);
        String searchCondition = (String) getPara(request, "searchCondition",true);
        mav.addObject("searchCondition",searchCondition);
        List<Video> videos = videoManager.getVideosInCondition(getCondition(searchCondition));
        int page=0;
        page=castToInt(getPara(request,"page"),0);
        System.out.println("page="+page);
        List<Video> showVideos=new ArrayList<>();
        for(int i=page*10;i<(page+1)*10&&i<videos.size();i++){
            showVideos.add(videos.get(i));
        }
        mav.addObject("page",page);
        mav.addObject("maxPage",getPageCount(videos.size(),10));
        mav.addObject("videos",showVideos);
        List<Classify> classifies = ClassifyManager.getInstance().getClassifies();
        mav.addObject("classifies", ClassifyManager.getInstance().getClassifies());
        String realPath=null;
        try {
            realPath = (String) getPara(request,"realPath");
        }catch (Exception e){}
        if(realPath==null)return mav;
        System.out.println(realPath);
        Video video = VideoManager.getInstance().getVideo(realPath);
        mav.addObject("video",video);

        return mav;
    }
    @RequestMapping("/list")
    public ModelAndView listVideos(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/list");
        String path= PropertyUtil.SrcPath;
        List<Video> videos = videoManager.getVideos();
        mav.addObject("path",path);
        mav.addObject("videos",videos);

        return mav;
    }
    @RequestMapping("/video")
    public ModelAndView showVideo(HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/video");
        String realPath=null;
        try {
            realPath = (String) request.getParameter("realPath");
        }catch (Exception e){};
        System.out.println(realPath);
        if(realPath==null)return mav;
        Video video = VideoManager.getInstance().getVideo(realPath);
        mav.addObject("video",video);
        List<Classify> classifies = ClassifyManager.getInstance().getClassifies();
        mav.addObject("classifies", ClassifyManager.getInstance().getClassifies());

        return mav;
    }
    @RequestMapping("/classify")
    public ModelAndView showClassifies(){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/classify");
        List<Classify> classifies = ClassifyManager.getInstance().getClassifies();
        System.out.println(classifies.size());
        if(classifies.size()>0){
            System.out.println(classifies.get(0).getName());
        }
        mav.addObject("classifies", ClassifyManager.getInstance().getClassifies());
        return mav;
    }
    @ResponseBody
    @RequestMapping("/addClassify")
    public String addClassify(@RequestBody Map<String,Object> map){
        String name= (String) map.get("name");
        System.out.println("add classify : "+name);
        if(name!=null){
           classifyManager.addClassify(name);
           classifyManager.save();
        }
        return "OK";
    }
    @ResponseBody
    @RequestMapping("/addClassifyValue")
    public String addClassifyValue(@RequestBody Map<String,Object> map){
        String name= (String) map.get("name");
        String value= (String) map.get("value");
        System.out.println("add classify value: "+value +" to classify : "+name);
        if(name!=null&&value!=null){
            classifyManager.addClassifyValue(name,value);
            String currVideo=(String) map.get("currVideo");
            if(currVideo!=null){
                videoManager.classifyVideo(currVideo,name,value);
            }
            videoManager.save();
        }
        return "OK";
    }
    @ResponseBody
    @RequestMapping("classifyVideo")
    public String classifyVideo(@RequestBody Map<String,Object> map){
        String realPath = (String) map.get("videoPath");
        String classify = (String) map.get("classify");
        String value = (String) map.get("value");
        System.out.println(realPath+" "+classify+"="+value);
        videoManager.classifyVideo(realPath,classify,value);
         videoManager.save();
        return "OK";
    }
    @ResponseBody
    @RequestMapping("unClassifyVideo")
    public String unClassifyVideo(@RequestBody Map<String,Object> map){
        String realPath = (String) map.get("videoPath");
        String classify = (String) map.get("classify");
        String value = (String) map.get("value");
        System.out.println(realPath+" remove "+classify+"="+value);
        videoManager.removeVideoClassifyValue(realPath,classify,value);
        videoManager.save();
        return "OK";
    }
    //TODO 视频列表添加分页，减少加载时间 Y
    //TODO 把正在查看的视频分页加入Session Y
    //TODO 通过分类条件筛选视频 Y
    //TODO 将分类条件加入Session Y
    //TODO 调整css样式
    //TODO 可以通过按钮隐藏/显示搜索区域，有视频时默认隐藏
}
