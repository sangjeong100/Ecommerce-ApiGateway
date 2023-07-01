package com.ecommerce.apigatewayservice.filter;

import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.ecommerce.apigatewayservice.filter.CustomFilter.Config;

import reactor.core.publisher.Mono;

// custom filter는 개별적으로 라우터 정보마다  yml에 등록을 해줘야함 

@Component
//@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config>{

	public CustomFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		// Custom Pre Filter
		return (exchange, chain)-> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			
			//log.info("Custom Pre Filter : request id : {}",request.getId());
			System.out.println("Custom Pre Filter : request id -> {"+request.getId()+"}");
			
			//Custom Post Filter 
			return chain.filter(exchange).then(Mono.fromRunnable(()->{ //비동기 방식 처리mono
				System.out.println("Custom Post Filter : response code -> {"+response.getStatusCode()+"}");
			}));

		};
	}

	public static class Config{
		//Put the configuration properties
	}
	
}
