package gpb.dppt.itg.atm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItgAtmTerminalDto {

    @JsonProperty("terminalId")
    private String terminalId;

    @JsonProperty("merchantId")
    private String merchantId;
}
