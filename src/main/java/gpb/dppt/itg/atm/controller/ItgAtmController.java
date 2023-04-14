package gpb.dppt.itg.atm.controller;


import gpb.dppt.itg.atm.service.ItgAtmService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/itg/atm")
public class ItgAtmController {

    @Autowired
    private ItgAtmService itgAtmService;



    @PostMapping
    public String SfveFee(@RequestHeader Map<String, String> headers, @RequestBody String requestStr){
     return itgAtmService.route(headers,requestStr);
    }
}
