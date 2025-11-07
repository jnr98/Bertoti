package biblioteca.src;

public class Teste {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // CORREÇÃO: Limpa a tabela antes de adicionar para evitar o erro UNIQUE
        biblioteca.dbManager.limparTodos();

        System.out.println("Iniciando e Adicionando livros...");
        // 1. Adiciona novos livros
        biblioteca.adicionarLivro("A Sociedade do Anel", "J.R.R. Tolkien", "978-8578270682");
        biblioteca.adicionarLivro("Admirável Mundo Novo", "Aldous Huxley", "978-8535914849");
        biblioteca.adicionarLivro("Cálculo Integral", "James Stewart", "978-8522108151");

        // 2. Lista todos os livros
        biblioteca.listarTodosLivros();

        // 3. Busca um livro
        biblioteca.buscarLivroPorTitulo("Sociedade");
    }
}
		
		
		
		
		
		
		
		



