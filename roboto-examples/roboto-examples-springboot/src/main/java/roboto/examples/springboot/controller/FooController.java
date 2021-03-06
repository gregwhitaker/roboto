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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Example controller with all methods included in robots.txt, except for a single method
 * that is annotated with {@link PostMapping}.
 */
@Controller
public class FooController {

    @GetMapping("/foo/1")
    public String foo1(Model model) {
        model.addAttribute("message", "This is Foo1");
        return "foo";
    }

    @GetMapping("/foo/2")
    public String foo2(Model model) {
        model.addAttribute("message", "This is Foo2");
        return "foo";
    }

    // Only GET methods are included in SEO
    @PostMapping("/foo/3")
    public String foo3(Model model) {
        return "foo";
    }
}
