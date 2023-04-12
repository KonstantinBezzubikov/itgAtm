package gpb.dppt.itg.atm.repository;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;

import java.math.BigInteger;

public interface ItgAtmSvfeRepository {


    public BigInteger svistaCalcAcqFee2(ItgSvfeCalcFeeAmtDto acqFee);
    public BigInteger svistaCalcAcqFee(ItgSvfeCalcFeeAmtDto acqFee);
    public BigInteger svistaCalcIssFee(ItgSvfeCalcFeeAmtDto acqFee);

}
