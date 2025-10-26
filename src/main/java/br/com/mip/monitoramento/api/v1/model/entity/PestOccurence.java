package br.com.mip.monitoramento.api.v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PestOccurence {
    protected UUID id;
    protected Region region;
    protected Pest pest;
    protected LocalDateTime createdAt;
    protected Location location;
}
