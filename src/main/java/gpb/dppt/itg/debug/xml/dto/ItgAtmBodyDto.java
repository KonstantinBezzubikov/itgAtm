package gpb.dppt.itg.debug.xml.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItgAtmBodyDto {
    @JsonProperty("calculateAcqFee")
    private ItgAtmCalculateAcqFeeDto calculateAcqFee;
}
