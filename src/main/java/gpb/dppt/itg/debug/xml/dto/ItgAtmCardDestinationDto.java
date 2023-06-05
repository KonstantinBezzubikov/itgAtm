package gpb.dppt.itg.debug.xml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItgAtmCardDestinationDto {

    @JsonProperty("number")
    private String number;
}
