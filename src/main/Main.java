package main;

import dao.PessoaDAO;
import dao.FuncionarioDAO;
import dao.ProjetoDAO;
import model.Pessoa;
import model.Funcionario;
import model.Projeto;

import java.util.List;
import java.util.Scanner;

public class Main {

    //scanner
    private static final Scanner sc = new Scanner(System.in);
    private static final PessoaDAO pessoaDAO = new PessoaDAO();
    private static final FuncionarioDAO funcDAO = new FuncionarioDAO();
    private static final ProjetoDAO projDAO = new ProjetoDAO();


    //tela incial para o usuario definir a escolha
    public static void main(String[] args) {
        int opc;
        //loop
        do {

            System.out.println("\n FAÇA SUA ESCOLHA \n");
            System.out.println("1. Cadastrar Pessoa");
            System.out.println("2. Listar Pessoas");
            System.out.println("3- Editar pessoas");
            System.out.println("4. Cadastrar Funcionário");
            System.out.println("5. Listar Funcionários");
            System.out.println("6-Editar Funcionáiros");
            System.out.println("7. Cadastrar Projeto");
            System.out.println("8. Listar Projetos");
            System.out.println("9-Editar Projetos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opc = sc.nextInt();
            sc.nextLine();
            //switch case para chamar a função quando o usuario definir
            switch (opc) {
                case 1 -> cadastrarPessoa();
                case 2 -> listarPessoas();
                case 3 -> atualizarPessoa();
                case 4 -> cadastrarFuncionario();
                case 5 -> listarFuncionarios();
                case 6 -> atualizarFuncionario();
                case 7 -> cadastrarProjeto();
                case 8 -> listarProjetos();
                case 9 -> atualizarProjeto();
                case 0 -> System.out.println("Fechando o sistema");
                default -> System.out.println("Opção inválida!");
            }
            //enquantoa  opção for diferente de 0 menu segue em loop
        } while (opc != 0);
    }
    //função para atualizar a pessoa
    private static void atualizarPessoa(){
        System.out.print("Digite o ID da pessoa: ");
        int id = sc.nextInt();
        sc.nextLine();

        Pessoa pessoaExistente = pessoaDAO.buscarPorId(id);
        if (pessoaExistente == null) {
            System.out.println(" Pessoa não encontrada.");
            return;
        }

        System.out.println("Pessoa atual: " + pessoaExistente);

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Novo email: ");
        String email = sc.nextLine();

        pessoaExistente.setNome(nome);
        pessoaExistente.setEmail(email);

        pessoaDAO.atualizar(pessoaExistente);


    }
    // função para atualizar o funcionario
    private static void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário: ");
        int id = sc.nextInt();
        sc.nextLine();

        Funcionario funcionario = funcDAO.buscarPorId(id);
        if (funcionario == null) {
            System.out.println(" Funcionário não encontrado.");
            return;
        }

        System.out.println("Funcionário atual: " + funcionario);

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();
        System.out.print("Novo email: ");
        String email = sc.nextLine();
        System.out.print("Nova matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Novo departamento: ");
        String departamento = sc.nextLine();

        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionario.setMatricula(matricula);
        funcionario.setDepartamento(departamento);

        funcDAO.atualizar(funcionario);
    }
    //função para atualizar o projeto
    private static void atualizarProjeto() {
        System.out.print("Digite o ID do projeto: ");
        int id = sc.nextInt();
        sc.nextLine();

        Projeto projeto = projDAO.buscarPorId(id);
        if (projeto == null) {
            System.out.println(" Projeto não encontrado.");
            return;
        }

        System.out.println("Projeto atual: " + projeto);

        System.out.print("Novo nome do projeto: ");
        String nome = sc.nextLine();
        System.out.print("Nova descrição: ");
        String descricao = sc.nextLine();
        System.out.print("ID do novo funcionário responsável: ");
        int idFunc = sc.nextInt();
        sc.nextLine();

        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setId_funcionario(idFunc);

        projDAO.atualizar(projeto);
    }

    //função para cadastrar pessoa
    private static void cadastrarPessoa() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        Pessoa p = new Pessoa(0, nome, email);
        pessoaDAO.inserir(p);
    }
    //função para listar pessoa
    private static void listarPessoas() {
        List<Pessoa> ps = pessoaDAO.listarTodas();
        System.out.println("\n-- PESSOAS CADASTRADAS --");
        ps.forEach(System.out::println);
    }
    //função para cadastrar funcionario
    private static void cadastrarFuncionario() {
        System.out.print("ID da Pessoa (deve existir): ");
        int idPessoa = sc.nextInt();
        sc.nextLine();
        System.out.print("Matrícula (ex: F001): ");
        String mat = sc.nextLine();
        System.out.print("Departamento: ");
        String dept = sc.nextLine();
        Funcionario f = new Funcionario(idPessoa, null, null, mat, dept);
        funcDAO.inserirFuncionario(f);
    }
    //função para listar funcionario
    private static void listarFuncionarios() {
        List<Funcionario> fs = funcDAO.listarTodos();
        System.out.println("\n-- FUNCIONÁRIOS CADASTRADOS --");
        fs.forEach(System.out::println);
    }
    //função para cadastrar objeto
    private static void cadastrarProjeto() {
        System.out.print("Nome do Projeto: ");
        String nome = sc.nextLine();
        System.out.print("Descrição: ");
        String desc = sc.nextLine();
        System.out.print("ID do Funcionário responsável: ");
        int idFunc = sc.nextInt();
        sc.nextLine();
        Projeto p = new Projeto(0, nome, desc, idFunc);
        projDAO.inserir(p);
    }
    //função para listar projetos
    private static void listarProjetos() {
        List<Projeto> ps = projDAO.listarTodos();
        System.out.println("\n-- PROJETOS CADASTRADOS --");
        ps.forEach(System.out::println);
    }
}
