package gpb.dppt.itg.atm.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class ItgSvfeCalcFeeAmtDto {

    private String transId;
    private String sourceCardNo;
    private String dstCardNo;
    private BigInteger amount;
    private Long currency;
    private String transType;
    private Long svistaTransType;
    private String terminalId;
    private String merchantId;
    private String serviceId;
    private BigInteger fee;
    private BigInteger receiptFee;
    private String feeRateAlg;
    private String feeRateData;
    private String feeRateMsg;
}
