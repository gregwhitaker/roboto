# roboto
[![Build Status](https://travis-ci.org/gregwhitaker/roboto.svg?branch=master)](https://travis-ci.org/gregwhitaker/roboto)

Module for Spring Boot applications that automatically generates [robots.txt](http://www.robotstxt.org/) and [sitemap.xml](https://www.sitemaps.org/protocol.html) files based on standard 
controller annotations.

## How does it work?
1. Simply annotate your main Spring application class with the `@EnableRoboto` annotation like so:

        @SpringBootApplication
        @EnableRoboto
        public class Application {
        
            public static void main(String... args) {
                SpringApplication.run(Application.class, args);
            }
        }

2. Next, use the `@DisallowRobots` annotation to mark methods and controllers that you would like to be excluded from search engine indexing.

        @Controller
        @DisallowRobots
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
        }
        
    Or
    
        @Controller
        public class FooController {
        
            @GetMapping("/foo/1")
            @DisallowRobots
            public String foo1(Model model) {
                model.addAttribute("message", "This is Foo1");
                return "foo";
            }
            
            @GetMapping("/foo/2")
            public String foo2(Model model) {
                model.addAttribute("message", "This is Foo2");
                return "foo";
            }
        }

## Getting Roboto
Roboto libraries are available via JCenter.

* [roboto-springboot](https://bintray.com/gregwhitaker/maven/roboto-springboot) - Use this library if you are integrating with Spring Boot.

## Examples
Please see the included [example projects](roboto-examples) for demonstrations on how to configure and use Roboto.

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/gregwhitaker/roboto/issues).

## License
Copyright 2018 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
