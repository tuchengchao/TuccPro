package com.tcc.dubbo.provider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("dubbo服务启动");
		SpringApplication.run(Application.class, args);
	}
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("dubbo服务运行");
	}
}
