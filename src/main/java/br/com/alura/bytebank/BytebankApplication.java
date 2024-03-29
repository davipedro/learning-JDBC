package br.com.alura.bytebank;

import br.com.alura.bytebank.DTOs.ContaDTO;
import br.com.alura.bytebank.validacao.excessoes.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;
import br.com.alura.bytebank.domain.conta.ContaService;
import br.com.alura.bytebank.domain.conta.AberturaContaDTO;

import java.math.BigDecimal;
import java.util.Scanner;

public class BytebankApplication {

    private static final ContaService service = new ContaService();
    private static final Scanner teclado = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        int opcao = exibirMenu();
        while (opcao != 0) {
            try {
                switch (opcao) {
                    case 1:
                        listarContas();
                        break;
                    case 2:
                        abrirConta();
                        break;
                    case 3:
                        encerrarConta();
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        realizarSaque();
                        break;
                    case 6:
                        realizarDeposito();
                        break;
                    case 7:
                        buscarPorNumero();
                        break;
                    case 8:
                        transferirDinheiro();
                        break;
                }
            } catch (RegraDeNegocioException e) {
                System.out.println("Erro: " +e.getMessage());
                System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu");
                teclado.next();
            }
            opcao = exibirMenu();
        }

        System.out.println("Finalizando a aplicação.");
    }

    private static int exibirMenu() {
        System.out.println("""
                BYTEBANK - ESCOLHA UMA OPÇÃO:
                1 - Listar contas abertas
                2 - Abertura de conta
                3 - Encerramento de conta
                4 - Consultar saldo de uma conta
                5 - Realizar saque em uma conta
                6 - Realizar depósito em uma conta
                7 - Buscar por Numero;
                8 - Transferir dinheiro
                0 - Sair
                """);
        return teclado.nextInt();
    }

    private static void transferirDinheiro() {
        System.out.println("Digite o número de origem");
        Integer numeroOrigem = teclado.nextInt();
        System.out.println("Digite o número conta de destino");
        Integer numeroDestino = teclado.nextInt();
        System.out.println("Digite o valor a ser transferido:");
        BigDecimal valor = teclado.nextBigDecimal();

        service.realizarTransferencia
                (numeroOrigem, numeroDestino, valor);
        System.out.printf("Transferência no valor de %s efetuada de com sucesso", valor);
        System.out.println();
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void listarContas() {
        System.out.println("Contas cadastradas:");
        System.out.println(service.listarContasAbertas());

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void buscarPorNumero(){
        System.out.println("Digite o número da conta:");
        Integer numero = teclado.nextInt();
        ContaDTO conta = service.buscarContaPorNumero(numero);
        System.out.println(conta);
    }

    private static void abrirConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o nome do cliente:");
        var nome = teclado.next();

        System.out.println("Digite o cpf do cliente:");
        var cpf = teclado.next();

        System.out.println("Digite o email do cliente:");
        var email = teclado.next();

        service.abrir(new AberturaContaDTO(numeroDaConta, new DadosCadastroCliente(nome, cpf, email)));

        System.out.println("Conta aberta com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void encerrarConta() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        service.encerrarLogico(numeroDaConta);

        System.out.println("Conta encerrada com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void consultarSaldo() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();
        var saldo = service.consultarSaldo(numeroDaConta);
        System.out.println("Saldo da conta: " +saldo);

        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarSaque() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o valor do saque:");
        var valor = teclado.nextBigDecimal();

        service.realizarSaque(numeroDaConta, valor);
        System.out.printf("Saque de %s realizado com sucesso!", valor);
        System.out.println();
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }

    private static void realizarDeposito() {
        System.out.println("Digite o número da conta:");
        var numeroDaConta = teclado.nextInt();

        System.out.println("Digite o valor do depósito:");
        var valor = teclado.nextBigDecimal();

        service.realizarDeposito(numeroDaConta, valor);

        System.out.println("Depósito realizado com sucesso!");
        System.out.println("Pressione qualquer tecla e de ENTER para voltar ao menu principal");
        teclado.next();
    }
}
