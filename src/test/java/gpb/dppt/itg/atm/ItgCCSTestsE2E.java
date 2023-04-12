package gpb.dppt.itg.atm;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.text.MessageFormat;
import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItgCCSTestsE2E {

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
								"<transType xmlns=\"\">{0}</transType>"+
								"<terminal xmlns=\"\">"+
									"<terminalId>3</terminalId>"+
									"<merchantId>3455</merchantId>"+
								"</terminal>"+
							"</calculateAcqFee>"+
						"</soap:Body>"+
					"</soap:Envelope>";


	String baseUrl = "http://localhost:";
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	@LocalServerPort
	private int port;

	@Test
	public void transfer() {
		try {

			assertEquals(buildXmlResponseStr("31"),controller(buildSoapStr("TRANSFER")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void cashIn() {
		try {
			assertEquals(buildXmlResponseStr("20"), controller(buildSoapStr("CASH_IN")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void cashOut() {
		try {
			assertEquals(buildXmlResponseStr("20"), controller(buildSoapStr("CASH_OUT")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void payment() {
		try {
			assertEquals(buildXmlResponseStr("20"), controller(buildSoapStr("PAYMENT")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void paymentCash() {
		try {
			assertEquals(buildXmlResponseStr("20"), controller(buildSoapStr("PAYMENT_CASH")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String controller(String soapStr) throws Exception {
		HttpEntity<String> entity = new HttpEntity<>(soapStr, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/itgatm"),
				HttpMethod.POST, entity, String.class);
		return response.getBody();
		//assertEquals(xmlResponseStr, response.getBody());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	public String buildSoapStr(String transType){
		return new MessageFormat(soapStr).format(new Object[] {transType});
	}

	public String buildXmlResponseStr(String fee){
		return new MessageFormat(xmlResponseStr).format(new Object[] {fee});
	}


}
