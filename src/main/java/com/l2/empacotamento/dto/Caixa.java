package com.l2.empacotamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Caixa {

    @JsonProperty("caixa_id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String id;
    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
    private String observacao;

    @JsonProperty("produtos")
    public List<String> getNomesProdutos() {
        return this.getProdutos().stream().map(Produto::getId).toList();
    }

    @JsonIgnore
    public int getVolumeTotal() {
        return Caixa.VOLUME_CAIXAS_DISPONIVEIS.get(this.getId());
    }

    @JsonIgnore
    public int getVolumeUsado() {
        return this.getProdutos().stream().mapToInt(Produto::getVolume).sum();
    }

    @JsonIgnore
    public int getVolumeLivre() {
        return this.getVolumeTotal() - this.getVolumeUsado();
    }

    public void addProduto(Produto produto) {
        this.getProdutos().add(produto);
    }

    public final static Map<String, Integer> VOLUME_CAIXAS_DISPONIVEIS = Map.of(
            "Caixa 1", 30 * 40 * 80,
            "Caixa 2", 40 * 50 * 80,
            "Caixa 3", 50 * 60 * 80
    );
}
