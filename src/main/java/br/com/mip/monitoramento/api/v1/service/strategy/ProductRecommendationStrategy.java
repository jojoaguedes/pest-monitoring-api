package br.com.mip.monitoramento.api.v1.service.strategy;

import br.com.mip.monitoramento.api.v1.model.entity.PestOccurence;
import br.com.mip.monitoramento.api.v1.model.entity.Product;
import br.com.mip.monitoramento.api.v1.model.entity.RecommendedProduct;
import br.com.mip.monitoramento.api.v1.model.entity.Region;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductRecommendationStrategy {

    boolean supports(Region region);

    List<RecommendedProduct> recommend(List<Product> products, Map<UUID, List<PestOccurence>> pestOccurrences, Region region);

}
