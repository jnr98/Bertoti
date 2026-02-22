import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Imports do JavaFX para a interface
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // --- Componentes da Interface ---
    private TextArea chatArea;      // Área para mostrar o histórico do chat
    private TextField inputField;   // Campo para o usuário digitar
    private Button sendButton;      // Botão para enviar
    
    // --- Cliente HTTP (reaproveitável) ---
    private HttpClient httpClient;
    
    // O modelo Ollama que você quer usar (TROQUE AQUI!)
    private String ollamaModel = "qwen3:4b"; // <-- IMPORTANTE: Use o modelo que você tem!

    /**
     * Ponto de entrada principal da aplicação JavaFX.
     * É aqui que construímos a janela.
     */
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Chat com Ollama");
        
        // 1. Inicializar o Cliente HTTP
        this.httpClient = HttpClient.newHttpClient();

        // 2. Inicializar os componentes da tela
        chatArea = new TextArea();
        chatArea.setEditable(false); // O usuário não pode digitar no histórico
        chatArea.setWrapText(true);  // Quebra de linha automática
        
        inputField = new TextField();
        inputField.setPromptText("Digite sua pergunta aqui..."); // Texto de ajuda
        
        sendButton = new Button("Enviar");
        
        // --- Montando o Layout ---
        
        // O VBox organiza os elementos verticalmente (um em cima do outro)
        VBox root = new VBox(10); // 10 pixels de espaçamento
        root.setPadding(new Insets(10)); // 10 pixels de margem interna
        
        // A área de chat deve crescer para preencher o espaço
        VBox.setVgrow(chatArea, Priority.ALWAYS);
        
        // O HBox organiza o campo de texto e o botão horizontalmente (lado a lado)
        HBox inputArea = new HBox(5, new Label("Pergunta:"), inputField, sendButton);
        HBox.setHgrow(inputField, Priority.ALWAYS); // O campo de texto deve crescer
        
        // Adiciona tudo na tela
        root.getChildren().addAll(chatArea, inputArea);

        // 3. Definir a Ação do Botão
        sendButton.setOnAction(event -> handleSendAction());
        
        // 4. Configurar e Mostrar a Janela
        Scene scene = new Scene(root, 600, 400); // Janela de 600x400 pixels
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Ação executada quando o botão "Enviar" é clicado.
     */
    private void handleSendAction() {
        String prompt = inputField.getText();
        
        if (prompt == null || prompt.isBlank()) {
            return; // Não faz nada se o texto estiver vazio
        }
        
        // 1. Adiciona a pergunta do usuário à área de chat
        chatArea.appendText("Você: " + prompt + "\n\n");
        inputField.clear(); // Limpa o campo de entrada
        
        // 2. Desabilita o botão para evitar cliques duplicados
        sendButton.setDisable(true);
        chatArea.appendText("Ollama está pensando...\n");

        // --- A PARTE CRÍTICA (THREADING) ---
        // 3. Criamos uma NOVA THREAD para fazer a chamada de rede.
        // Isso evita que a interface gráfica (GUI) congele.
        new Thread(() -> {
            try {
                // 4. Chamamos nosso código de API (em outra thread)
                String ollamaResponse = callOllamaAPI(prompt);
                
                // 5. QUANDO TERMINAR, usamos Platform.runLater
                // para enviar a resposta de volta para a thread da GUI.
                Platform.runLater(() -> {
                    chatArea.appendText("Ollama: " + ollamaResponse + "\n\n");
                });
                
            } catch (Exception e) {
                // 6. Se der erro, também atualizamos a GUI
                Platform.runLater(() -> {
                    chatArea.appendText("Erro ao conectar com Ollama: " + e.getMessage() + "\n\n");
                });
            } finally {
                // 7. Reabilita o botão, mesmo se der erro
                Platform.runLater(() -> {
                    sendButton.setDisable(false);
                });
            }
        }).start(); // Inicia a nova thread
    }

    /**
     * Este é o nosso código de rede, copiado do exemplo anterior.
     * Ele é chamado pela thread de trabalho (background).
     */
    private String callOllamaAPI(String prompt) throws Exception {
        
        String ollamaUrl = "http://localhost:11434/api/generate";

        String jsonBody = "{"
                        + "  \"model\": \"" + this.ollamaModel + "\","
                        + "  \"prompt\": \"" + prompt.replace("\"", "\\\"") + "\","
                        + "  \"stream\": false"
                        + "}";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ollamaUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        
        // Envia a requisição e espera a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Extrai apenas o texto da resposta (igual ao código anterior)
        String responseBody = response.body();
        
        String startKey = "\"response\":\"";
        int startIndex = responseBody.indexOf(startKey) + startKey.length();
        int endIndex = responseBody.indexOf("\"", startIndex);

        if (startIndex == -1 || endIndex == -1) {
             throw new Exception("Formato de resposta JSON inesperado. Resposta: " + responseBody);
        }
        
        String extractedResponse = responseBody.substring(startIndex, endIndex);
        String cleanResponse = extractedResponse.replace("\\n", "\n").replace("\\\"", "\"");
        
        return cleanResponse;
    }

    /**
     * O método 'main' tradicional, que apenas chama o 'launch' do JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}