package biblioteca.src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver SQLite JDBC.");
            e.printStackTrace();
        }
        createTable();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS livros ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " titulo TEXT NOT NULL,"
                + " autor TEXT NOT NULL,"
                + " isbn TEXT UNIQUE NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'livros' verificada/criada.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    // 1. Adicionar Livro (INSERT)
    public void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO livros(titulo, autor, isbn) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getIsbn());
            pstmt.executeUpdate();
            System.out.println("Livro adicionado: " + livro.getTitulo());
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    // 2. Buscar Todos os Livros (SELECT *)
    public List<Livro> buscarTodos() {
        String sql = "SELECT id, titulo, autor, isbn FROM livros";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
        return livros;
    }

    // 3. Buscar Por Título (SELECT com LIKE)
    public List<Livro> buscarPorTitulo(String termo) {
        String sql = "SELECT id, titulo, autor, isbn FROM livros WHERE titulo LIKE ?";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + termo + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("isbn")
                    );
                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por título: " + e.getMessage());
        }
        return livros;
    }

    // 4. Limpar Todos (DELETE) - Usado para testes limpos
    public void limparTodos() {
        String sql = "DELETE FROM livros";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'livros' limpa com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao limpar tabela: " + e.getMessage());
        }
    }
}