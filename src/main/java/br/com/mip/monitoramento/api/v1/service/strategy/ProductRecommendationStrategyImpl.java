package br.com.mip.monitoramento.api.v1.service.strategy;

import br.com.mip.monitoramento.api.v1.model.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductRecommendationStrategyImpl implements ProductRecommendationStrategy {

    @Override
    public boolean supports(Region region) {
        return true;
    }

    @Override
    public List<RecommendedProduct> recommend(List<Product> products, Map<UUID, List<PestOccurence>> pestOccurrences, Region region) {
        List<RecommendedProduct> recommendations = new ArrayList<>();

        for (Product product : products) {
            float dosage = 0f;
            boolean apply = false;

            for (ProductThreshold threshold : product.getThresholds()) {
                UUID pestId = threshold.getPest().getId();
                int occurrences = pestOccurrences.getOrDefault(pestId, List.of()).size();

                if (occurrences >= threshold.getMinimumOccurences()) {
                    float dose = threshold.getDosePerHectare() * region.getSize();
                    dosage += dose;
                    apply = true;
                }
            }

            if (apply) {
                float cost = dosage * product.getCostPerDose();
                recommendations.add(new RecommendedProduct(region, product, dosage, cost));
            }
        }

        return recommendations;
    }
}
