package gpb.dppt.itg.atm.msgrouter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.atm.dto.ItgAtmEnvelopeDto;

import java.math.BigInteger;

public class ItgAtmJaksonXmlParser implements ItgAtmXmlParser {


    @Override
    public ItgSvfeCalcFeeAmtDto parseMsg(String soapStr) {
        XmlMapper xmlMapper = new XmlMapper();
       // ItgAtmEnvelopeDto envelope = new ItgAtmEnvelopeDto();
        ItgAtmEnvelopeDto envelope = ItgAtmEnvelopeDto.builder().build();
        try {
            envelope=xmlMapper.readValue(soapStr, ItgAtmEnvelopeDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return convertingEnvelopeToSvfeCalcFee(envelope);
    }

    private ItgSvfeCalcFeeAmtDto convertingEnvelopeToSvfeCalcFee(ItgAtmEnvelopeDto envelope){
        return   ItgSvfeCalcFeeAmtDto.builder()
                .dstCardNo(envelope.getBody().getCalculateAcqFee().getCardDestination().getNumber())
                .sourceCardNo(envelope.getBody().getCalculateAcqFee().getCardSource().getNumber())
                .amount(new BigInteger(envelope.getBody().getCalculateAcqFee().getAmount().getAmount()))
                .currency(Long.valueOf(envelope.getBody().getCalculateAcqFee().getAmount().getCurrency()))
                .transType(envelope.getBody().getCalculateAcqFee().getTransType())
                .terminalId(envelope.getBody().getCalculateAcqFee().getTerminal().getTerminalId())
                .merchantId(envelope.getBody().getCalculateAcqFee().getTerminal().getMerchantId())
                .serviceId(envelope.getBody().getCalculateAcqFee().getServiceId())
                .build();
    }
}
