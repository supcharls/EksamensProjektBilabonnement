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
public class BilController
{
    // injection af bilservice der håndterer forretningslogik
    @Autowired
    private BilService bilService;

    // injection af bilrepo som håndterer databaseoperationerne
    @Autowired
    private BilRepo bilRepo;


    // visning af formular for bil
    @GetMapping("/bil")
    public String showBilForm(Model model)
    {
        model.addAttribute("bil", new Bil());
        return "bil";
    }

    // håndtering af oprettelse af bil via formular
    @PostMapping("/bil")
    public String opretBil(@ModelAttribute Bil bil)
    {
        bilRepo.opretBil(bil);
        return "redirect:/bilListe";
    }

    // visning af bil liste side
    @GetMapping("/bilListe")
    public String showBilListe(Model model)
    {
        model.addAttribute("bilListe", bilRepo.hentAlleBiler());
        return "bilListe";
    }

    // visning af redigering for bil info formular siden
    @GetMapping("/bil/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model)
    {
        Bil bil = bilRepo.hentBilMedId(id).orElseThrow();
        model.addAttribute("bil", bil);
        return "redigerBil";
    }

    // håndtering af opdatering for bil info
    @PostMapping("/bil/update")
    public String updateBil(@ModelAttribute Bil bil)
    {
        bilRepo.opdaterBil(bil);
        return "redirect:/bilListe";
    }


    // sletning af bil baseret på ID
    @GetMapping("/bil/delete/{id}")
    public String deleteBil(@PathVariable long id)
    {
        bilRepo.sletBil(id);
        return "redirect:/bilListe";
    }

}
