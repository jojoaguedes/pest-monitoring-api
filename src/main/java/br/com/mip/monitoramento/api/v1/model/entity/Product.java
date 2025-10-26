package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    protected UUID id;
    protected String name;
    protected List<ProductThreshold> thresholds;
    protected Float costPerDose;
}
