package com.ecommerce.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

// custom filter는 개별적으로 라우터 정보마다  yml에 등록을 해줘야함 

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config>{

	public LoggingFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		// Custom Pre Filter
//		return (exchange, chain)-> {
//			ServerHttpRequest request = exchange.getRequest();
//			ServerHttpResponse response = exchange.getResponse();
//			
//			//log.info(Global Filter baseMessage : {}",config.getBaseMessage());
//			System.out.println("Logging Filter baseMessage : {"+config.getBaseMessage()+"}");
//			
//			if(config.isPreLogger()) {
//				//log.info("Global Filter start request id -> {}",request.getId());
//				System.out.println("Logging Filter Start request id -> "+request.getId());
//			}
//			
//			//Custom Post Filter 
//			return chain.filter(exchange).then(Mono.fromRunnable(()->{ //비동기 방식 처리mono
//				if(config.isPostLogger()) {
//					//log.info("Global Filter End response code -> {}",response.getStatusCode());
//					System.out.println("Logging Filter End response code -> "+response.getStatusCode());
//				}
//			}));
//
//		};
		
		GatewayFilter filter = new OrderedGatewayFilter((exchange,chain)->{
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			
			log.info("Logging Filter baseMessage : {}",config.getBaseMessage());
			
			if(config.isPreLogger()) {
				log.info("Logging Filter start request id -> {}",request.getId());
			}
			
			//Custom Post Filter 
			return chain.filter(exchange).then(Mono.fromRunnable(()->{ //비동기 방식 처리mono
				if(config.isPostLogger()) {
					log.info("Logging Filter End response code -> {}",response.getStatusCode());
				}
			}));
		}, Ordered.HIGHEST_PRECEDENCE); // HIGHEST->global 보다 우선된다. //LOWEST 는 맨 마지막 
		
		return filter;
		
	}

	@Data
	public static class Config{
		//Put the configuration properties
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
	
}
