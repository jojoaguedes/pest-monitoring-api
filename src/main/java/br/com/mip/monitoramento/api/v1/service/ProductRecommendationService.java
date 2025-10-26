package br.com.mip.monitoramento.api.v1.service;

import br.com.mip.monitoramento.api.v1.service.operation.ProductRecommendationFindOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductRecommendationService {
    private final ProductRecommendationFindOperation productRecommendationFindOperation;

    public void generateRecommendations() {
        productRecommendationFindOperation.generateRecommendations();
    }
}
