package gpb.dppt.itg.atm.controller;


import gpb.dppt.itg.atm.ItgAtmService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/itgatm")
public class ItgAtmController {

    @Autowired
    private ItgAtmService itgAtmService;



    @PostMapping
    public String SfveFee(@RequestBody String requestStr){
     return itgAtmService.route(requestStr);
    }
}
