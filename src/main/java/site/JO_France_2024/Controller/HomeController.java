package site.JO_France_2024.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home(Model model) {
        model.addAttribute("title", "Accueil - JO 2024");
        return "index";
    }
}
