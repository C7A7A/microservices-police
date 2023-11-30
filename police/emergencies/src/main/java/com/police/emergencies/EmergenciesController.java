package com.police.emergencies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.police.basedomains.Event;
import com.police.emergencies.data.Emergency;
import com.police.emergencies.data.Type;
import com.police.emergencies.kafka.EmergencyProducer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView emergencies(@ModelAttribute Emergency emergency) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        String result = emergenciesService.getEmergencyResult(emergency);
        modelAndView.addObject("result", result);

        return modelAndView;
    }

    @PostMapping("/testKafka")
    public String testKafka(@RequestBody Event event) throws JsonProcessingException {
        emergencyProducer.sendMessage(event);

        return "Event sent successfully";
    }

}
