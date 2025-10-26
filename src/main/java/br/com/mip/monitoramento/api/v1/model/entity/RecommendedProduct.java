package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class RecommendedProduct {

    protected Region region;
    protected Product product;
    protected Float dosage;
    protected Float cost;

}
