/*
 * Copyright 2018 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package roboto.examples.springboot.controller;

import com.github.gregwhitaker.roboto.spring.RobotoController;
import com.github.gregwhitaker.roboto.spring.annotation.AllowRobots;
import com.github.gregwhitaker.roboto.spring.annotation.DenyRobots;
import org.springframework.beans.factory.annotation.Autowired;
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
