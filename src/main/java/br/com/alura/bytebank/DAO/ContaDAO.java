package br.com.alura.bytebank.DAO;

import br.com.alura.bytebank.DTOs.ContasDTO;
import br.com.alura.bytebank.domain.RegraDeNegocioException;
import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.conta.Conta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private final Connection connection;

    public ContaDAO() {
        this.connection = ConnectionFactoryDB.getConnection();
    }

    public void salvar(Cliente cliente, Conta conta){
        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email)" +
                "VALUES (?,?,?,?,?)";

        try(var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, cliente.getNome());
            preparedStatement.setString(4,cliente.getCpf());
            preparedStatement.setString(5, cliente.getEmail());

            preparedStatement.execute();
        }
        catch (SQLException e){
            throw new RuntimeException("Não foi possível salvar a conta", e);
        }
    }

    public void salvarDeposito(Integer numeroDaConta ,BigDecimal valorDeposito){
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE numero = ?";

        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1,valorDeposito);
            preparedStatement.setInt(2,numeroDaConta);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void salvarSaque(Integer numeroDaConta ,BigDecimal valorSaque){
        String sql = "UPDATE conta SET saldo = saldo - ? WHERE numero = ?";

        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1,valorSaque);
            preparedStatement.setInt(2,numeroDaConta);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Não foi possível efetuar o saque", e);
        }
    }

    public void deletarConta(Integer numeroDaConta){
        String sql = "DELETE FROM conta WHERE numero = ?";

        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,numeroDaConta);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Não foi possível deletar a conta", e);
        }
    }

    public String listarContas(){
        String sql = "SELECT * FROM conta";

        Set<ContasDTO> contas = new HashSet<>();

        try (var preparedStatement = connection.prepareStatement(sql)){
            preencherContaDTO(contas, preparedStatement);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return contas.toString();
    }

    public Conta buscarPorNumero(Integer num){
        String sql = "SELECT * FROM conta WHERE numero = ?";

        Set<ContasDTO> contas = new HashSet<>();

        try (var preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, num);

            preencherContaDTO(contas, preparedStatement);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return contas.stream().findFirst().map(contaDTO -> new Conta(
                contaDTO.getNumero(),
                new Cliente(
                        contaDTO.getCliente_nome(),
                        contaDTO.getCliente_cpf(),
                        contaDTO.getCliente_email()
                )
        )).orElseThrow(() -> new RegraDeNegocioException("A conta não foi encontrada"));
    }

    private void preencherContaDTO(Set<ContasDTO> contas, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet  = preparedStatement.executeQuery()){
            while (resultSet.next()){
                Integer numero = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);

                contas.add(new ContasDTO(numero, saldo, nome, cpf, email));
            }
        }
    }
}
