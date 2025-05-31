package com.l2.empacotamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {

    @JsonProperty("produto_id")
    private String id;
    private Dimensoes dimensoes;

    public int getVolume() {
        Dimensoes dimensoes = this.getDimensoes();
        return dimensoes.getAltura() * dimensoes.getLargura() * dimensoes.getComprimento();
    }
}
