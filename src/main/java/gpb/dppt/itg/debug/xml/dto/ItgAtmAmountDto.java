package gpb.dppt.itg.debug.xml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItgAtmAmountDto {
    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

}
