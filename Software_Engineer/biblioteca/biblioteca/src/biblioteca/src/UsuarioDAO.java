package biblioteca.src;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
// ... (outros imports)

public class UsuarioDAO {
    // A URL e o connect() são os mesmos, mas os métodos SQL mudam.
    private static final String URL = "jdbc:sqlite:biblioteca.db";
    // ... (connect() e Class.forName)

    // Exemplo de método para UsuarioDAO
    public void adicionarUsuario(Usuario user) {
        String sql = "INSERT INTO usuarios(nome, login, senha) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // ... define os parâmetros
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    private Connection connect() {
        return null;
    }

    public void verificarCredenciais(String login, String senha) {
    }
}