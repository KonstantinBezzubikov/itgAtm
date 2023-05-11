package gpb.dppt.itg.atm.msgrouter;

import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import lombok.Builder;

import java.text.MessageFormat;

@Builder
public class ItgAtmMsgBuilder {
    private static final String xmlResponseStr =
            "<s:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "<s:Body>" +
                    "<calculateAcqFeeResponse xmlns=\"http://www.bpcbt.com/sv/infosource\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
                    "<result xmlns=\"\">" +
                    "<fee>{0}</fee>" +
                    "</result>" +
                    "</calculateAcqFeeResponse>" +
                    "</s:Body>" +
                    "</s:Envelope>";

    public String buildResponse(ItgSvfeCalcFeeAmtDto fee){
        if (fee == null) {fee = ItgSvfeCalcFeeAmtDto.builder().build();}
        return new MessageFormat(xmlResponseStr).format(new Object[] {fee.getFee()});
    }
}
