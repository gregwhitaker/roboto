package roboto.examples.springboot.controller;

import com.github.gregwhitaker.roboto.spring.annotation.DisallowRobots;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@DisallowRobots(userAgents = { "google" })
public class BazController {

    // SEO is disabled for this endpoint for "google" bot only.
    @GetMapping("/baz/1")
    public String baz1(Model model) {
        model.addAttribute("message", "This is Baz1");
        return "baz";
    }
}
