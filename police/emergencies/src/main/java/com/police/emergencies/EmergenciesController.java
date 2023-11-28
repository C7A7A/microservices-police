package com.police.emergencies;

import com.police.emergencies.data.Emergency;
import com.police.emergencies.data.Type;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmergenciesController {
    private final EmergenciesService emergenciesService;

    public EmergenciesController(EmergenciesService emergenciesService) {
        this.emergenciesService = emergenciesService;
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

}
