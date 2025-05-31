package com.l2.empacotamento.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {

    @JsonProperty("pedido_id")
    private long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Produto> produtos = new ArrayList<>();
    private List<Caixa> caixas = new ArrayList<>();

    @JsonIgnore
    public int getVolumePedido() {
        if (Objects.isNull(this.getProdutos())) {
            return 0;
        }

        return this.getProdutos().stream().map(produto -> {
            Dimensoes dimensoes = produto.getDimensoes();
            return dimensoes.getAltura() * dimensoes.getLargura() * dimensoes.getComprimento();
        }).mapToInt(Integer::intValue).sum();
    }

    public void addCaixa(Caixa caixa) {
        this.getCaixas().add(caixa);
    }
}
