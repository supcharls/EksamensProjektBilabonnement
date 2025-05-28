package org.example.eksamensprojektbilabonnement.Controller;

import org.example.eksamensprojektbilabonnement.Model.Bil;
import org.example.eksamensprojektbilabonnement.Model.Kunder;
import org.example.eksamensprojektbilabonnement.Model.LejeAftale;
import org.example.eksamensprojektbilabonnement.Repository.BilRepo;
import org.example.eksamensprojektbilabonnement.Repository.KunderRepo;
import org.example.eksamensprojektbilabonnement.Repository.LejeAftaleRepo;
import org.example.eksamensprojektbilabonnement.Service.LejeAftaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;

@Controller
public class LejeAftaleController {
    @Autowired
    private LejeAftaleService service;

    @Autowired
    private BilRepo bilRepo;

    @Autowired
    private KunderRepo kunderRepo;

    @Autowired
    private LejeAftaleRepo lejeAftaleRepo;

    @GetMapping("/lejeAftale")
    public String showLejeAftaleForm(Model model) {
        model.addAttribute("lejeAftale", new LejeAftale());
        model.addAttribute("kunder", kunderRepo.hentAlleKunder());
        model.addAttribute("biler", bilRepo.hentAlleBiler());
        return "lejeAftale";
    }

    @PostMapping("/lejeAftale")
    public String opretAftale(@RequestParam Long kundeId,
                              @RequestParam Long bilId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDato,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate slutDato, RedirectAttributes redirectAttributes) {
        Optional<Kunder> kunde = kunderRepo.hentKundeMedId(kundeId);
        Optional<Bil> bil = bilRepo.hentBilMedId(bilId);

        if (kunde.isEmpty() || bil.isEmpty()) {
            return "redirect:/lejeAftale?error";
        }

        try {
            LejeAftale leje = new LejeAftale();
            leje.setKundeId(kunde.get().getKundeId());
            leje.setBilId(bil.get().getBilId());
            leje.setStartDato(startDato);
            leje.setSlutDato(slutDato);

            service.opretLejeaftale(leje);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/lejeAftale?error";
        }

        return "redirect:/lejeAftaleListe";
    }

    @GetMapping("/lejeAftaleListe")
    public String visAlle(Model model) {
        List<LejeAftale> lejeaftaler = service.hentAlleLejeaftaler();

        List<Map<String, Object>> enrichedLejeaftaler = new ArrayList<>();

        for (LejeAftale l : lejeaftaler) {
            Map<String, Object> row = new HashMap<>();
            row.put("lejeaftale", l);
            row.put("kunde", kunderRepo.hentKundeMedId(l.getKundeId()).orElse(null));
            row.put("bil", bilRepo.hentBilMedId(l.getBilId()).orElse(null));
            enrichedLejeaftaler.add(row);
        }

        model.addAttribute("lejeAftaler", enrichedLejeaftaler);
        return "lejeAftaleListe";
    }



    @GetMapping("/lejeAftale/slet/{id}")
    public String sletLejeaftale(@PathVariable Long id) {
        service.sletLejeAftale(id);
        return "redirect:/lejeAftaleListe";
    }


    @GetMapping("/lejeAftale/rediger/{id}")
    public String redigerForm(@PathVariable Long id, Model model) {
        LejeAftale aftale = service.hentLejeaftaleMedId(id);
        model.addAttribute("lejeaftale", aftale);
        model.addAttribute("kunder", kunderRepo.hentAlleKunder());
        model.addAttribute("ledigeBiler", bilRepo.findBilerDerRedigeres(aftale.getBilId()));
        return "redigerLejeAftale";
    }

    @PostMapping("/lejeAftale/update")
    public String updateLejeAftale(@ModelAttribute LejeAftale lejeAftale) {
        service.opdaterLejeaftale(lejeAftale);
        return "redirect:/lejeAftaleListe";
    }
}