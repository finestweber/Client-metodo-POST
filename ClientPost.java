import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.HashMap;

public class ClientPost {
    public static void main(String[] args) throws Exception {
        // Parâmetros para a solicitação POST
        Map<String, String> parameters = new HashMap<>();
        parameters.put("ownerRef", "valueOwnerRef");
 parameters.put("emailFrom", "finestweber@gmail.com");
        parameters.put("emailTo", "to@example.com");
        parameters.put("subject", "Test Subject");
        parameters.put("text", "Test email content.");

        // Criando uma instância do cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construindo a carga útil JSON
        String jsonPayload = buildJsonPayload(parameters);

        // Construindo a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.18.199:8080/emails/send-email"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        // Enviando a requisição e recebendo a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Exibindo o código de status da resposta
        System.out.println("Status code: " + response.statusCode());

        // Exibindo o corpo da resposta
        System.out.println("Response body: " + response.body());
    }

    // Método para construir a carga útil JSON a partir dos parâmetros
    private static String buildJsonPayload(Map<String, String> parameters) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        // Iterar sobre os parâmetros e construir o JSON
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }

        // Remover a vírgula final
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
