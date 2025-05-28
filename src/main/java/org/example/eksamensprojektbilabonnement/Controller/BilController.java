package org.example.eksamensprojektbilabonnement.Controller;

import org.example.eksamensprojektbilabonnement.Model.Bil;
import org.example.eksamensprojektbilabonnement.Repository.BilRepo;
import org.example.eksamensprojektbilabonnement.Service.BilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BilController {
    @Autowired
    private BilService bilService;

    @Autowired
    private BilRepo bilRepo;

    @GetMapping("/bil")
    public String showBilForm(Model model) {
        model.addAttribute("bil", new Bil());
        return "bil";
    }

    @PostMapping("/bil")
    public String opretBil(@ModelAttribute Bil bil) {
        bilRepo.opretBil(bil);
        return "redirect:/bilListe";
    }

    @GetMapping("/bilListe")
    public String showBilListe(Model model) {
        model.addAttribute("bilListe", bilRepo.hentAlleBiler());
        return "bilListe";
    }

    @GetMapping("/bil/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Bil bil = bilRepo.hentBilMedId(id).orElseThrow();
        model.addAttribute("bil", bil);
        return "redigerBil";
    }

    @PostMapping("/bil/update")
    public String updateBil(@ModelAttribute Bil bil) {
        bilRepo.opdaterBil(bil);
        return "redirect:/bilListe";
    }

    @GetMapping("/bil/delete/{id}")
    public String deleteBil(@PathVariable long id) {
        bilRepo.sletBil(id);
        return "redirect:/bilListe";
    }
}
