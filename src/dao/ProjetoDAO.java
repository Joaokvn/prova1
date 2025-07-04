package dao;

import model.Projeto;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    //metodo para inserir projeto
    public void inserir(Projeto projeto) {
        String checkFunc = "SELECT 1 FROM funcionario WHERE id = ?";
        String sql = "INSERT INTO projeto (nome, descricao, id_funcionario) VALUES (?, ?, ?)";
        //conexao com o banco
        try (Connection conn = Conexao.conectar();
             PreparedStatement psCheck = conn.prepareStatement(checkFunc)) {

            psCheck.setInt(1, projeto.getId_funcionario());
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                System.out.println(" Erro: Funcionário com ID " + projeto.getId_funcionario() + " não encontrado.");
                return;
            }
            // insere os funcionarios
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, projeto.getNome());
                stmt.setString(2, projeto.getDescricao());
                stmt.setInt(3, projeto.getId_funcionario());

                stmt.executeUpdate();
                ResultSet keys = stmt.getGeneratedKeys();
                //verifica se deu para inserir com sucesso
                if (keys.next()) {
                    projeto.setId(keys.getInt(1));
                }
                System.out.println(" Projeto inserido com sucesso! ID: " + projeto.getId());
            }
            // caso der erro ele avisa
        } catch (SQLException e) {
            System.out.println(" Erro ao inserir projeto:");
            e.printStackTrace();
        }
    }

    // metodo para buscar o projeto com o id
    public Projeto buscarPorId(int id) {
        String sql = "SELECT * FROM projeto WHERE id = ?";
        Projeto projeto = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //insere o id
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setId_funcionario(rs.getInt("id_funcionario"));
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao buscar projeto:");
            e.printStackTrace();
        }

        return projeto;
    }

        //metodo para listar todo o projeto
    public List<Projeto> listarTodos() {
        String sql = "SELECT * FROM projeto";
        List<Projeto> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setId_funcionario(rs.getInt("id_funcionario"));
                lista.add(projeto);
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao listar projetos:");
            e.printStackTrace();
        }

        return lista;
    }

    // metodo para atualizar um projeto que ja existe
    public void atualizar(Projeto projeto) {
        String sql = "UPDATE projeto SET nome = ?, descricao = ?, id_funcionario = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setInt(3, projeto.getId_funcionario());
            stmt.setInt(4, projeto.getId());
            stmt.executeUpdate();
            System.out.println(" Projeto atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar projeto:");
            e.printStackTrace();
        }
    }

    //metodo para excluir um projeto com o ID
    public void excluir(int id) {
        String sql = "DELETE FROM projeto WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("    Projeto excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println(" Erro ao excluir projeto:");
            e.printStackTrace();
        }
    }
}
