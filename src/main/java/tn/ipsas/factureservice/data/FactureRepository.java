package tn.ipsas.factureservice.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.ipsas.coremodels.models.facture.Facture;

import java.util.List;

public interface FactureRepository extends MongoRepository<Facture, String> {
    List<Facture> findAllByClientId(String id);
}
