
package example.obhavo;

import lombok.Data;

import java.util.List;

@Data
public class Celcie {

    private String name;
    private Main main;
    private List<Weather> weather;

    @Data
    public static class Main {
        private float temp;
    }

    @Data
    public static class Weather {
        private String description;
    }

    @Override
    public String toString() {
        return String.format("Shaxar : %s \uD83C\uDFD9\nXarorat : %.1f °C \uD83C\uDF21\nHavo : %s \uD83C\uDF24", name, (main.temp -= 273), weather.get(0).description);
    }

}
