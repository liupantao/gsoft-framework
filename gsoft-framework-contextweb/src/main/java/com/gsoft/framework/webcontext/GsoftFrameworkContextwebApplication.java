package com.gsoft.framework.webcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gsoft.framework.context.annotation.ModuleScan;

@SpringBootApplication
@ModuleScan()
public class GsoftFrameworkContextwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsoftFrameworkContextwebApplication.class, args);
    }
}
