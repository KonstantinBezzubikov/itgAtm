package gpb.dppt.itg.atm.msgrouter;

public class Test {
    public static void main(String[] args) {

        String soapStr =
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
                        "<transType xmlns=\"\">{0}</transType>"+
                        "<terminal xmlns=\"\">"+
                        "<terminalId>3</terminalId>"+
                        "<merchantId>3455</merchantId>"+
                        "</terminal>"+
                        "</calculateAcqFee>"+
                        "</soap:Body>"+
                        "</soap:Envelope>";

        ItgAtmXmlParserFactory factory = new ItgAtmXmlParserFactory();
        ItgAtmXmlParser parser = factory.parser(ItgAtmParsingTypes.JACKSON);
        System.out.println(parser.parseMsg(soapStr));
    }
}
