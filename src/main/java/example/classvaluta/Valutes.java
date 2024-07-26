
package example.classvaluta;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@SuppressWarnings("unused")
public class Valutes {

    @SerializedName("Ccy")
    private String ccy;
    @SerializedName("CcyNm_RU")
    private String ccyNmRU;
    @SerializedName("CcyNm_UZ")
    private String ccyNmUZ;
    @SerializedName("Date")
    private String date;
    @SerializedName("Rate")
    private String rate;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss ⏰");
        return "%s \n1\uFE0F⃣  %s %s \uD83D\uDCB8 So'm ✅".formatted(LocalDateTime.now().format(formatter), ccyNmUZ, getRate(), getDate());
    }
}
