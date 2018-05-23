package roboto.examples.springboot.controller;

import com.github.gregwhitaker.roboto.spring.annotation.AllowRobots;
import com.github.gregwhitaker.roboto.spring.annotation.DenyRobots;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Example controller with all methods excluded from robots.txt, except for a single method
 * with an {@link AllowRobots} annotation.
 */
@Controller
@DenyRobots
public class BarController {

    // SEO is disabled for this endpoint due to the class-level @DenySEO annotation
    @GetMapping("/bar/1")
    public String bar1(Model model) {
        model.addAttribute("message", "This is Bar1");
        return "bar";
    }

    // SEO is disabled for this endpoint due to the class-level @DenySEO annotation
    @GetMapping("/bar/2")
    public String bar2(Model model) {
        model.addAttribute("message", "This is Bar2");
        return "bar";
    }

    // @AllowSEO annotations on the method level override @DenySEO annotations at the class level
    @GetMapping("bar/3")
    @AllowRobots
    public String bar3(Model model) {
        model.addAttribute("message", "This is Bar3");
        return "bar";
    }
}
