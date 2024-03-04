package br.com.alura.bytebank.DAO;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.conta.Conta;
import br.com.alura.bytebank.domain.conta.DadosAberturaConta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaDAO {

    private Connection connection;

    public ContaDAO(Connection connection) {
        this.connection = connection;
    }

    private PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void salvar(Cliente cliente, Conta conta){
        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email)" +
                "VALUES (?,?,?,?,?)";

        try(var preparedStatement = createPreparedStatement(sql)){
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, cliente.getNome());
            preparedStatement.setString(4,cliente.getCpf());
            preparedStatement.setString(5, cliente.getEmail());

            preparedStatement.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
