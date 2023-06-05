package gpb.dppt.itg.atm.msgrouter;

public class ItgAtmXmlParserFactory {

        public ItgAtmXmlParser parser(ItgAtmParsingTypes type) {
        ItgAtmXmlParser toReturn = null;
        switch (type) {
            case JACKSON:
                toReturn = new ItgAtmJaksonXmlParser();
                break;
            case DOM:
                toReturn = new ItgAtmDomXmlParser();
                break;
            default:
                throw new IllegalArgumentException("Wrong parser type:" + type);
        }
        return toReturn;
    }

}
