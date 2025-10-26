package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    protected Float latitude;
    protected Float longitude;
}
