package com.l2.empacotamento.service;

import com.l2.empacotamento.dto.Caixa;
import com.l2.empacotamento.dto.Dimensoes;
import com.l2.empacotamento.dto.Pedido;
import com.l2.empacotamento.dto.ProcessarEmpacotamentoDto;
import com.l2.empacotamento.dto.Produto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class EmpacotamentoServiceTest {

    private final EmpacotamentoService empacotamentoService = new EmpacotamentoService();

    @Test
    void pedidoDeveSerEmpacotadoEmUmaUnicaCaixa() {
        Produto produto1 = new Produto();
        produto1.setId("Produto 1");
        produto1.setDimensoes(new Dimensoes(1, 1, 1));

        Produto produto2 = new Produto();
        produto2.setId("Produto 2");
        produto2.setDimensoes(new Dimensoes(1, 1, 1));

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProdutos(new ArrayList<>(List.of(produto1, produto2)));

        ProcessarEmpacotamentoDto result =
                this.empacotamentoService.processarEmpacotamento(new ProcessarEmpacotamentoDto(List.of(pedido)));

        Assertions.assertThat(result.getPedidos()).hasSize(1);

        Pedido pedidoProcessado = result.getPedidos().getFirst();
        Assertions.assertThat(pedidoProcessado.getId()).isEqualTo(1L);
        Assertions.assertThat(pedidoProcessado.getCaixas()).hasSize(1);

        Caixa caixaProcessada = pedidoProcessado.getCaixas().getFirst();
        Assertions.assertThat(caixaProcessada.getId()).isEqualTo("Caixa 1");
        Assertions.assertThat(caixaProcessada.getProdutos()).hasSize(2);
        Assertions.assertThat(caixaProcessada.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto1, produto2).map(Produto::getId).toList());
    }

    @Test
    void pedidoDeveSerEmpacotadoEm3Caixas() {
        Produto produto1 = new Produto();
        produto1.setId("Produto 1");
        produto1.setDimensoes(new Dimensoes(30, 40, 80));

        Produto produto2 = new Produto();
        produto2.setId("Produto 2");
        produto2.setDimensoes(new Dimensoes(40, 50, 80));

        Produto produto3 = new Produto();
        produto3.setId("Produto 3");
        produto3.setDimensoes(new Dimensoes(50, 60, 80));

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProdutos(new ArrayList<>(List.of(produto1, produto2, produto3)));

        ProcessarEmpacotamentoDto result =
                this.empacotamentoService.processarEmpacotamento(new ProcessarEmpacotamentoDto(List.of(pedido)));

        Assertions.assertThat(result.getPedidos()).hasSize(1);

        Pedido pedidoProcessado = result.getPedidos().getFirst();
        Assertions.assertThat(pedidoProcessado.getId()).isEqualTo(1L);
        Assertions.assertThat(pedidoProcessado.getCaixas()).hasSize(3);

        Caixa caixaProcessada1 = pedidoProcessado.getCaixas().getFirst();
        Assertions.assertThat(caixaProcessada1.getId()).isEqualTo("Caixa 3");
        Assertions.assertThat(caixaProcessada1.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto3).map(Produto::getId).toList());

        Caixa caixaProcessada2 = pedidoProcessado.getCaixas().get(1);
        Assertions.assertThat(caixaProcessada2.getId()).isEqualTo("Caixa 2");
        Assertions.assertThat(caixaProcessada2.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto2).map(Produto::getId).toList());

        Caixa caixaProcessada3 = pedidoProcessado.getCaixas().get(2);
        Assertions.assertThat(caixaProcessada3.getId()).isEqualTo("Caixa 1");
        Assertions.assertThat(caixaProcessada3.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto1).map(Produto::getId).toList());
    }

    @Test
    void pedidoDeveSerEmpacotadoEm2CaixasOnde1ContemMultiplosProdutos() {
        Produto produto1 = new Produto();
        produto1.setId("Produto 1");
        produto1.setDimensoes(new Dimensoes(1, 1, 1));

        Produto produto2 = new Produto();
        produto2.setId("Produto 2");
        produto2.setDimensoes(new Dimensoes(1, 1, 1));

        Produto produto3 = new Produto();
        produto3.setId("Produto 3");
        produto3.setDimensoes(new Dimensoes(50, 60, 80));

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProdutos(new ArrayList<>(List.of(produto1, produto2, produto3)));

        ProcessarEmpacotamentoDto result =
                this.empacotamentoService.processarEmpacotamento(new ProcessarEmpacotamentoDto(List.of(pedido)));

        Assertions.assertThat(result.getPedidos()).hasSize(1);

        Pedido pedidoProcessado = result.getPedidos().getFirst();
        Assertions.assertThat(pedidoProcessado.getId()).isEqualTo(1L);
        Assertions.assertThat(pedidoProcessado.getCaixas()).hasSize(2);

        Caixa caixaProcessada1 = pedidoProcessado.getCaixas().getFirst();
        Assertions.assertThat(caixaProcessada1.getId()).isEqualTo("Caixa 3");
        Assertions.assertThat(caixaProcessada1.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto3).map(Produto::getId).toList());

        Caixa caixaProcessada2 = pedidoProcessado.getCaixas().get(1);
        Assertions.assertThat(caixaProcessada2.getId()).isEqualTo("Caixa 1");
        Assertions.assertThat(caixaProcessada2.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto1, produto2).map(Produto::getId).toList());
    }

    @Test
    void pedidoDeveSerEmpacotadoEm1CaixaEConterUmProdutoGrandeDemais() {
        Produto produto1 = new Produto();
        produto1.setId("Produto 1");
        produto1.setDimensoes(new Dimensoes(100, 100, 100));

        Produto produto2 = new Produto();
        produto2.setId("Produto 2");
        produto2.setDimensoes(new Dimensoes(1, 1, 1));

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProdutos(new ArrayList<>(List.of(produto1, produto2)));

        ProcessarEmpacotamentoDto result =
                this.empacotamentoService.processarEmpacotamento(new ProcessarEmpacotamentoDto(List.of(pedido)));

        Assertions.assertThat(result.getPedidos()).hasSize(1);

        Pedido pedidoProcessado = result.getPedidos().getFirst();
        Assertions.assertThat(pedidoProcessado.getId()).isEqualTo(1L);
        Assertions.assertThat(pedidoProcessado.getCaixas()).hasSize(2);

        Caixa caixaProcessada1 = pedidoProcessado.getCaixas().getFirst();
        Assertions.assertThat(caixaProcessada1.getId()).isNull();
        Assertions.assertThat(caixaProcessada1.getObservacao())
                .isEqualTo("Produto não cabe em nenhuma caixa disponível.");
        Assertions.assertThat(caixaProcessada1.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto1).map(Produto::getId).toList());

        Caixa caixaProcessada2 = pedidoProcessado.getCaixas().get(1);
        Assertions.assertThat(caixaProcessada2.getId()).isEqualTo("Caixa 1");
        Assertions.assertThat(caixaProcessada2.getNomesProdutos())
                .containsExactlyInAnyOrderElementsOf(Stream.of(produto2).map(Produto::getId).toList());
    }
}
