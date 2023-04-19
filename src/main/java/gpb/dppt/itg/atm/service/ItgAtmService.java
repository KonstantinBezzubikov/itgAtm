package gpb.dppt.itg.atm.service;



import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.atm.msgrouter.ItgAtmMsgRouterService;
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
    private ItgAtmMsgRouterService itgAtmMsgRouterService;


    public String route(Map<String, String> headers, String requestStr){
        ItgSvfeCalcFeeAmtDto feeData = null;

       try {
           feeData = itgAtmMsgRouterService.parseMsg(requestStr);

            if(headers.get("x-correlation-id") != null){
            feeData.setTransId(headers.get("x-correlation-id"));}

            log.info("->Fee: {xCorTransId= " + feeData.getTransId() +" TransId= " + buildTransId(feeData) +"}");
            feeData.setMerchantId(feeData.getTerminalId());

            changeFeeDataByTransType(feeData);

            itgAtmSvfeJdbcRepository.svistaCalcFee(feeData);

        }catch (Exception e){
            log.error(e);
        }
        setFeeByModuleAndCheckForNull(feeData);

       log.info("->Fee: {xCorTransId=" + feeData.getTransId() + ",fee=" + feeData.getFee().toString() + "}");


        return itgAtmMsgRouterService.buildResponse(feeData);
    }




}


