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


public class ItgAtmDomXmlParser implements ItgAtmXmlParser {
    private Document doc;

    @Override
    public ItgSvfeCalcFeeAmtDto parseMsg(String soapStr) {
        getDocFromXml(soapStr);
        String preExpression = "/Envelope/Body/calculateAcqFee";
        ItgSvfeCalcFeeAmtDto itgSvfeCalcFeeAmtDto = ItgSvfeCalcFeeAmtDto.builder().build();

        itgSvfeCalcFeeAmtDto.setSourceCardNo(getValueFromXml(preExpression,"/cardSource","number"));
        itgSvfeCalcFeeAmtDto.setDstCardNo(getValueFromXml(preExpression,"/cardDestination","number"));
        itgSvfeCalcFeeAmtDto.setCurrency((long) Integer.parseInt(getValueFromXml(preExpression,"/amount","currency")));
        itgSvfeCalcFeeAmtDto.setAmount(new BigInteger(getValueFromXml(preExpression,"/amount","amount")));
        itgSvfeCalcFeeAmtDto.setTransType(getValueFromXml(preExpression,"","transType"));
        itgSvfeCalcFeeAmtDto.setTerminalId(getValueFromXml(preExpression,"/terminal","terminalId"));
        itgSvfeCalcFeeAmtDto.setMerchantId(getValueFromXml(preExpression,"/terminal","merchantId"));
        itgSvfeCalcFeeAmtDto.setServiceId(getValueFromXml(preExpression,"","serviceId"));

        return itgSvfeCalcFeeAmtDto;
    }

    private void getDocFromXml(String soapStr){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
              DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(new InputSource(new StringReader(soapStr)));
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }


    private String getValueFromXml(String preExpression, String expression, String tagName) {
        String result = "";
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node nNode = ((NodeList) xPath.compile(preExpression+expression).evaluate(
                    doc, XPathConstants.NODESET)).item(0);
            Element eElement = (Element) nNode;
            if (eElement.getElementsByTagName(tagName).getLength()>0){
                result = eElement.getElementsByTagName(tagName)
                        .item(0)
                        .getTextContent();}
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
