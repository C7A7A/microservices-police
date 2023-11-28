package com.police.vehiclesui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.net.URL;

@Controller
public class VehicleController {

    @Autowired
    private VehicleClient vehicleClient;

    private static final Logger log = LoggerFactory.getLogger(VehicleClient.class);

    @GetMapping("/getVehicle")
    public String getVehicleForm(Model model) {
        GetVehicleRequest request = new GetVehicleRequest();

        model.addAttribute("getVehicleRequest", request);
        return "getVehicle";
    }

    @PostMapping("/getVehicle")
    public String getVehicle(@ModelAttribute GetVehicleRequest request, Model model) {
        log.info("Requesting for vehicle " + request.getName());

        GetVehicleResponse response = vehicleClient.getVehicle(request.getName());
        model.addAttribute("vehicleInfo", response.getVehicle());
        return "vehicleInfo";
    }

    @GetMapping("/getVehicles")
    public String getVehicleListForm(Model model) {
        GetVehicleListRequest request = new GetVehicleListRequest();

        model.addAttribute("getVehicleListRequest", request);
        return "getVehicleList";
    }


    @PostMapping("/getVehicles")
    public String getVehicleList(@ModelAttribute GetVehicleListRequest request, Model model) {
        log.info("Requesting for vehicle type " + request.getName());

        GetVehicleListResponse response = vehicleClient.getVehicleList(request.getName());
        model.addAttribute("vehicleListInfo", response.getVehicles());
        return "vehicleListInfo";
    }
}