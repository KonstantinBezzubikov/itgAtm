package gpb.dppt.itg.atm.service;



import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.atm.msgrouter.ItgAtmMsgRouter;
import gpb.dppt.itg.atm.repository.ItgAtmSvfeJdbcRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
@Log4j
public class ItgAtmService extends ItgAtmServiceBase{

    @Autowired
    private ItgAtmSvfeJdbcRepository itgAtmSvfeJdbcRepository;
    @Autowired
    private ItgAtmMsgRouter itgAtmMsgRouter;


    public String route(Map<String, String> headers, String requestStr){
        ItgSvfeCalcFeeAmtDto feeData = null;

       try {
            feeData = itgAtmMsgRouter.parseMsg(requestStr);

            setTransId(feeData, headers);

            log.info("->Fee: {xCorTransId= " + feeData.getTransId() +" TransId= " + buildTransId(feeData) +"}");

            setMerchantId(feeData);

            changeFeeDataByTransType(feeData);

            itgAtmSvfeJdbcRepository.svistaCalcFee(feeData);

        }catch (Exception e){
            log.error(e);
        }
        setFeeByModuleAndCheckForNull(feeData);

       log.info("->Fee: {xCorTransId=" + feeData.getTransId() + ",fee=" + feeData.getFee().toString() + "}");


        return itgAtmMsgRouter.buildResponse(feeData);
    }




}


