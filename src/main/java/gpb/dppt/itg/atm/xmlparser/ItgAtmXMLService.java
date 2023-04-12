package gpb.dppt.itg.atm.xmlparser;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.MessageFormat;
import javax.xml.XMLConstants;



@Service
public class ItgAtmXMLService {

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

    public void parseMsg(String xml, ItgSvfeCalcFeeAmtDto itgSvfeCalcFeeAmtDto){
        try {

            SAXBuilder sax = new SAXBuilder();
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Document doc = sax.build(new StringReader(xml));
            Element node = doc.getRootElement().getChildren().get(0).getChildren().get(0);

            if(node.getChild("cardSource") != null){
                String sourceCardNo =  node.getChild("cardSource").getChildText("number");
                itgSvfeCalcFeeAmtDto.setSourceCardNo(sourceCardNo);}

            if(node.getChild("cardDestination") != null){
                String dstCardNo = node.getChild("cardDestination").getChildText("number");
                itgSvfeCalcFeeAmtDto.setDstCardNo(dstCardNo);}

            if(node.getChild("amount") != null){
            String amount = node.getChild("amount").getChildText("amount");
                System.out.println("amount:" + amount);
            itgSvfeCalcFeeAmtDto.setAmount(new BigInteger(amount));}

            if(node.getChild("amount") != null){
            String currency = node.getChild("amount").getChildText("currency");
            itgSvfeCalcFeeAmtDto.setCurrency((long)Integer.parseInt(currency));}

            if(node.getChild("transType") != null){
            String transType = node.getChild("transType").getText();
             itgSvfeCalcFeeAmtDto.setTransType(transType);}

            if(node.getChild("terminal") != null){
                String terminalId = node.getChild("terminal").getChildText("terminalId");
                itgSvfeCalcFeeAmtDto.setTerminalId(terminalId);}

            if(node.getChild("terminal") != null){
                String merchantId = node.getChild("terminal").getChildText("merchantId");
                itgSvfeCalcFeeAmtDto.setMerchantId(merchantId);}

            if(node.getChild("serviceId") != null){
                String serviceId = node.getChild("serviceId").getText();
                itgSvfeCalcFeeAmtDto.setServiceId(serviceId);}

        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

    }


}





