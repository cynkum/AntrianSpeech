package com.antrianservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class SpeechApplication {

    public static void main(String[] args) {
       SpringApplication.run(SpeechApplication.class, args);
        //ConfigurableApplicationContext context = SpringApplication.run(SpeechApplication.class, args);

    }

}
