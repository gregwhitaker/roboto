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

package com.github.gregwhitaker.roboto.spring;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller responsible for creating and hosting the robots.txt and sitemap files.
 */
@Controller
public class RobotoController {

    private final RobotoMapper mapper;

    public RobotoController(RobotoMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Handles requests for robots.txt file.
     *
     * @return a {@link ResponseEntity} containing the robots.txt file.
     */
    @RequestMapping(value = { "/robots.txt" },
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> robots(HttpServletRequest request) {
        return ResponseEntity.ok(RobotsResponse.create(request, mapper));
    }

    /**
     * Handles requests for sitemap.xml file.
     *
     * @return a {@link ResponseEntity} containing the sitemap.xml file.
     */
    @RequestMapping(value = { "/sitemap.xml" },
                    produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public SitemapResponse.XmlUrlSet sitemap(HttpServletRequest request) {
        return SitemapResponse.create(request, mapper);
    }
}
