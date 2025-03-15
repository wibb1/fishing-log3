package com.fishingLog.spring.viewcontroller;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.service.AnglerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.Collections;

@Controller
public class AnglerViewController {
    private final AnglerService anglerService;

    @Autowired
    public AnglerViewController(AnglerService anglerService) {
        this.anglerService = anglerService;
    }

    @GetMapping("/signup2")
    public String showSignUpForm(Model model) {
        model.addAttribute("angler", new Angler());
        return "signup";
    }

    @PostMapping("/signup2")
    public String signUp(@ModelAttribute Angler angler) {
        Instant time = Instant.now();
        angler.setCreatedAt(time);
        angler.setUpdatedAt(time);
        angler.setRoles(Collections.singletonList("ANGLER"));
        anglerService.saveAngler(angler);
        return "redirect:/login";
    }
}
