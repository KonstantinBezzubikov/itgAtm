package gpb.dppt.itg.atm.service;

import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import java.math.BigInteger;

@Log4j
public class ItgAtmServiceBase {

    private boolean isCashIn(String transType) { return "CASH_IN".equals(transType); }
    private boolean isCashOut(String transType) { return "CASH_OUT".equals(transType); }
    private boolean isPayment(String transType) { return "PAYMENT".equals(transType); }
    private boolean isPaymentCash(String transType) { return "PAYMENT_CASH".equals(transType); }
    private boolean isTransfer(String transType) { return "TRANSFER".equals(transType); }


    public void changeFeeDataByTransType(ItgSvfeCalcFeeAmtDto feeData){

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
    }

    private void correctCardNoForPaymentInCash(ItgSvfeCalcFeeAmtDto feeData){
        if(feeData.getSourceCardNo() == null || feeData.getSourceCardNo().equals("")){
            //Check "9643810004075707" !!!!!
            feeData.setSourceCardNo("9643810004075707");
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

    public String buildTransId(ItgSvfeCalcFeeAmtDto feeData){

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

        }
        return sb.toString();
    }

    public void setFeeByModuleAndCheckForNull(ItgSvfeCalcFeeAmtDto feeData){
        if(feeData.getFee()!=null) {
            //  log.info("->Fee: {id=" + transId + ",fee=" + feeData.getFee().toString() + "}");
            if(feeData.getFee().longValue() < 0) {
                feeData.setFee(feeData.getFee().multiply(new BigInteger("-1")));
                log.info("-->Fee (temp correct): {id=" + feeData.getTransId() + ",fee=" + feeData.getFee().toString() + "}");
            }
        } else {
            log.info("->Fee (null): {id=" + feeData.getTransId() + "}");
            feeData.setFee(new BigInteger("0"));
        }

    }
}
