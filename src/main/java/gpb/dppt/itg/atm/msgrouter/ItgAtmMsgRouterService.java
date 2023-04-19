package gpb.dppt.itg.atm.msgrouter;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import org.springframework.stereotype.Service;
@Service
public class ItgAtmMsgRouterService {

    public ItgSvfeCalcFeeAmtDto parseMsg(String xml){
        return ItgAtmDomParserXml.builder().build().
                parseMsg(xml);
    }


    public String buildResponse(ItgSvfeCalcFeeAmtDto fee){
         return ItgAtmBuildResponseXML.builder().build().
                    buildResponse(fee);
    }

}





