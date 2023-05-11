package gpb.dppt.itg.atm.msgrouter;

import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import lombok.Builder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

@Builder
public class ItgAtmXmlDomParser {

    public ItgSvfeCalcFeeAmtDto parseMsg(String soapStr) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        ItgSvfeCalcFeeAmtDto itgSvfeCalcFeeAmtDto = ItgSvfeCalcFeeAmtDto.builder().build();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc =db.parse(new InputSource(new StringReader(soapStr)));
            doc.getDocumentElement().normalize();

            Node node = doc.getDocumentElement().getFirstChild().getFirstChild();
            Element element = (Element) node;

            if(doc.getElementsByTagName("amount") != null) {
                NodeList amountList = doc.getElementsByTagName("amount");
                Node amountNode = amountList.item(0);
                Element amountElement = (Element) amountNode;

                if (amountElement.getElementsByTagName("currency").item(0) != null)
                    itgSvfeCalcFeeAmtDto.setCurrency((long) Integer.parseInt(amountElement.getElementsByTagName("currency").item(0).getTextContent()));

                if (amountElement.getElementsByTagName("amount").item(0) != null)
                    itgSvfeCalcFeeAmtDto.setAmount(new BigInteger(amountElement.getElementsByTagName("amount").item(0).getTextContent()));
            }


            if(element.getElementsByTagName("cardSource").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setSourceCardNo(element.getElementsByTagName("cardSource").item(0).getTextContent());

            if(element.getElementsByTagName("cardDestination").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setDstCardNo(element.getElementsByTagName("cardDestination").item(0).getTextContent());

            if(element.getElementsByTagName("transType").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setTransType(element.getElementsByTagName("transType").item(0).getTextContent());

            if(element.getElementsByTagName("terminalId").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setTerminalId(element.getElementsByTagName("terminalId").item(0).getTextContent());

            if(element.getElementsByTagName("merchantId").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setMerchantId(element.getElementsByTagName("merchantId").item(0).getTextContent());

            if(element.getElementsByTagName("serviceId").item(0)!=null)
                itgSvfeCalcFeeAmtDto.setServiceId(element.getElementsByTagName("serviceId").item(0).getTextContent());


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return itgSvfeCalcFeeAmtDto;
    }

}

