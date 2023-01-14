package tn.ipsas.factureservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tn.ipsas.coremodels.dto.FactureReglementDTO;
import tn.ipsas.coremodels.exceptions.EntityNotFoundException;
import tn.ipsas.coremodels.models.facture.Facture;
import tn.ipsas.coremodels.models.reglement.ReglementItem;
import tn.ipsas.factureservice.data.FactureRepository;
import tn.ipsas.factureservice.feign.ReglementFeign;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactureService {
    private final FactureRepository repository;
    private final ReglementFeign reglementFeign;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FactureService(FactureRepository repository, ReglementFeign reglementFeign, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.reglementFeign = reglementFeign;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Page<FactureReglementDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(facture -> {
            List<ReglementItem> reglementItems = reglementFeign.byFacture(facture.getId());
            FactureReglementDTO dto = new FactureReglementDTO();
            dto.setFacture(facture);
            dto.setItems(reglementItems);
            return dto;
        });
    }

    public List<Facture> getAll() {
        return repository.findAll();
    }

    public List<FactureReglementDTO> getAllByClientId(String clientId, boolean onlyNotPaid) {
        return repository
                .findAllByClientId(clientId)
                .stream()
                .map(facture -> {
                    List<ReglementItem> reglementItems = reglementFeign.byFacture(facture.getId());
                    FactureReglementDTO dto = new FactureReglementDTO();
                    dto.setFacture(facture);
                    dto.setItems(reglementItems);
                    return dto;
                }).filter(factureReglementDTO -> {
                    if (onlyNotPaid) {
                        return factureReglementDTO.getNeedToPay() > 0;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    public Facture getById(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Facture save(Facture facture) {
        boolean add = facture.getId() == null;
        Facture factureSaved = repository.save(facture);
        if (add) {
            kafkaTemplate.send("facture_add", factureSaved);
        }
        return factureSaved;
    }

    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        repository.deleteById(id);
    }

    public boolean exists(String id) {
        return repository.existsById(id);
    }


}
