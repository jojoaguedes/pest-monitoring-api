package br.com.mip.monitoramento.api.v1.service;

import br.com.mip.monitoramento.api.v1.model.entity.*;
import br.com.mip.monitoramento.api.v1.model.repository.PestOccurenceRepository;
import br.com.mip.monitoramento.api.v1.model.repository.ProductRepository;
import br.com.mip.monitoramento.api.v1.service.operation.ProductRecommendationFindOperation;
import br.com.mip.monitoramento.api.v1.service.strategy.ProductRecommendationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRecommendationServiceTest {

    @InjectMocks
    private ProductRecommendationService recommendationService;

    @Mock
    private ProductRecommendationFindOperation productRecommendationFindOperation;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PestOccurenceRepository pestOccurenceRepository;

    @Mock
    private ProductRecommendationStrategy productRecommendationStrategy;

    @BeforeEach
    void setUp() {
        recommendationService = new ProductRecommendationService(productRecommendationFindOperation);
    }

    UUID pestId = UUID.randomUUID();
    UUID regionId = UUID.randomUUID();

    @Test
    void generateRecommendationsWithSuitableProductTest() {
        Pest pest = new Pest(pestId, "Caterpillar");
        Region region = new Region(regionId, "Region 1", null, 10.0f);

        ProductThreshold threshold = new ProductThreshold(pest, 3, 0.5f);
        Product product = new Product(UUID.randomUUID(), "Product 1", Collections.singletonList(threshold), 100.0f);

        PestOccurence occurrence1 = new PestOccurence(UUID.randomUUID(), region, pest, null, null);
        PestOccurence occurrence2 = new PestOccurence(UUID.randomUUID(), region, pest, null, null);
        PestOccurence occurrence3 = new PestOccurence(UUID.randomUUID(), region, pest, null, null);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(pestOccurenceRepository.findAll()).thenReturn(List.of(occurrence1, occurrence2, occurrence3));

        recommendationService.generateRecommendations();

        verify(productRecommendationFindOperation, atLeastOnce()).generateRecommendations();
        verify(productRepository, atLeastOnce()).findAll();
        verify(pestOccurenceRepository, atLeastOnce()).findAll();
        verify(productRecommendationStrategy, atLeastOnce()).recommend(any(), any(), any());
    }

    @Test
    void generateRecommendationsWithoutSuitableProductTest() {
        Pest pest = new Pest(pestId, "Aphid");
        Region region = new Region(regionId, "Region 2", null, 5.0f);

        ProductThreshold threshold = new ProductThreshold(pest, 5, 1.0f);
        Product product = new Product(UUID.randomUUID(), "Product 2", Collections.singletonList(threshold), 50.0f);

        PestOccurence occurrence1 = new PestOccurence(UUID.randomUUID(), region, pest, null, null);
        PestOccurence occurrence2 = new PestOccurence(UUID.randomUUID(), region, pest, null, null);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(pestOccurenceRepository.findAll()).thenReturn(List.of(occurrence1, occurrence2));

        recommendationService.generateRecommendations();
    }

    @Test
    void generateRecommendationsWithMoreThanOneSuitableProductTest() {
        Pest pest = new Pest(pestId, "Broca");
        Region region = new Region(regionId, "Talhão 3", null, 20.0f);

        ProductThreshold threshold = new ProductThreshold(pest, 1, 0.5f);
        Product expensive = new Product(UUID.randomUUID(), "Expensive Product", Collections.singletonList(threshold), 200.0f);
        Product cheap = new Product(UUID.randomUUID(), "Cheap Product", Collections.singletonList(threshold), 50.0f);

        PestOccurence occurrence = new PestOccurence(UUID.randomUUID(), region, pest, null, null);

        when(productRepository.findAll()).thenReturn(List.of(expensive, cheap));
        when(pestOccurenceRepository.findAll()).thenReturn(Collections.singletonList(occurrence));

        recommendationService.generateRecommendations();
    }

    @Test
    void generateRecommendationsInMultipleRegionsTest() {
        UUID regionId1 = UUID.randomUUID();
        UUID regionId2 = UUID.randomUUID();

        Pest pest = new Pest(pestId, "Ant");
        Region region1 = new Region(regionId1, "Region A", null, 10.0f);
        Region region2 = new Region(regionId2, "Region B", null, 15.0f);

        ProductThreshold threshold = new ProductThreshold(pest, 2, 0.3f);
        Product product = new Product(UUID.randomUUID(), "Multi Product", Collections.singletonList(threshold), 120.0f);

        PestOccurence o1 = new PestOccurence(UUID.randomUUID(), region1, pest, null, null);
        PestOccurence o2 = new PestOccurence(UUID.randomUUID(), region1, pest, null, null);
        PestOccurence o3 = new PestOccurence(UUID.randomUUID(), region2, pest, null, null);
        PestOccurence o4 = new PestOccurence(UUID.randomUUID(), region2, pest, null, null);
        PestOccurence o5 = new PestOccurence(UUID.randomUUID(), region2, pest, null, null);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(pestOccurenceRepository.findAll()).thenReturn(List.of(o1, o2, o3, o4, o5));

        recommendationService.generateRecommendations();
    }

}
