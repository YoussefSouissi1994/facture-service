package tn.ipsas.factureservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.ipsas.coremodels.exceptions.EntityNotFoundException;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.factureservice.data.FactureRepository;

@Service
public class FactureService {
    private final FactureRepository repository;

    public FactureService(FactureRepository repository) {
        this.repository = repository;
    }
    public Page<Facture> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Facture getById(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }
    public Facture save(Facture facture) {
        Facture factureSaved = repository.save(facture);
        return factureSaved;
    }
    public void delete(String id) {
        Facture facture = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        repository.deleteById(id);
    }
    public boolean exists(String id) {
        return repository.existsById(id);
    }


}
