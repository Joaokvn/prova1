package dao;
import model.Pessoa;
import util.Conexao;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;



public class PessoaDAO {
        //metodo para inserir pessoa
    public void inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, email) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());

            stmt.executeUpdate();
            System.out.println("Sucesso ao inserir a pessoa");

        } catch (SQLException e) {
            System.out.println(" Erro ao inserir");
            e.printStackTrace();
        }
    }
    //metodo para buscar pelo ID
    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        Pessoa pessoa = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao buscar pessoa:");
            e.printStackTrace();
        }

        return pessoa;
    }
    //metodo para listar as pessoas
    public List<Pessoa> listarTodas() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setEmail(rs.getString("email"));
                lista.add(pessoa);
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao listar pessoas:");
            e.printStackTrace();
        }

        return lista;
    }
    //metodo para atualizar a pessoa existente
    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setInt(3, pessoa.getId());

            stmt.executeUpdate();
            System.out.println(" Pessoa atualizada com sucesso!");

        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar pessoa:");
            e.printStackTrace();
        }
    }
    public void excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println(" Pessoa excluída com sucesso!");

        } catch (SQLException e) {
            System.out.println(" Erro ao excluir pessoa:");
            e.printStackTrace();
        }
    }
}
