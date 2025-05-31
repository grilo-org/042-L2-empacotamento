package com.l2.empacotamento.service;

import com.l2.empacotamento.dto.Caixa;
import com.l2.empacotamento.dto.Pedido;
import com.l2.empacotamento.dto.ProcessarEmpacotamentoDto;
import com.l2.empacotamento.dto.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class EmpacotamentoService {

    private final int volumeMaiorCaixa =
            Caixa.VOLUME_CAIXAS_DISPONIVEIS.get("Caixa " + Caixa.VOLUME_CAIXAS_DISPONIVEIS.size());

    public ProcessarEmpacotamentoDto processarEmpacotamento(ProcessarEmpacotamentoDto processarEmpacotamentoDto) {
        List<Pedido> pedidos = processarEmpacotamentoDto.getPedidos();

        pedidos.forEach(this::processarPedido);

        return processarEmpacotamentoDto;
    }

    private void processarPedido(Pedido pedido) {
        if (pedido.getVolumePedido() <= this.volumeMaiorCaixa) {
            this.processarPedidoComUnicaCaixa(pedido);
            return;
        }

        this.ordenarProdutosPorVolume(pedido.getProdutos());
        this.filtrarProdutosGrandesDemaisParaCaixasDisponiveis(pedido);
        this.empacotarPedidoEmMultiplasCaixas(pedido);
    }

    private void processarPedidoComUnicaCaixa(Pedido pedido) {
        for (Map.Entry<String, Integer> entry : this.getTiposDeCaixasDisponiveisEmOrdemCrescente()) {
            if (entry.getValue() >= pedido.getVolumePedido()) {
                pedido.setCaixas(List.of(new Caixa(entry.getKey(), pedido.getProdutos(), null)));
                return;
            }
        }
    }

    private void ordenarProdutosPorVolume(List<Produto> produtos) {
        produtos.sort((p1, p2) -> Integer.compare(p2.getVolume(), p1.getVolume()));
    }

    private void filtrarProdutosGrandesDemaisParaCaixasDisponiveis(Pedido pedido) {
        List<Produto> produtos = pedido.getProdutos();
        List<Produto> produtosGrandesDemais = new ArrayList<>();

        for (Produto produto : produtos) {
            if (produto.getVolume() > this.volumeMaiorCaixa) {
                produtosGrandesDemais.add(produto);
                Caixa caixa = new Caixa(null, List.of(produto), "Produto não cabe em nenhuma caixa disponível.");
                pedido.addCaixa(caixa);
            }
        }

        produtos.removeAll(produtosGrandesDemais);
    }

    private void empacotarPedidoEmMultiplasCaixas(Pedido pedido) {
        List<Produto> produtos = pedido.getProdutos();
        List<Caixa> caixasAbertas = new ArrayList<>();

        for (Produto produto : produtos) {
            boolean empacotado = false;

            for (Caixa caixa : caixasAbertas) {
                if (produto.getVolume() <= caixa.getVolumeLivre()) {
                    caixa.addProduto(produto);
                    empacotado = true;
                    break;
                }
            }

            if (Boolean.FALSE.equals(empacotado)) {
                Caixa caixa = new Caixa();
                caixasAbertas.add(caixa);

                for (Map.Entry<String, Integer> entry : this.getTiposDeCaixasDisponiveisEmOrdemCrescente()) {
                    if (entry.getValue() >= produto.getVolume()) {
                        caixa.addProduto(produto);
                        caixa.setId(entry.getKey());
                        break;
                    }
                }
            }
        }

        pedido.getCaixas().addAll(caixasAbertas);
    }

    private List<Map.Entry<String, Integer>> getTiposDeCaixasDisponiveisEmOrdemCrescente() {
        List<Map.Entry<String, Integer>> tiposDeCaixasDisponiveis =
                        new ArrayList<>(Caixa.VOLUME_CAIXAS_DISPONIVEIS.entrySet().stream().toList());
        tiposDeCaixasDisponiveis.sort(Comparator.comparingInt(Map.Entry::getValue));
        return tiposDeCaixasDisponiveis;
    }
}
