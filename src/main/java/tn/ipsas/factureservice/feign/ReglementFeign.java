package tn.ipsas.factureservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.ipsas.coremodels.models.reglement.ReglementItem;

import java.util.List;

@FeignClient(name = "reglement-service", path = "reglement")
public interface ReglementFeign {
    @GetMapping("byFacture/{factureId}")
    List<ReglementItem> byFacture(@PathVariable("factureId") String factureId);
}
