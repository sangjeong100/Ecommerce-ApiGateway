package com.ecommerce.apigatewayservice.filter;

import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.ecommerce.apigatewayservice.filter.CustomFilter.Config;

import lombok.Data;
import reactor.core.publisher.Mono;

//Global filter는 공통적으로 모든 router에 사용되는 필터 
// 모든 필터의 처음과 끝에 동작 

@Component
//@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config>{

	public GlobalFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		// Custom Pre Filter
		return (exchange, chain)-> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			
			//log.info(Global Filter baseMessage : {}",config.getBaseMessage());
			System.out.println("Global Filter baseMessage : {"+config.getBaseMessage()+"}");
			
			if(config.isPreLogger()) {
				//log.info("Global Filter start request id -> {}",request.getId());
				System.out.println("Global Filter Start request id -> "+request.getId());
			}
			
			//Custom Post Filter 
			return chain.filter(exchange).then(Mono.fromRunnable(()->{ //비동기 방식 처리mono
				if(config.isPostLogger()) {
					//log.info("Global Filter End response code -> {}",response.getStatusCode());
					System.out.println("Global Filter End response code -> "+response.getStatusCode());
				}
			}));

		};
	}

	@Data
	public static class Config{
		//Put the configuration properties
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
	
}
