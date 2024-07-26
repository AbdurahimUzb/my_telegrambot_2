package example.classvaluta;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ValutaHttp {

    public String getValutalar(String data) throws IOException, InterruptedException {

        final String url = "https://cbu.uz/uz/arkhiv-kursov-valyut/json/";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\033[1;92m" + "Valutalar http natijasi : " + response.statusCode() + "\033[0m");

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, Valutes.class).getType();
        List<Valutes> json = gson.fromJson(response.body(), type);
        for (Valutes valutes : json) {
            if (data.equals(valutes.getCcy()))
                return valutes.toString();
        }
        return "Bunday malimot topilmadi 😭😭😭";
    }
}
