package br.com.mip.monitoramento.api.v1.model.repository;

import br.com.mip.monitoramento.api.v1.model.entity.Product;
import br.com.mip.monitoramento.api.v1.model.exception.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class ProductRepository {

    private final List<Product> products;

    public ProductRepository() {
        this.products = getProducts();
    }

    private List<Product> getProducts() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dataset/products.json")) {
            return mapper.readValue(is, new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<Product> findAll() {
        return products;
    }
}
