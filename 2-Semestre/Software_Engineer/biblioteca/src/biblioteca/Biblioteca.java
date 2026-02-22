package biblioteca;

import java.util.List;

import java.util.LinkedList;

public class Biblioteca {

	private List<Livro> livros = new LinkedList<Livro>();
	private List<Usuario> usuarios = new LinkedList<Usuario>();
	
	public void addLivro(Livro livro) {
		livros.add(livro);
	}
	
	public void addUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	public List<Livro> buscarLivroTitulo(String titulo){
		List<Livro> encontrados = new LinkedList<Livro>();
		for(Livro livro:livros) {
			if(livro.getTitulo().equals(titulo)) 
				encontrados.add(livro);
		}
		return encontrados;
	}
	
	public Livro buscarLivroIsbn(String isbn) {
		for(Livro livro:livros){
			if(livro.getIsbn().equals(isbn)) 
				return livro;
		}
		return null;
	}
}










//Livro - Classe - substantivo letra maiuscula
		// livros - atributo da classe Bibliotca
		// Atributo - substantivo letra minuscula
		// livro - variavel
		// na lista de todos os livros (attr livros),
		// pegue cada um deles (objetos do tipo Livro)
		// e use a variavel 'livro' para controlar aquele
		// objeto
