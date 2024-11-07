package com.fishingLog.spring.controller;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.service.AnglerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/anglers")
public class AnglerController {
    private final AnglerService anglerService;
    @Autowired
    public AnglerController(AnglerService anglerService) {
        this.anglerService = anglerService;
    }
    @GetMapping
    public List<Angler> getAllAnglers() {
        return anglerService.findAllAnglers();
    }
    @GetMapping("/{id}")
    public Optional<Angler> getAnglerById(@PathVariable("id") Long id) {
        return anglerService.findAnglerById(id);
    }
    @PostMapping
    public Angler saveAngler(@RequestBody Angler angler) {
        return anglerService.saveAngler(angler);
    }
    @PutMapping(path = "/{id}")
    public void updateAngler(@PathVariable Long id, @RequestBody Angler angler) {
        Optional<Angler> current = anglerService.findAnglerById(id);
        if (null != current) {
            angler.setId(id);
            anglerService.saveAngler(angler);
        }
    }
    @DeleteMapping(path = "/{id}")
    public void deleteAngler(@PathVariable Long id) {
        anglerService.deleteAngler(id);
    }
}
