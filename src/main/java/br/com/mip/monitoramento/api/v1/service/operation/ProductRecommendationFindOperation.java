package br.com.mip.monitoramento.api.v1.service.operation;

import br.com.mip.monitoramento.api.v1.model.entity.PestOccurence;
import br.com.mip.monitoramento.api.v1.model.entity.Product;
import br.com.mip.monitoramento.api.v1.model.entity.RecommendedProduct;
import br.com.mip.monitoramento.api.v1.model.entity.Region;
import br.com.mip.monitoramento.api.v1.model.repository.PestOccurenceRepository;
import br.com.mip.monitoramento.api.v1.model.repository.ProductRepository;
import br.com.mip.monitoramento.api.v1.service.strategy.ProductRecommendationStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductRecommendationFindOperation {

    private final ProductRepository productRepository;
    private final PestOccurenceRepository pestOccurrenceRepository;
    private final List<ProductRecommendationStrategy> productRecommendationStrategies;

    public void generateRecommendations() {
        List<Product> products = productRepository.findAll();
        List<PestOccurence> occurrences = pestOccurrenceRepository.findAll();
        Map<UUID, Map<UUID, List<PestOccurence>>> grouped = groupByRegionAndPest(occurrences);

        grouped.keySet().stream().map(grouped::get).forEach(pestMap -> {
            Region region = extractRegion(pestMap);
            if (region == null) return;
            ProductRecommendationStrategy strategy = productRecommendationStrategies.stream()
                    .filter(s -> s.supports(region))
                    .findFirst()
                    .orElseThrow();
            List<RecommendedProduct> recommendations = strategy.recommend(products, pestMap, region);
            showTheBestReccomendation(recommendations);
        });
    }

    private Map<UUID, Map<UUID, List<PestOccurence>>> groupByRegionAndPest(List<PestOccurence> pestOccurences) {
        return pestOccurences.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getRegion().getId(),
                        Collectors.groupingBy(o -> o.getPest().getId())
                ));
    }

    private Region extractRegion(Map<UUID, List<PestOccurence>> occurences) {
        return occurences.values().stream()
                .flatMap(List::stream)
                .findFirst()
                .map(PestOccurence::getRegion)
                .orElse(null);
    }

    private void showTheBestReccomendation(List<RecommendedProduct> recommendedProducts) {
        if (recommendedProducts.isEmpty()) {
            log.info("Nenhum produto necessário.");
        } else {
            RecommendedProduct cheaper = recommendedProducts.stream()
                    .min(Comparator.comparing(RecommendedProduct::getCost))
                    .orElseThrow();

            log.info("Region: {}", cheaper.getRegion().getName());
            log.info("- Product: {} - Cost: {} - Dosage: {}", cheaper.getProduct().getName(), cheaper.getCost(), cheaper.getDosage());
        }
    }
}
