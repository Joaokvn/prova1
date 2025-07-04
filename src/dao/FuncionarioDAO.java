package dao;

import model.Funcionario;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    //metodo para inserir um funcionario
    public void inserirFuncionario(Funcionario funcionario) {
        // verifica se a pessoa existe
        String checkPessoa = "SELECT 1 FROM pessoa WHERE id = ?";
        String insertSql = "INSERT INTO funcionario (id, matricula, departamento) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psCheck = conn.prepareStatement(checkPessoa)) {

            psCheck.setInt(1, funcionario.getId());
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                System.out.println(" Erro: Pessoa com ID " + funcionario.getId() + " não encontrada.");
                return;
            }

            // insere funcionario
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                psInsert.setInt(1, funcionario.getId());
                psInsert.setString(2, funcionario.getMatricula());
                psInsert.setString(3, funcionario.getDepartamento());
                psInsert.executeUpdate();
                System.out.println(" Funcionário inserido com sucesso!");
            }
        // caso der erro ele avisa o erro
        } catch (SQLException e) {
            System.out.println(" Erro ao inserir funcionário:");
            e.printStackTrace();
        }
    }

        // metodo para buscar funcionario por ID
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM funcionario f " +
                "JOIN pessoa p ON f.id = p.id " +
                "WHERE f.id = ?";
        Funcionario func = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                func = new Funcionario();
                func.setId(rs.getInt("id"));
                func.setNome(rs.getString("nome"));
                func.setEmail(rs.getString("email"));
                func.setMatricula(rs.getString("matricula"));
                func.setDepartamento(rs.getString("departamento"));
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao buscar funcionário:");
            e.printStackTrace();
        }

        return func;
    }

        //metodo para listar os funcionarios
    public List<Funcionario> listarTodos() {
        String sql = "SELECT f.id, p.nome, p.email, f.matricula, f.departamento " +
                "FROM funcionario f " +
                "JOIN pessoa p ON f.id = p.id";
        List<Funcionario> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario func = new Funcionario();
                func.setId(rs.getInt("id"));
                func.setNome(rs.getString("nome"));
                func.setEmail(rs.getString("email"));
                func.setMatricula(rs.getString("matricula"));
                func.setDepartamento(rs.getString("departamento"));
                lista.add(func);
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao listar funcionários:");
            e.printStackTrace();
        }

        return lista;
    }


    public void atualizar(Funcionario funcionario) {
        String updatePessoa = "UPDATE pessoa SET nome = ?, email = ? WHERE id = ?";
        String updateFunc = "UPDATE funcionario SET matricula = ?, departamento = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar()) {
            // Atualiza funcionario
            try (PreparedStatement ps1 = conn.prepareStatement(updatePessoa)) {
                ps1.setString(1, funcionario.getNome());
                ps1.setString(2, funcionario.getEmail());
                ps1.setInt(3, funcionario.getId());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(updateFunc)) {
                ps2.setString(1, funcionario.getMatricula());
                ps2.setString(2, funcionario.getDepartamento());
                ps2.setInt(3, funcionario.getId());
                ps2.executeUpdate();
            }

            System.out.println(" Funcionário atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println(" Erro ao atualizar funcionário:");
            e.printStackTrace();
        }
    }

        //metodo para excluir funcionario com o ID
    public void excluir(int id) {
        String checkProjetos = "SELECT COUNT(*) FROM projeto WHERE id_funcionario = ?";
        String deleteSql = "DELETE FROM funcionario WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement psCheck = conn.prepareStatement(checkProjetos)) {

            psCheck.setInt(1, id);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println(" Erro: não é possível excluir funcionário vinculado a projetos.");
                return;
            }

            // exclusão
            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setInt(1, id);
                psDelete.executeUpdate();
                System.out.println(" Funcionário excluído com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println(" Erro ao excluir funcionário:");
            e.printStackTrace();
        }
    }
}