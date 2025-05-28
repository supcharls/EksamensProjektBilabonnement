package org.example.eksamensprojektbilabonnement.Controller;

import org.example.eksamensprojektbilabonnement.Model.Fejl;
import org.example.eksamensprojektbilabonnement.Model.TilstandsRapport;
import org.example.eksamensprojektbilabonnement.Repository.*;
import org.example.eksamensprojektbilabonnement.Service.TilstandsrapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class TilstandsrapportController
{

    private final TilstandsrapportService tilstandsrapportService;
    private final LejeAftaleRepo lejeAftaleRepo;
    private final BilRepo bilRepo;
    private final KunderRepo kunderRepo;
    private final TilstandsrapportRepo tilstandsrapportRepo;
    private final FejlRepo fejlRepo;

    @Autowired
    public TilstandsrapportController(TilstandsrapportService tilstandsrapportService, LejeAftaleRepo lejeAftaleRepo, BilRepo bilRepo, KunderRepo kunderRepo, TilstandsrapportRepo tilstandsrapportRepo, FejlRepo fejlRepo)
    {
        this.tilstandsrapportService = tilstandsrapportService;
        this.lejeAftaleRepo = lejeAftaleRepo;
        this.bilRepo = bilRepo;
        this.kunderRepo = kunderRepo;
        this.tilstandsrapportRepo = tilstandsrapportRepo;
        this.fejlRepo = fejlRepo;
    }


    @GetMapping("/tilstandsrapport")
    public String visTilstand(Model model) {
        TilstandsRapport rapport = new TilstandsRapport();
        model.addAttribute("rapport", rapport);
        model.addAttribute("lejeaftaler", lejeAftaleRepo.hentAlleLejeaftaler());
        model.addAttribute("fejlValgmuligheder", fejlRepo.hentAlleFejl());
        return "tilstandsrapport";
    }

    @PostMapping("/tilstandsrapport")
    public String gem(@ModelAttribute TilstandsRapport rapport) {
        rapport.setDato(LocalDate.now());
        tilstandsrapportRepo.opretTilstandsrapport(rapport);

        for (Fejl fejl : rapport.getFejlListe()) {
            if (fejl.getFejlId() != null) {
                Fejl original = fejlRepo.hentFejlMedId(fejl.getFejlId());

                Fejl kopi = new Fejl();
                kopi.setBeskrivelse(original.getBeskrivelse());
                kopi.setPris(original.getPris());
                kopi.setTilstandsrapport(rapport.getTilstandsrapportId());

                fejlRepo.opretFejl(kopi);
            }
        }

        return "redirect:/tilstandsrapport";
    }

}
