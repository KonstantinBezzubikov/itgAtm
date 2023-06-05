package gpb.dppt.itg.atm.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Envelope")
public class ItgAtmEnvelopeDto {

   @JsonProperty("Body")
    private ItgAtmBodyDto body;
}
