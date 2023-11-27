package com.ecommerce.apigatewayservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiGatewayServiceApplication.class, args);
	}

	// for httptrace
	@Bean
	public HttpTraceRepository httpTraceRepository() {
		return new HttpTraceRepository() {
			
			@Override
			public List<HttpTrace> findAll() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void add(HttpTrace trace) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
