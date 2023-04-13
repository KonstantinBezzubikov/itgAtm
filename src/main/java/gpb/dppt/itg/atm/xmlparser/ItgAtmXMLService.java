package gpb.dppt.itg.atm.xmlparser;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
@Service
public class ItgAtmXMLService {

    public void parseMsg(String xml, ItgSvfeCalcFeeAmtDto itgSvfeCalcFeeAmtDto){
        ItgAtmDomParserXml.builder().build().parseMsg(xml,itgSvfeCalcFeeAmtDto);
    }

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
            return new MessageFormat(xmlResponseStr).format(new Object[] {fee.getFee()});
    }




}





