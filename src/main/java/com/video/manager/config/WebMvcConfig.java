package com.video.manager.config;


import com.video.manager.util.PropertyUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //需要的文件路径映射
       // registry.addResourceHandler("/local/**").addResourceLocations("file:F:/");
        registry.addResourceHandler(PropertyUtil.MapPath.replaceAll("\\\\","/")+"**").addResourceLocations("file:"+PropertyUtil.SrcPath.replaceAll("\\\\","/"));

        //项目内静态文件映射，仍然可以访问到静态资源
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }
}
