package com.example.concessionaria;

import java.util.ArrayList;
import java.util.List;

public class Concessionaria {
    private final String nome;
    private final String endereco;
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();

    public Concessionaria(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public void adicionarVeiculo(Veiculo v) {
        veiculos.add(v);
        System.out.println("Veículo adicionado: " + v.getPlaca());
    }

    public void adicionarCliente(Cliente c) {
        clientes.add(c);
        System.out.println("Cliente adicionado: " + c.getNome());
    }

    public Veiculo buscarVeiculo(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) return v;
        }
        return null;
    }

    public Cliente buscarCliente(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome)) return c;
        }
        return null;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
