package gpb.dppt.itg.atm.msgrouter;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import org.springframework.stereotype.Service;
@Service
public class ItgAtmMsgRouter {

    public ItgSvfeCalcFeeAmtDto parseMsg(String xml){
        ItgAtmXmlParserFactory factory = new ItgAtmXmlParserFactory();
        ItgAtmXmlParser parser = factory.parser(ItgAtmParsingTypes.JACKSON);
        return parser.parseMsg(xml);
    }


    public String buildResponse(ItgSvfeCalcFeeAmtDto fee){
         return ItgAtmMsgBuilder.builder().build().
                    buildResponse(fee);
    }

}





