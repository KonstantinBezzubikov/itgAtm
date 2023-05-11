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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

@Builder
public class ItgAtmXmlXPathParser {

    private Document doc;
    private String preExpression;
    private String soapStr;


    public ItgSvfeCalcFeeAmtDto parseMsg(String soapStr) {
        this.soapStr = soapStr;
        preExpression = "/Envelope/Body/calculateAcqFee";
        ItgSvfeCalcFeeAmtDto itgSvfeCalcFeeAmtDto = ItgSvfeCalcFeeAmtDto.builder().build();

        itgSvfeCalcFeeAmtDto.setSourceCardNo(xPathParser("/cardSource","number"));
        itgSvfeCalcFeeAmtDto.setDstCardNo(xPathParser("/cardDestination","number"));
        itgSvfeCalcFeeAmtDto.setCurrency((long) Integer.parseInt(xPathParser("/amount","currency")));
        itgSvfeCalcFeeAmtDto.setAmount(new BigInteger(xPathParser("/amount","amount")));
        itgSvfeCalcFeeAmtDto.setTransType(xPathParser("","transType"));
        itgSvfeCalcFeeAmtDto.setTerminalId(xPathParser("/terminal","terminalId"));
        itgSvfeCalcFeeAmtDto.setMerchantId(xPathParser("/terminal","merchantId"));
        itgSvfeCalcFeeAmtDto.setServiceId(xPathParser("","serviceId"));

        return itgSvfeCalcFeeAmtDto;
    }

    private String xPathParser(String expression, String tagName){
        String result = "";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(new StringReader(soapStr)));
            doc.getDocumentElement().normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node nNode = ((NodeList) xPath.compile(preExpression+expression).evaluate(
                    doc, XPathConstants.NODESET)).item(0);
            Element eElement = (Element) nNode;
            if (eElement.getElementsByTagName(tagName).getLength()>0){
                result = eElement.getElementsByTagName(tagName)
                        .item(0)
                        .getTextContent();}

        } catch (ParserConfigurationException | XPathExpressionException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
