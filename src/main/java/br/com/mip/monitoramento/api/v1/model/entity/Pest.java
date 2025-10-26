package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pest {

    protected UUID id;
    protected String name;
}
