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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RobotoResponse {

    private static Map<RobotoMapper, String> ROBOTS = new HashMap<>();
    private static Map<RobotoMapper, String> SITEMAP = new HashMap<>();

    public static String robots(RobotoMapper mapper) {
        return ROBOTS.computeIfAbsent(mapper, robots -> {
            Map<String, Set<String>> disallowed = mapper.getDisallowed();
            return "";
        });
    }

    public static String sitemap(RobotoMapper mapper) {
        return SITEMAP.computeIfAbsent(mapper, sitemap -> {
            return "";
        });
    }
}
