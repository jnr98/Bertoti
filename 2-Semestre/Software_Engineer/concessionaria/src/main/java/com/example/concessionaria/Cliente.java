package com.example.concessionaria;

public class Cliente {
    private final String nome;
    private final String cpf;
    private final String telefone;

    public Cliente(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void comprarVeiculo(Veiculo v) {
        System.out.println(nome + " comprou o veículo de placa " + v.getPlaca());
    }

    public void exibirDados() {
        System.out.println("Cliente: " + nome + ", CPF: " + cpf + ", Telefone: " + telefone);
    }
}
