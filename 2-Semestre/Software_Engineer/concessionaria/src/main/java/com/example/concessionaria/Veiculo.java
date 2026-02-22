package com.example.concessionaria;

public class Veiculo {
    private final String modelo;
    private final String marca;
    private final String placa;

    public Veiculo(String modelo, String marca, String placa) {
        this.modelo = modelo;
        this.marca = marca;
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void exibirInfo() {
        System.out.println("Modelo: " + modelo + ", Marca: " + marca + ", Placa: " + placa);
    }
}
