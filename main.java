
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

// Classe Abstrata Pessoa
abstract class Pessoa {
    protected String nome;
    protected String dataNascimento; // Formato: "dd/MM/yyyy"
    protected String cpf;

    public Pessoa(String nome, String dataNascimento, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public abstract int calcularIdade();
}

// Classe Criança
class Crianca extends Pessoa {
    private final String turma;
    private final String horario;

    public Crianca(String nome, String dataNascimento, String cpf, String turma, String horario) {
        super(nome, dataNascimento, cpf);
        this.turma = turma;
        this.horario = horario;
    }

    @Override
    public int calcularIdade() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

    public String getTurma() {
        return turma;
    }

    public String getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return "Criança: " + nome + ", Idade: " + calcularIdade() + ", Turma: " + turma + ", Horário: " + horario;
    }
}

// Classe Professor
class Professor extends Pessoa {
    private final String disciplina;
    private final ArrayList<Turma> turmas;

    public Professor(String nome, String dataNascimento, String cpf, String disciplina) {
        super(nome, dataNascimento, cpf);
        this.disciplina = disciplina;
        this.turmas = new ArrayList<>();
    }

    public void adicionarTurma(Turma turma) {
        if (turmas.size() < 2) {
            turmas.add(turma);
        } else {
            System.out.println("O professor já está atribuído a 2 turmas.");
        }
    }

    @Override
    public int calcularIdade() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(dataNascimento, formatter);
        return Period.between(nascimento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Professor: " + nome + ", Disciplina: " + disciplina;
    }
}

// Classe Turma
class Turma {
    private final String nome;
    private final String faixaEtaria;
    private final ArrayList<Professor> professores;
    private final ArrayList<Crianca> criancas;

    public Turma(String nome, String faixaEtaria) {
        this.nome = nome;
        this.faixaEtaria = faixaEtaria;
        this.professores = new ArrayList<>();
        this.criancas = new ArrayList<>();
    }

    public void adicionarProfessor(Professor professor) {
        if (professores.size() < 2) {
            professores.add(professor);
            professor.adicionarTurma(this);
        } else {
            System.out.println("A turma já tem 2 professores.");
        }
    }

    public void adicionarCrianca(Crianca crianca) {
        int idade = crianca.calcularIdade();
        String[] faixa = faixaEtaria.split("-");
        int idadeMinima = Integer.parseInt(faixa[0]);
        int idadeMaxima = Integer.parseInt(faixa[1]);

        if (idade >= idadeMinima && idade <= idadeMaxima) {
            criancas.add(crianca);
        } else {
            System.out.println("A criança não se encaixa na faixa etária da turma.");
        }
    }

    public ArrayList<Crianca> getCriancas() {
        return criancas;
    }

    public String getNome() {
        return nome;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public ArrayList<Professor> getProfessores() {
        return professores;
    }

    @Override
    public String toString() {
        return "Turma: " + nome + " (Faixa Etária: " + faixaEtaria + ")";
    }
}

// Classe Creche
class Creche {
    private final ArrayList<Crianca> criancas;
    private final ArrayList<Professor> professores;
    private final ArrayList<Turma> turmas;

    public Creche() {
        this.criancas = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    public void cadastrarCrianca(Crianca crianca) {
        criancas.add(crianca);
        for (Turma turma : turmas) {
            turma.adicionarCrianca(crianca);
        }
    }

    public void cadastrarProfessor(Professor professor) {
        professores.add(professor);
    }

    public void cadastrarTurma(Turma turma) {
        turmas.add(turma);
    }

    public void relatorioCriancasPorTurma() {
        for (Turma turma : turmas) {
            System.out.println(turma);
            for (Crianca crianca : turma.getCriancas()) {
                System.out.println(" - " + crianca);
            }
        }
    }

    public void relatorioCriancasPorHorario(String horario) {
        System.out.println("Crianças no horário " + horario + ":");
        for (Crianca crianca : criancas) {
            if (crianca.getHorario().equalsIgnoreCase(horario)) {
                System.out.println(" - " + crianca);
            }
        }
    }

    public void relatorioProfessoresPorTurma() {
        for (Turma turma : turmas) {
            System.out.println(turma);
            for (Professor professor : turma.getProfessores()) {
                System.out.println(" - " + professor);
            }
        }
    }
}

public class main {
    public static void main(String[] args) {
        Creche creche = new Creche();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n|___________CRECHE SOFTWARE_____________|");
            System.out.println("1. Cadastrar Criança");
            System.out.println("2. Cadastrar Professor");
            System.out.println("3. Cadastrar Turma");
            System.out.println("4. Relatório de Crianças por Turma");
            System.out.println("5. Relatório de Crianças por Horário");
            System.out.println("6. Relatório de Professores por Turma");
            System.out.println("7. Sair");
    
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
    
            switch (opcao) {
                case 1: {
                    System.out.print("Nome da criança: ");
                    String nomeC = scanner.nextLine();
                    System.out.print("Data de nascimento (dd/MM/yyyy): ");
                    String dataNascimentoC = scanner.nextLine();
                    System.out.print("CPF dos responsáveis: ");
                    String cpfC = scanner.nextLine();
                    System.out.print("Turma: ");
                    String turmaC = scanner.nextLine();
                    System.out.print("Horário (integral ou meio período): ");
                    String horarioC = scanner.nextLine();
                    Crianca crianca = new Crianca(nomeC, dataNascimentoC, cpfC, turmaC, horarioC);
                    creche.cadastrarCrianca(crianca);
                    System.out.println("Criança cadastrada com sucesso!");
                    break;
                }
                case 2: {
                    System.out.print("Nome do professor: ");
                    String nomeP = scanner.nextLine();
                    System.out.print("Data de nascimento (dd/MM/yyyy): ");
                    String dataNascimentoP = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpfP = scanner.nextLine();
                    System.out.print("Disciplina: ");
                    String disciplinaP = scanner.nextLine();
                    Professor professor = new Professor(nomeP, dataNascimentoP, cpfP, disciplinaP);
                    creche.cadastrarProfessor(professor);
                    System.out.println("Professor cadastrado com sucesso!");
                    break;
                }
                case 3: {
                    System.out.print("Nome da turma: ");
                    String nomeT = scanner.nextLine();
                    System.out.print("Faixa etária (ex: 2-3): ");
                    String faixaEtariaT = scanner.nextLine();
                    Turma turma = new Turma(nomeT, faixaEtariaT);
                    creche.cadastrarTurma(turma);
                    System.out.println("Turma cadastrada com sucesso!");
                    break;
                }
                case 4: {
                    System.out.println("\nRelatório de Crianças por Turma:");
                    creche.relatorioCriancasPorTurma();
                    break;
                }
                case 5: {
                    System.out.print("Horário (integral ou meio período): ");
                    String horario = scanner.nextLine();
                    creche.relatorioCriancasPorHorario(horario);
                    break;
                }
                case 6: {
                    System.out.println("\nRelatório de Professores por Turma:");
                    creche.relatorioProfessoresPorTurma();
                    break;
                }
                case 7: {
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                }
                default: {
                    System.out.println("Opção inválida! Por favor, escolha uma opção de 1 a 7.");
                    break;
                }
            }
        }
    }
}