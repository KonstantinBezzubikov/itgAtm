package gpb.dppt.itg.atm.service;



import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import gpb.dppt.itg.atm.xmlparser.ItgAtmXMLService;
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
    private ItgAtmXMLService itgAtmXMLService;


    public String route(Map<String, String> headers, String requestStr){
        ItgSvfeCalcFeeAmtDto feeData = ItgSvfeCalcFeeAmtDto.builder().build();

       try {
            itgAtmXMLService.parseMsg(requestStr, feeData);

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

        return itgAtmXMLService.buildResponse(feeData);
    }




}


