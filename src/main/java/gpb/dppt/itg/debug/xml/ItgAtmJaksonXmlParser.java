package gpb.dppt.itg.debug.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.debug.xml.dto.ItgAtmEnvelopeDto;
import java.io.IOException;

public class ItgAtmJaksonXmlParser {

    private static final String soapStr =
            "<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+
                    "<soap:Body>"+
                    "<calculateAcqFee xmlns=\"http://www.bpcbt.com/sv/infosource\">"+
                    "<cardSource xmlns=\"\">"+
                    "<number>1234123412341234</number>"+
                    "</cardSource>"+
                    "<cardDestination xmlns=\"\">"+
                    "<number>11111111111111111</number>"+
                    "</cardDestination>"+
                    "<amount xmlns=\"\">"+
                    "<amount>10000</amount>"+
                    "<currency>810</currency>"+
                    "</amount>"+
                    "<transType xmlns=\"\">TRANSFER</transType>"+
                    "<terminal xmlns=\"\">"+
                    "<terminalId>3</terminalId>"+
                    "<merchantId>3455</merchantId>"+
                    "</terminal>"+
                    "</calculateAcqFee>"+
                    "</soap:Body>"+
                    "</soap:Envelope>";

    public static void main(String[] args) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        ItgAtmEnvelopeDto envelope = new ItgAtmEnvelopeDto();
        envelope=xmlMapper.readValue(soapStr, ItgAtmEnvelopeDto.class);
        System.out.println(envelope);
    }
}
