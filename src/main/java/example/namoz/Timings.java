
package example.namoz;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@SuppressWarnings("unused")
@Data
public class Timings  {
    @SerializedName("Fajr")
    private String fajr;

    @SerializedName("Sunrise")
    private String sunrise;

    @SerializedName("Dhuhr")
    private String dhuhr;

    @SerializedName("Asr")
    private String asr;

    @SerializedName("Sunset")
    private String sunset;

    @SerializedName("Maghrib")
    private String maghrib;

    @SerializedName("Isha")
    private String isha;


}

@Data
class Dates {
    @SerializedName("timings")
    private Timings timings;

}

@Data
class ApiResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Dates dates;


}
