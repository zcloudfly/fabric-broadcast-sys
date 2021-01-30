package com.broadcast.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

//@ServletComponentScan
//@RestController
@SpringBootApplication
//@EnableAutoConfiguration
public class BroadcastApplicationStart  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BroadcastApplicationStart.class,args);
    }
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(BroadcastApplicationStart.class);
    }



    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertyPlaceholder() throws IOException {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setOrder(2);
        configurer.setFileEncoding("UTF-8");
        configurer.setLocations(new PathMatchingResourcePatternResolver().getResources("config/*.properties"));
        return configurer;
    }*/
}
