package org.vrin.nons;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    @GetMapping("/")
    public String getSite(Model model){
        model.addAttribute("title","mainPage");
        return "nons.html";
    }
}
