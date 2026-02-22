package com.example.concessionaria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConcessionariaTest {

    @Test
    void deveAdicionarEBuscarClienteEVeiculo() {
        Concessionaria cons = new Concessionaria("AutoVale", "Rua A, 100");
        Veiculo v1 = new Veiculo("Civic", "Honda", "ABC-1234");
        Cliente c1 = new Cliente("Fagner Louis", "123.456.789-00", "(12) 99999-0000");

        cons.adicionarVeiculo(v1);
        cons.adicionarCliente(c1);

        assertEquals(1, cons.getVeiculos().size());
        assertEquals(1, cons.getClientes().size());

        Veiculo buscadoV = cons.buscarVeiculo("abc-1234");
        Cliente buscadoC = cons.buscarCliente("fagner louis");

        assertNotNull(buscadoV, "Veículo deve ser encontrado por placa (case-insensitive)");
        assertNotNull(buscadoC, "Cliente deve ser encontrado por nome (case-insensitive)");
        assertEquals("ABC-1234", buscadoV.getPlaca());
        assertEquals("Fagner Louis", buscadoC.getNome());
    }

    @Test
    void deveImprimirCompraEInfos() {
        Concessionaria cons = new Concessionaria("AutoVale", "Rua A, 100");
        Veiculo v1 = new Veiculo("Onix", "Chevrolet", "XYZ-5678");
        Cliente c1 = new Cliente("Joana", "987.654.321-00", "(12) 98888-7777");
        cons.adicionarVeiculo(v1);
        cons.adicionarCliente(c1);

        // Captura do stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));
        try {
            c1.comprarVeiculo(v1);
            v1.exibirInfo();
            c1.exibirDados();
        } finally {
            System.setOut(originalOut);
        }

        String saida = baos.toString();
        assertTrue(saida.contains("Joana comprou o veículo de placa XYZ-5678"));
        assertTrue(saida.contains("Modelo: Onix, Marca: Chevrolet, Placa: XYZ-5678"));
        assertTrue(saida.contains("Cliente: Joana, CPF: 987.654.321-00, Telefone: (12) 98888-7777"));
    }
}
