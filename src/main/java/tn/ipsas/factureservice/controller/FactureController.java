package tn.ipsas.factureservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tn.ipsas.coremodels.dto.FactureReglementDTO;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.factureservice.service.FactureService;

import java.util.List;

@RestController
@RequestMapping
public class FactureController {
    private final FactureService service;

    public FactureController(FactureService service) {
        this.service = service;
    }
    @GetMapping
    public Page<FactureReglementDTO> page(Pageable pageable) {
        return service.getAll(pageable);
    }
    @GetMapping("all")
    public List<Facture> all() {
        return service.getAll();
    }

    @GetMapping("byClient/{clientId}")
    public List<FactureReglementDTO> byClient(
            @PathVariable("clientId") String clientId,
            @RequestParam(value = "onlyNotPaid", required = false, defaultValue = "false") boolean onlyNotPaid) {
        return service.getAllByClientId(clientId, onlyNotPaid);
    }
    @GetMapping("{id}")
    public Facture byId(@PathVariable("id") String id) {
        return service.getById(id);
    }
    @PutMapping
    public Facture add(@RequestBody Facture facture) {
        facture.setId(null);
        return service.save(facture);
    }
    @PutMapping("{id}")
    public Facture update(@PathVariable("id") String id, @RequestBody Facture facture) {
        if (!service.exists(id)) {
            throw new IllegalArgumentException();
        }
        facture.setId(id);
        return service.save(facture);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        if (!service.exists(id)) {
            throw new IllegalArgumentException();
        }
        service.delete(id);
    }

}

