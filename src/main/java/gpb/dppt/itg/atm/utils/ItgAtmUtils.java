package gpb.dppt.itg.atm.utils;

public class ItgAtmUtils {
    public String maskCardNo(String cardNo){
        if(cardNo==null)
            return "";
        if(cardNo.length() >= 16)
            return cardNo.substring(0, 6).concat("******").concat(cardNo.substring(12));
        else if(cardNo.length() >= 4)
            return cardNo.substring(cardNo.length()-4);
        else
            return cardNo;
    }
}
