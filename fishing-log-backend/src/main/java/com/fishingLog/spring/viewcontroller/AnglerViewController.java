package com.fishingLog.spring.viewcontroller;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.service.AnglerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;

@Controller
public class AnglerViewController {
    private final AnglerService anglerService;

    @Autowired
    public AnglerViewController(AnglerService anglerService) {
        this.anglerService = anglerService;
    }

    @GetMapping("/signup2")
    public String showSignUp2Form(Model model) {
        model.addAttribute("angler", new Angler());
        return "signup2";
    }

    @PostMapping("/signup2")
    public String signUp2(@ModelAttribute Angler angler) {
        Instant time = Instant.now();
        angler.setCreatedAt(time);
        angler.setUpdatedAt(time);
        angler.setRole("ANGLER");
        anglerService.saveAngler(angler);
        return "redirect:/login2";
    }

    @GetMapping("/anglers/{id}")
    public String getAnglerView(@PathVariable("id") Long id, Model model) {
        Angler angler = anglerService.findAnglerById(id).orElseThrow(() -> new IllegalArgumentException("Invalid angler Id:" + id));
        model.addAttribute("angler", angler);
        return "angler";
    }
}
