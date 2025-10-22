package org.example.eksamensprojektbilabonnement.Service;

import org.example.eksamensprojektbilabonnement.Model.Bil;
import org.example.eksamensprojektbilabonnement.Model.Kunder;
import org.example.eksamensprojektbilabonnement.Model.LejeAftale;
import org.example.eksamensprojektbilabonnement.Repository.BilRepo;
import org.example.eksamensprojektbilabonnement.Repository.KunderRepo;
import org.example.eksamensprojektbilabonnement.Repository.LejeAftaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LejeAftaleService
{
    @Autowired
    private LejeAftaleRepo lejeAftaleRepo;
    @Autowired
    private KunderRepo kunderRepo;
    @Autowired
    private BilRepo bilRepo;

    public LejeAftaleService(LejeAftaleRepo lejeAftaleRepo, KunderRepo kunderRepo, BilRepo bilRepo) {
        this.lejeAftaleRepo = lejeAftaleRepo;
        this.kunderRepo = kunderRepo;
        this.bilRepo = bilRepo;
    }

    // opret lejeaftaler
    public void opretLejeaftale(LejeAftale lejeAftale) {
        Bil bil = bilRepo.hentBilMedId(lejeAftale.getBilId()).orElseThrow(() ->
                new RuntimeException("Bilen blev ikke fundet"));

        if ("Udlejet".equalsIgnoreCase(bil.getBilStatus())) {
            throw new RuntimeException("Bilen er allerede Udlejet");
        }

        lejeAftaleRepo.opretLejeaftale(lejeAftale);
        bil.setBilStatus("Udlejet");
        bilRepo.opdaterBil(bil);
    }

    //
    public Map<Long, LejeAftale> hentLejeaftaler()
    {
        List<LejeAftale> lejeaftaler = lejeAftaleRepo.hentAlleLejeaftaler();

        Map<Long, LejeAftale> map = new HashMap<>();
        for(LejeAftale l : lejeaftaler)
        {
            map.put(l.getLejeaftaleId(), l);
        }

        return map;
    }

    // controlleren kan foretage listevisning
    public List<LejeAftale> hentAlleLejeaftaler()
    {
        return lejeAftaleRepo.hentAlleLejeaftaler();
    }

    // hent lejeaftale til redigering
    public LejeAftale hentLejeaftaleMedId(Long id)
    {
        return lejeAftaleRepo.hentLejeaftaleMedId(id);
    }

    // opdatere en aftale
    public void opdaterLejeaftale(LejeAftale lejeAftale)
    {
        lejeAftaleRepo.opdaterLejeaftale(lejeAftale);
    }

    // Viser fornavn, efternavn og email i steden for ids
    public List<Map<String, Object>> hentLejeAftalerTilVisning(){
        List<LejeAftale> lejeAftaler =lejeAftaleRepo.hentAlleLejeaftaler();

        Map<Long, Kunder> kunder = kunderRepo.hentAlleKunder().stream()
                .collect(Collectors.toMap(Kunder::getKundeId, k -> k));

        Map<Long, Bil> biler = bilRepo.hentAlleBiler().stream()
                .collect(Collectors.toMap(Bil::getBilId, b -> b));

        List<Map<String, Object>> visningListe = new ArrayList<>();

        for (LejeAftale l : lejeAftaler) {
            Map<String, Object> map = new HashMap<>();
            map.put("lejeAftale", l);
            map.put("kunde", l.getKundeId());
            map.put("bil", l.getBilId());
            visningListe.add(map);
        }
        return visningListe;
    }

    public long getAntalLejetBiler(){
        return lejeAftaleRepo.hentAlleLejeaftaler().size();
    }

    public void sletLejeAftale(long lejeAftaleId) {
        LejeAftale lejeAftale = lejeAftaleRepo.hentLejeaftaleMedId(lejeAftaleId);
        Bil bil = bilRepo.hentBilMedId(lejeAftale.getBilId()).orElseThrow(() ->
                        new RuntimeException("Bilen blev ikke fundet"));

        lejeAftaleRepo.sletLejeaftale(lejeAftaleId);

        bil.setBilStatus("Klar");
        bilRepo.opdaterBil(bil);
    }

    public BigDecimal getSamletPrisForUdlejetBiler() {
        return bilRepo.hentAlleBiler().stream().filter(bil -> "Udlejet".equalsIgnoreCase(bil.getBilStatus()))
                .map(Bil::getPris)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}