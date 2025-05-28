package org.example.eksamensprojektbilabonnement.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForsideController
{
    @GetMapping("/skadeForside")
    public String skadeForside(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn != null && loggedIn) {
            return "skadeForside"; //logged in
        } else {
            return "redirect:/login"; // ikki godtaget
        }
    }

    @GetMapping("/dataForside")
    public String dataForside(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn != null && loggedIn) {
            return "dataForside";
        } else {
            return "redirect:/login";
        }
    }

}
