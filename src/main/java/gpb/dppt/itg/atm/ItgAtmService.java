package gpb.dppt.itg.atm;



import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.atm.xmlparser.ItgAtmXMLService;
import gpb.dppt.itg.atm.repository.ItgAtmSvfeJdbcRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;

@Service
@AllArgsConstructor
@Log4j
public class ItgAtmService {

    @Autowired
    private ItgAtmSvfeJdbcRepository itgAtmSvfeJdbcRepository;
    @Autowired
    private ItgAtmXMLService itgAtmXMLService;


    private boolean isCashIn(String transType) { return "CASH_IN".equals(transType); }
    private boolean isCashOut(String transType) { return "CASH_OUT".equals(transType); }
    private boolean isPayment(String transType) { return "PAYMENT".equals(transType); }
    private boolean isPaymentCash(String transType) { return "PAYMENT_CASH".equals(transType); }
    private boolean isTransfer(String transType) { return "TRANSFER".equals(transType); }

    public String route(String requestStr){
        String transId = "";

        ItgSvfeCalcFeeAmtDto feeData = new ItgSvfeCalcFeeAmtDto();

       try {
            itgAtmXMLService.parseMsg(requestStr, feeData);

            transId = buildTransId(feeData);

            log.info(" ");
            log.info("->Fee: {transId= " + transId + "}");
            feeData.setMerchantId(feeData.getTerminalId());

            if (isTransfer(feeData.getTransType())) {
                log.info("->Transfer: {crd=" + maskCardNo(feeData.getSourceCardNo()) + "dstCrd: " + maskCardNo(feeData.getDstCardNo()) + ",term=" + feeData.getTerminalId() + ",amt=" + feeData.getAmount() +
                        ",cur=" + feeData.getCurrency() + "}");
                feeData.setSvistaTransType((long) 700);
            }
            if (isPaymentCash(feeData.getTransType())) {
                log.info("->PaymentCash: {crd=" + maskCardNo(feeData.getSourceCardNo()) + ",term=" + feeData.getTerminalId() + ",amt=" + feeData.getAmount() +
                        ",cur=" + feeData.getCurrency() + "}");
                feeData.setSvistaTransType((long) 509);
                correctCardNoForPaymentInCash(feeData);
            }

            if (isPayment(feeData.getTransType())) {
                log.info("->Payment: {crd=" + maskCardNo(feeData.getSourceCardNo()) + ",term=" + feeData.getTerminalId() + ",amt=" + feeData.getAmount() +
                        ",cur=" + feeData.getCurrency() + "}");
                feeData.setSvistaTransType((long) 508);
            }
            if (isCashOut(feeData.getTransType())) {
                log.info("->CashOut: {crd=" + maskCardNo(feeData.getSourceCardNo()) + ",term=" + feeData.getTerminalId() + ",amt=" + feeData.getAmount() +
                        ",cur=" + feeData.getCurrency() + "}");
                feeData.setSvistaTransType((long) 700);
            }
            if (isCashIn(feeData.getTransType())) {
                log.info("->CashIn: {crd=" + maskCardNo(feeData.getSourceCardNo()) + ",term=" + feeData.getTerminalId() + ",amt=" + feeData.getAmount() +
                        ",cur=" + feeData.getCurrency() + "}");
                feeData.setSvistaTransType((long) 618);
            }
            itgAtmSvfeJdbcRepository.svistaCalcFee(feeData);

        }catch (Exception e){
            log.error(e);
        }



        setFeeByModuleAndCheckForNull(feeData,transId);

       log.info("->Fee: {id=" + transId + ",fee=" + feeData.getFee().toString() + "}");

        return itgAtmXMLService.buildResponse(feeData);
    }


    private void correctCardNoForPaymentInCash(ItgSvfeCalcFeeAmtDto feeData){
        if(feeData.getSourceCardNo() == null || feeData.getSourceCardNo().equals("")){
            //Check "9643810004075707" !!!!!
            feeData.setSourceCardNo("9643810004075707");
        }
    }

    private void setFeeByModuleAndCheckForNull(ItgSvfeCalcFeeAmtDto feeData, String transId){
        if(feeData.getFee()!=null) {
          //  log.info("->Fee: {id=" + transId + ",fee=" + feeData.getFee().toString() + "}");
            if(feeData.getFee().longValue() < 0) {
                feeData.setFee(feeData.getFee().multiply(new BigInteger("-1")));
                log.info("-->Fee (temp correct): {id=" + transId + ",fee=" + feeData.getFee().toString() + "}");
            }
        } else {
            log.info("->Fee (null): {id=" + transId + "}");
            feeData.setFee(new BigInteger("0"));
        }

    }

    private String maskCardNo(String cardNo){
        if(cardNo==null)
            return "";
        if(cardNo.length() >= 16)
            return cardNo.substring(0, 6).concat("******").concat(cardNo.substring(12));
        else if(cardNo.length() >= 4)
            return cardNo.substring(cardNo.length()-4);
        else
            return cardNo;
    }

    private String buildTransId(ItgSvfeCalcFeeAmtDto feeData){

   StringBuffer sb = new StringBuffer();
       try {
        sb.append("[").append(feeData.getTransType()).append("]");
        sb.append("[").append(maskCardNo(feeData.getSourceCardNo())).append("]");
        sb.append("[").append(feeData.getTerminalId()).append("]");
        sb.append("[");
        if(feeData.getServiceId() != null) {
         sb.append(feeData.getServiceId());
        }
        sb.append("]");
        sb.append("[");
        if(feeData.getAmount() != null) {
         sb.append(feeData.getAmount().toString());
        }
        sb.append("]");
        } catch(Exception ex) {
        return "0";
       }
   return sb.toString();
    }
}


