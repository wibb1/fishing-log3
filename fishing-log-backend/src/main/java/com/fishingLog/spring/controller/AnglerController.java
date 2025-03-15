package com.fishingLog.spring.controller;

import com.fishingLog.spring.dto.AnglerDTO;
import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.service.AnglerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/v1/anglers")
public class AnglerController {
    private final AnglerService anglerService;

    @Autowired
    public AnglerController(AnglerService anglerService) {
        this.anglerService = anglerService;
    }

    @GetMapping
    public List<AnglerDTO> getAllAnglers() {
        List<Angler> anglers = anglerService.findAllAnglers();
        return anglers.stream()
                .map(angler -> new AnglerDTO(angler, false))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<AnglerDTO> getAnglerById(@PathVariable("id") Long id,
                                             @RequestParam(value = "includeRoles", defaultValue = "true") boolean includeRoles,
                                             @RequestParam(value = "includeRecords", defaultValue = "true") boolean includeRecords) {
        Optional<Angler> angler = anglerService.findAnglerById(id);
        return Optional.of(new AnglerDTO(angler.get(), includeRecords));
    }

    @PostMapping
    public AnglerDTO saveAngler(@RequestBody Angler angler,
                                @RequestParam(value = "includeRoles", defaultValue = "true") boolean includeRoles,
                                @RequestParam(value = "includeRecords", defaultValue = "true") boolean includeRecords) {
        return new AnglerDTO(anglerService.saveAngler(angler), includeRecords);
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
