package gpb.dppt.itg.debug.xml.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "cardSource", "cardDestination", "amount", "transType","terminal","serviceId" })
public class ItgAtmCalculateAcqFeeDto {

 @JsonProperty("cardSource")
 private ItgAtmCardSourceDto cardSource;

 @JsonProperty("cardDestination")
 private ItgAtmCardDestinationDto cardDestination;

 @JsonProperty("amount")
 private ItgAtmAmountDto amount;

 @JsonProperty("transType")
 private String transType;

 @JsonProperty("terminal")
 private ItgAtmTerminalDto terminal;

 @JsonProperty("serviceId")
 private String serviceId;

}
