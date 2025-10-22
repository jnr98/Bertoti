import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet; // Nosso novo import
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:meu_banco_de_dados.db";

        // Try-with-resources principal (gerencia conn e stmt)
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

            // 1. Criar a tabela
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS usuarios ("
                                  + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                  + "nome TEXT NOT NULL,"
                                  + "email TEXT NOT NULL UNIQUE"
                                  + ");";
            stmt.execute(sqlCreateTable);
            
            System.out.println("Tabela 'usuarios' criada (ou já existia)!");

            // 2. Tentar inserir dados (com um try-catch interno)
            try {
                // Comando SQL para inserir Alice
                String sqlInsert1 = "INSERT INTO usuarios(nome, email) VALUES('Alice', 'alice@exemplo.com')";
                stmt.execute(sqlInsert1);

                // Comando SQL para inserir Bob
                String sqlInsert2 = "INSERT INTO usuarios(nome, email) VALUES('Bob', 'bob@exemplo.com')";
                stmt.execute(sqlInsert2);
                
                System.out.println("Dados inseridos com sucesso!");
                
            } catch (SQLException e) {
                // Se cairmos aqui, é muito provável que os dados já existam.
                System.out.println("Dados já existem. Ignorando inserção.");
            }

            // 3. Consultar os dados da tabela
            String sqlSelect = "SELECT id, nome, email FROM usuarios";
            
            // 4. Usar um try-with-resources para o ResultSet
            try (ResultSet rs = stmt.executeQuery(sqlSelect)) {
                
                System.out.println("\n--- Usuários Cadastrados ---");
                
                // 5. Iterar (percorrer) os resultados
                while (rs.next()) {
                    
                    // 6. Extrair os dados de cada linha
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    
                    // 7. Imprimir os dados
                    System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email);
                }
            } // O 'rs' é fechado automaticamente aqui

        } catch (SQLException e) {
            // Catch principal (para erros de conexão, criação de tabela ou consulta)
            System.err.println("Erro ao conectar ou operar o banco de dados: " + e.getMessage());
        }
        // O 'stmt' e 'conn' são fechados automaticamente aqui
    }
}