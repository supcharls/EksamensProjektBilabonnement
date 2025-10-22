package org.example.eksamensprojektbilabonnement.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamensprojektbilabonnement.Repository.BilRepo;
import org.example.eksamensprojektbilabonnement.Repository.LejeAftaleRepo;
import org.example.eksamensprojektbilabonnement.Service.BilService;
import org.example.eksamensprojektbilabonnement.Service.LejeAftaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForretningUdviklerController {
    @Autowired
    private BilService bilService;

    @Autowired
    private LejeAftaleService lejeAftaleService;

    @Autowired
    private BilRepo bilRepo;

    @Autowired
    private LejeAftaleRepo lejeAftaleRepo;

    @GetMapping("/foretUdviklForside")
    public String foretUdviklForside(Model model, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            return "redirect:/login";
        }

        model.addAttribute("antalBiler", bilService.getAntalBiler());
        model.addAttribute("samletPris", bilService.getSamletPris());
        model.addAttribute("samletBilerLejetUd", lejeAftaleService.getAntalLejetBiler());
        model.addAttribute("samletPrisForUdlejetBiler", lejeAftaleService.getSamletPrisForUdlejetBiler());

        return "foretUdviklForside";
    }

}