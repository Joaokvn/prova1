package model;

public class Funcionario extends  Pessoa {
    private String matricula;
    private String departamento;

    public Funcionario() {

    }
    //construtor
    public Funcionario(int id, String nome, String email, String matricula, String departamento) {
        super(id,nome,email);
        this.matricula = matricula;
        this.departamento = departamento;
    }
    @Override
    public String toString() {
        return "Funcionario { " +
                ", nome='" + departamento + '\'' +
                ", email='" + matricula + '\'' +
                " }";
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}

