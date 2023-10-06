package gpb.dppt.itg.atm.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Envelope")
public class ItgAtmEnvelopeDto {
   @JsonProperty("Body")
    private ItgAtmBodyDto body;


    public static Builder builder(){
        return new Builder();
    }
    public static class Builder {
        private ItgAtmEnvelopeDto newItgAtmEnvelopeDto;
        public Builder() {newItgAtmEnvelopeDto = new ItgAtmEnvelopeDto();}
        public ItgAtmEnvelopeDto build() {return newItgAtmEnvelopeDto;}
        public Builder body(ItgAtmBodyDto body) {newItgAtmEnvelopeDto.body = body; return this;}

    }
}
