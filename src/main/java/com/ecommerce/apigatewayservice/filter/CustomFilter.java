package com.ecommerce.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

// custom filter는 개별적으로 라우터 정보마다  yml에 등록을 해줘야함 

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config>{

	public CustomFilter() {
		super(Config.class);
	}
	
	@Override //exchange로 어떤 필터가 사용될지 지정 가능 
	public GatewayFilter apply(Config config) {
		
		// Custom Pre Filter. 예제 : JWT를 추출하여 인증을 수행할 수 있음 
		return (exchange, chain)-> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			
			//log.info("Custom Pre Filter : request id : {}",request.getId());
			System.out.println("Custom Pre Filter : request id -> {"+request.getId()+"}");
			
			//Custom Post Filter 예제 : 에러코드에 따른 에러 핸들러 
			return chain.filter(exchange).then(Mono.fromRunnable(()->{ //비동기 방식 처리mono
												//WebFlux에서 지원하는 mono data type사용가능 
				System.out.println("Custom Post Filter : response code -> {"+response.getStatusCode()+"}");
			}));

		};
	}

	public static class Config{
		//Put the configuration properties
	}
	
}
