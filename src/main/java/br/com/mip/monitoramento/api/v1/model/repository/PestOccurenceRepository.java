package br.com.mip.monitoramento.api.v1.model.repository;

import br.com.mip.monitoramento.api.v1.model.entity.PestOccurence;
import br.com.mip.monitoramento.api.v1.model.exception.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class PestOccurenceRepository {

    private final List<PestOccurence> pestOccurences;

    public PestOccurenceRepository() {
        this.pestOccurences = getPestOccurences();
    }

    private List<PestOccurence> getPestOccurences() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dataset/pest_occurrences.json")) {
            return mapper.readValue(is, new TypeReference<List<PestOccurence>>() {});
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<PestOccurence> findAll() {
        return pestOccurences;
    }
}
