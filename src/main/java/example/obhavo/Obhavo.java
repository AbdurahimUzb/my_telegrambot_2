package example.obhavo;

import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Obhavo {

    @SneakyThrows
    public String sendHttpObhavo() {
        final String url = "http://api.openweathermap.org/data/2.5/weather?q=Tashkent&appid=c640bd47626d3494162fbe6e43f26e75";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\033[1;91m" + "Ob havodan qaytgan natija : " + response.statusCode() + "\033[0m");
        Gson gson = new Gson();

        Celcie json = gson.fromJson(response.body(), Celcie.class);
        return json.toString();
    }


}
