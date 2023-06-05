package gpb.dppt.itg.atm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItgAtmCardSourceDto {

    @JsonProperty("number")
    private String number;
}
