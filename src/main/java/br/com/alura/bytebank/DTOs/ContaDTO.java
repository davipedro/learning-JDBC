package br.com.alura.bytebank.DTOs;

import java.math.BigDecimal;

public class ContaDTO {
    private Integer numero;
    private BigDecimal saldo;
    private String cliente_nome;
    private String cliente_cpf;
    private String cliente_email;
    private boolean estaAtiva;

    public ContaDTO(Integer numero, BigDecimal saldo, String cliente_nome,
                    String cliente_cpf, String cliente_email, boolean estaAtiva) {
        this.numero = numero;
        this.saldo = saldo;
        this.cliente_nome = cliente_nome;
        this.cliente_cpf = cliente_cpf;
        this.cliente_email = cliente_email;
        this.estaAtiva = estaAtiva;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getCliente_nome() {
        return cliente_nome;
    }

    public void setCliente_nome(String cliente_nome) {
        this.cliente_nome = cliente_nome;
    }

    public String getCliente_cpf() {
        return cliente_cpf;
    }

    public void setCliente_cpf(String cliente_cpf) {
        this.cliente_cpf = cliente_cpf;
    }

    public String getCliente_email() {
        return cliente_email;
    }

    public void setCliente_email(String cliente_email) {
        this.cliente_email = cliente_email;
    }

    public boolean isEstaAtiva() {
        return estaAtiva;
    }

    public void setEstaAtiva(boolean estaAtiva) {
        this.estaAtiva = estaAtiva;
    }

    @Override
    public String toString() {
        return "{\n" +
                "numero=" + numero + "\n" +
                "saldo=" + saldo + "\n" +
                "cliente_nome='" + cliente_nome + "\n" +
                "cliente_cpf='" + cliente_cpf + "\n" +
                "cliente_email='" + cliente_email + "\n" +
                '}';
    }
}
