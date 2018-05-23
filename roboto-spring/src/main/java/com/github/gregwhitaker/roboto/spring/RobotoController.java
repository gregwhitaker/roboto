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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for creating and hosting the robots.txt and sitemap files.
 */
@Controller("RobotoController")
public class RobotoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotoController.class);

    private final RobotsService robotsService;
    private final SitemapService sitemapService;

    @Autowired
    public RobotoController(RobotsService robotsService, SitemapService sitemapService) {
        this.robotsService = robotsService;
        this.sitemapService = sitemapService;
    }

    /**
     * Handles requests for robots.txt file.
     *
     * @return
     */
    @RequestMapping(value = { "/robots", "/robots.txt", "/robot", "/robot.txt" })
    public ResponseEntity robots() {
        return null;
    }

    /**
     * Handles requests for sitemap.xml file.
     *
     * @return
     */
    @RequestMapping(value = { "/sitemap.xml" },
                    produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity sitemapXml() {
        return null;
    }

    /**
     * Handles requests for sitemap.txt file.
     *
     * @return
     */
    @RequestMapping(value = { "/sitemap.txt" })
    public ResponseEntity sitemapText() {
        return null;
    }
}
