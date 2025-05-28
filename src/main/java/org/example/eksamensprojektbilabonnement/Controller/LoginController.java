package org.example.eksamensprojektbilabonnement.Controller;

import org.example.eksamensprojektbilabonnement.Service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

   @GetMapping("/login")
    public String login() {
       return "login";
   }

   @PostMapping("/login")
    public String loginformation(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
       if (loginService.login(username, password)) {
           session.setAttribute("loggedIn", true);
           session.setAttribute("username", username);
           return "redirect:/";
       } else {
           model.addAttribute("error", "Invalid username or password");
           return "login";
       }
    }

    @GetMapping("/nulstilkode")
    public String reset() {
        return "nulstilkode";
   }

   @GetMapping("/protected")
    public String protectedpage(HttpSession session) {
        boolean loggedIn = (boolean) session.getAttribute("loggedIn");
        if (loggedIn) {
            return "protectedPage";
        } else {
            return "redirect:/";
        }
   }

   @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
   }
}
