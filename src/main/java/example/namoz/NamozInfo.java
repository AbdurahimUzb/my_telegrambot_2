package example.namoz;

import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NamozInfo {

    @SneakyThrows
    public String getNamozHttpInfo() {


        final String url = "https://api.aladhan.com/v1/timingsByCity/19-07-2024?city=Tashkent&country=Uzbekistan&method=2";

        final HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\033[1;91m" + "Namoz http dan qaytgan natija : " + response.statusCode() + "\033[0m");

        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response.body(), ApiResponse.class);
        Timings timings = apiResponse.getDates().getTimings();

        String fajr = timings.getFajr();
        String sunrise = timings.getSunrise();
        String dhuhr = timings.getDhuhr();
        String asr = timings.getAsr();
        String sunset = timings.getSunset();
        String isha = timings.getIsha();

        return String.format("\uD83C\uDFD9  ТОНГ - %s \uD83D\uDD70\n🌅  ҚУЁШ - %s 🕰\n\uD83C\uDFDEПЕШИН - %s \uD83D\uDD70\n\uD83C\uDF07  АСР - %s \uD83D\uDD70\n\uD83C\uDF06  ШОМ - %s \uD83D\uDD70\n\uD83C\uDF03  ХУФТОН - %s \uD83D\uDD70\n",
                fajr,
                sunrise,
                dhuhr,
                asr,
                sunset,
                isha);
    }
}

