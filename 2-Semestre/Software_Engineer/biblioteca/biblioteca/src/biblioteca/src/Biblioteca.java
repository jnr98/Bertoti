package biblioteca.src;

import java.util.List;

public class Biblioteca {
    // Substitui a List interna pelo gerenciador do DB
    public DatabaseManager dbManager;

    public Biblioteca() {
        this.dbManager = new DatabaseManager();
    }

    public void adicionarLivro(String titulo, String autor, String isbn) {
        Livro novoLivro = new Livro(titulo, autor, isbn);
        dbManager.adicionarLivro(novoLivro);
    }

    public void listarTodosLivros() {
        List<Livro> livros = dbManager.buscarTodos();

        System.out.println("\n--- Lista Completa de Livros ---");
        if (livros.isEmpty()) {
            System.out.println("A biblioteca está vazia.");
            return;
        }
        for (Livro livro : livros) {
            System.out.println("ID: " + livro.getId() +
                    ", Título: " + livro.getTitulo() +
                    ", Autor: " + livro.getAutor() +
                    ", ISBN: " + livro.getIsbn());
        }
        System.out.println("--------------------------------");
    }

    public void buscarLivroPorTitulo(String termo) {
        List<Livro> livrosEncontrados = dbManager.buscarPorTitulo(termo);

        System.out.println("\n--- Resultados da Busca por '" + termo + "' ---");
        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o termo: '" + termo + "'.");
            return;
        }
        for (Livro livro : livrosEncontrados) {
            System.out.println("ID: " + livro.getId() +
                    ", Título: " + livro.getTitulo() +
                    ", Autor: " + livro.getAutor());
        }
        System.out.println("-------------------------------------------------");
    }
}