package br.com.mip.monitoramento.api;

import br.com.mip.monitoramento.api.v1.service.operation.ProductRecommendationFindOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		ProductRecommendationFindOperation recommendationService =
				context.getBean(ProductRecommendationFindOperation.class);

		recommendationService.generateRecommendations();
	}
}
