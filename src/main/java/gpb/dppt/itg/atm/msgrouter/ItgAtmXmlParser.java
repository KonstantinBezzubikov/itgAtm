package gpb.dppt.itg.atm.msgrouter;

import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;

public interface ItgAtmXmlParser {
    ItgSvfeCalcFeeAmtDto parseMsg(String soapStr);
}
