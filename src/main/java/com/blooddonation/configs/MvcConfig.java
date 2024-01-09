package com.blooddonation.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing //entityListener 설정 홠넝화
@EnableConfigurationProperties(FileProperties.class)//불러와서 주입
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileProperties fileProperties;

    //정적 업로드 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {//정적 경로 설정 추
        registry.addResourceHandler(fileProperties.getPath() + "**")
                .addResourceLocations("file:///" + fileProperties.getPath());

    }
    //메세지 추가
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.validations", "message.errors");

        return ms;

    }

    //양식에서 _method를 가지고 get/post 이외의 메서드를 쓸 수 있다
    @Bean
    public HiddenHttpMethodFilter httpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }
}