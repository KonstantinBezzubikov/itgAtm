package gpb.dppt.itg.debug;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;


@Log4j
public class ItgAtmDebugClient {


    //java -cp itgAtm-0.0.1-SNAPSHOT.jar  -Dloader.main=gpb.dppt.itg.debug.ItgAtmDebugClient org.springframework.boot.loader.PropertiesLauncher
// "arg1"
    private static final String soapStr =
            "<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
                    "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+
                    "<soap:Body>"+
                    "<calculateAcqFee xmlns=\"http://www.bpcbt.com/sv/infosource\">"+
                    "<cardSource xmlns=\"\">"+
                    "<number>{0}</number>"+
                    "</cardSource>"+
                     "{1}"+
                    "<amount xmlns=\"\">"+
                    "<amount>{2}</amount>"+
                    "<currency>{3}</currency>"+
                    "</amount>"+
                    "<transType xmlns=\"\">{4}</transType>"+
                    "<terminal xmlns=\"\">"+
                    "<terminalId>{5}</terminalId>"+
                    "<merchantId>{7}</merchantId>"+
                    "</terminal>"+
                    "{7}"+
                    "</calculateAcqFee>"+
                    "</soap:Body>"+
                    "</soap:Envelope>";


  // static final String URL = "http://localhost:8080/itgatm";


   public static void main(String[] args) {
      ItgAtmDebugClient c = new ItgAtmDebugClient();
      c.start(args);
   }



   private void start(String[] args){

      String URL = args[0];
      String transType = args[1];
      String cardNo = args[2];
      String amount = args[3];
      String currency = args[4];
      String serviceId = null;
      String dstCardNo = null;
      String terminalId = args[5];
      String merchantId = "";
      if("PAYMENT".equals(transType) || "PAYMENT_CASH".equals(transType)) {
         serviceId = args[6];
      } else if("TRANSFER".equals(transType)) {
         dstCardNo = args[6];
      }
      if("empty".equals(cardNo)) {
         cardNo = "";
      }
      log.info("building soap string....");


       HttpHeaders headers = new HttpHeaders();
      headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
      headers.setContentType(MediaType.APPLICATION_XML);

      RestTemplate restTemplate = new RestTemplate();

      // Data attached to the request.
      HttpEntity<String> requestBody = new HttpEntity<>(buildSoapStr(transType, cardNo, amount, currency, terminalId, merchantId, serviceId, dstCardNo), headers);

      // Send request with POST method.
      ResponseEntity<String> e = restTemplate.postForEntity(URL, requestBody, String.class);

      System.out.println("arg: " + args[0]+" " + args[1]+" "+URL);
      if (e != null) {
         System.out.println("Result: " + e);
      } else {
         System.out.println("Something error!");
      }

   }

   public static String buildSoapStr(String transType, String cardNo, String amount, String currency, String terminalId, String merchantId, String serviceId, String dstCardNo){
      String dstCardNoMsg = "";
      String serviceIdMsg = "";
      if(dstCardNo!=null) {
         dstCardNoMsg = "<cardDestination xmlns=\"\">" +
                 "<number>" + dstCardNo + "</number>" +
                 "</cardDestination>";
      }
      if(serviceId!=null) {
         serviceIdMsg = "<serviceId xmlns=\"\">" + serviceId + "</serviceId>";
      }
      return new MessageFormat(soapStr).format(new Object[] {cardNo, dstCardNoMsg, amount, currency,transType, terminalId, merchantId, serviceIdMsg});
   }


}
