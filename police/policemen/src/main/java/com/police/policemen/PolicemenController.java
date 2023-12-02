package com.police.policemen;

import com.police.basedomains.policemen.EquippedPoliceman;
import com.police.basedomains.policemen.Policeman;
import com.police.basedomains.policemen.Weapon;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class PolicemenController {
    private final PolicemenService policemenService;

    public PolicemenController(PolicemenService policemenService) {
        this.policemenService = policemenService;
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/policemen")
    public ModelAndView showPolicemen() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("policemen");

        List<Policeman> policemen = policemenService.getPolicemen();
        modelAndView.addObject("policemenList", policemen);

        return modelAndView;
    }

    @GetMapping("/weapons")
    public ModelAndView showWeapons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("weapons");

        List<Weapon> weapons = policemenService.getWeapons();
        modelAndView.addObject("weaponList", weapons);

        return modelAndView;
    }

    @GetMapping("/preparePolicemen")
    public ModelAndView showPreparePolicemen() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("preparePolicemen");

        return modelAndView;
    }

    @PostMapping("/preparePolicemen")
    public ModelAndView preparePolicemen(@RequestParam String threat) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("preparedPolicemen");

        List<EquippedPoliceman> equippedPolicemen = policemenService.preparePolicemen(threat);
        modelAndView.addObject("equippedPolicemen", equippedPolicemen);
        modelAndView.addObject("threat", threat);

        return modelAndView;
    }

}
