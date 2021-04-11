package com.example.alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//BUILD IMAGE ON DOCKER:
//docker build -r /alura/forum .
//docker image list - > Lista as imagens do docker
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
public class AluraApplication {
    public static void main(String[] args) {
        SpringApplication.run(AluraApplication.class, args);
    }

}
