package org.example.eksamensprojektbilabonnement.Service;

import org.example.eksamensprojektbilabonnement.Model.Bil;
import org.example.eksamensprojektbilabonnement.Repository.BilRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BilService {
    @Autowired
    private BilRepo bilRepo;

    // hent alle biler
    public List<Bil> hentAlleBiler() {
        return bilRepo.hentAlleBiler();
    }

    // hent bil med id
    public Optional <Bil> hentBilMedId(Long id) {
        return bilRepo.hentBilMedId(id);
    }

    // opret bil
    public void opretBil(Bil bil) {
        bilRepo.opretBil(bil);
    }

    // opdater bil
    public void opdaterBil(Bil bil) {
        bilRepo.opdaterBil(bil);
    }

    // slet bil
    public void sletBil(Long id) {
        bilRepo.sletBil(id);
    }


    //giver antalet af biler
    public long getAntalBiler() {
        return bilRepo.hentAlleBiler().size();
    }

    public BigDecimal getSamletPris() {
        return bilRepo.hentAlleBiler().stream()
                .map(Bil::getPris)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
