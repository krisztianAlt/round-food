package com.example.roundfood.controller.collectdata;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class FoodDataHandler {

	public FoodDataHandler() {
    }

    public void collectFoodTypeData(@RequestParam Map<String,String> allRequestParams,
                                 Model model,
                                 HttpServletRequest httpServletRequest){
        Long customerId = (Long) httpServletRequest.getSession().getAttribute("customer_id");
        String customerName = (String) httpServletRequest.getSession().getAttribute("customer_name");

        /*
        long solarSystemId = 1;

        if (allRequestParams.get("solarSystemId") != null){
            solarSystemId = Integer.parseInt(allRequestParams.get("solarSystemId"));
        }

        List<SolarSystem> solarSystemsList = queryHandler.getAllSolarSystem();
        List<Planet> planetListBySolarSystem = queryHandler.getPlanetsBySolarSystemId(solarSystemId);
        List<Planet> allPlanet = queryHandler.getAllPlanet();
		*/
        
        model.addAttribute("loggedIn", customerId != null);
        model.addAttribute("customername", customerName);
        /*model.addAttribute("solarsystems",solarSystemsList);
        model.addAttribute("planets", planetListBySolarSystem);
        model.addAttribute("allplanet",allPlanet);*/

    }
    
}
