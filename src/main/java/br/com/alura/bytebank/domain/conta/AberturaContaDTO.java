package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

public record AberturaContaDTO(Integer numero, DadosCadastroCliente dadosCliente) {
}
