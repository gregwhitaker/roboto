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

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Builds responses for robots.txt and sitemap.xml files based on the resources found by {@link RobotoMapper}.
 */
public class RobotoResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotoResponse.class);

    private static Map<RobotoMapper, String> ROBOTS = new HashMap<>();
    private static Map<RobotoMapper, String> SITEMAP = new HashMap<>();

    public static String robots(HttpServletRequest request, RobotoMapper mapper) {
        return ROBOTS.computeIfAbsent(mapper, robots -> {
            Map<String, Set<String>> disallowed = mapper.getDisallowed();

            StringBuilder builder = new StringBuilder();
            for(Map.Entry<String, Set<String>> entry : disallowed.entrySet()) {
                builder.append("User-agent: ").append(entry.getKey()).append(System.lineSeparator());

                entry.getValue().forEach(path -> {
                    builder.append("Disallow: ").append(path).append(System.lineSeparator());
                });

                builder.append(System.lineSeparator());
            }

            try {
                URI uri = new URI(request.getRequestURL().toString());

                builder.append("Sitemap: ")
                        .append(uri.getScheme())
                        .append("://")
                        .append(uri.getHost())
                        .append(":")
                        .append(uri.getPort())
                        .append("/sitemap.xml");

                builder.append(System.lineSeparator());
            } catch (URISyntaxException e) {
                LOGGER.error("Failed to create sitemap.xml declaration for /robots.txt", e);
            }

            return builder.toString();
        });
    }

    public static String sitemap(HttpServletRequest request, RobotoMapper mapper) {
        return SITEMAP.computeIfAbsent(mapper, sitemap -> {
            Set<String> allowed = mapper.getAllowed();
            return "";
        });
    }
}
