package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductThreshold {

    protected Pest pest;
    protected Integer minimumOccurences;
    protected Float dosePerHectare;
}
