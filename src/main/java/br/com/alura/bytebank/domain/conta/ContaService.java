package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.DAO.ContaDAO;
import br.com.alura.bytebank.DTOs.ContaDTO;
import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.validacao.excessoes.RegraDeNegocioException;

import java.math.BigDecimal;

public class ContaService {

    private final ContaDAO contaDAO;

    public ContaService() {
        contaDAO = new ContaDAO();
    }

    public String listarContasAbertas() {
        return contaDAO.listarContas();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(AberturaContaDTO dadosDaConta) {
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente);

        boolean contaExiste = contaDAO.recuperarPorNumero(conta.getNumero()).isPresent();

        if (contaExiste){
            throw new RegraDeNegocioException("Já existe outra conta aberta com o mesmo número!");
        }

        contaDAO.salvar(cliente, conta);
    }

    public void realizarSaque(Integer numeroDaConta, BigDecimal valor) {
        ContaDTO conta = buscarContaPorNumero(numeroDaConta);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do saque deve ser superior a zero!");
        }

        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new RegraDeNegocioException("Saldo insuficiente!");
        }

        contaDAO.salvarSaque(numeroDaConta, valor);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        buscarContaPorNumero(numeroDaConta);

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor do deposito deve ser superior a zero!");
        }

        contaDAO.salvarDeposito(numeroDaConta, valor);
    }

    public void encerrar(Integer numeroDaConta) {
        ContaDTO conta = buscarContaPorNumero(numeroDaConta);
        boolean existeSaldo = conta.getSaldo().compareTo(BigDecimal.ZERO) != 0;
        if (existeSaldo) {
            throw new RegraDeNegocioException("Conta não pode ser encerrada pois ainda possui saldo!");
        }

        contaDAO.deletarConta(numeroDaConta);
    }

    public ContaDTO buscarContaPorNumero(Integer numero) {
        return contaDAO.recuperarPorNumero(numero)
                .orElseThrow(()
                        -> new RegraDeNegocioException("A conta não foi encontrada"));
    }
}
