package gpb.dppt.itg.atm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class ItgAtmBodyDto {
    @JsonProperty("calculateAcqFee")
    private ItgAtmCalculateAcqFeeDto calculateAcqFee;
}
