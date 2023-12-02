package com.police.emergencies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.police.basedomains.Event;
import com.police.basedomains.EventType;
import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.vehicles.GetVehicleListResponse;
import com.police.emergencies.data.Emergency;
import com.police.emergencies.data.PolicemenVehicles;
import com.police.emergencies.data.Type;
import com.police.emergencies.kafka.EmergencyProducer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmergenciesController {
    private final EmergenciesService emergenciesService;
    private final EmergencyProducer emergencyProducer;

    public EmergenciesController(EmergenciesService emergenciesService, EmergencyProducer emergencyProducer) {
        this.emergenciesService = emergenciesService;
        this.emergencyProducer = emergencyProducer;
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        List<String> types = Arrays.stream(Type.values())
                        .map(Type::name)
                        .toList();

        modelAndView.addObject("emergency", new Emergency());
        modelAndView.addObject("types", types);

        return modelAndView;
    }

    @PostMapping("/emergencies")
    public ModelAndView emergencies(@ModelAttribute Emergency emergency) throws JsonProcessingException {
        emergenciesForm(emergency);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        String result = emergenciesService.getEmergencyResult(emergency);
        modelAndView.addObject("result", result);

        return modelAndView;
    }

    @PostMapping("/emergenciesAPI")
    public void emergenciesAPI(@RequestBody Event event) throws JsonProcessingException {
        emergencyProducer.sendMessage(event, false);
    }

    @PostMapping("/emergenciesForm")
    public void emergenciesForm(@ModelAttribute Emergency emergency) throws JsonProcessingException {
        String result = emergenciesService.getEmergencyResult(emergency);
        Event event = new Event(EventType.EMERGENCY_ACCEPTED, result);
        emergencyProducer.sendMessage(event, false);
    }

    @GetMapping("/fullResult")
    public ModelAndView showFullResult(@RequestParam("payloadId") String payloadId) {
        System.out.println("Received payloadId: " + payloadId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fullResult");

        PolicemenVehicles policemenVehicles = emergenciesService.getFullEmergencyResult(payloadId);

        List<EquippedPoliceman> equippedPolicemen = new ArrayList<>();
        GetVehicleListResponse vehicleList = new GetVehicleListResponse();

        if (policemenVehicles != null) {
            equippedPolicemen = policemenVehicles.getEquippedPolicemen();
            vehicleList = policemenVehicles.getVehicles();
        }

        modelAndView.addObject("equippedPolicemen", equippedPolicemen);
        modelAndView.addObject("vehicleList", vehicleList);

        return modelAndView;
    }

}
