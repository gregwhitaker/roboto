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

import com.github.gregwhitaker.roboto.spring.annotation.DisallowRobots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Maps resource endpoint methods to allowed or disallowed from robots collections.
 */
public class RobotoMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RobotoMapper.class);

    private final Set<String> allowed = new HashSet<>();
    private final Map<String, Set<String>> disallowed = new HashMap<>();

    public RobotoMapper(BeanFactory beanFactory) {
        doMapping(beanFactory);
    }

    /**
     * Gets the list of all allowed paths.
     *
     * @return
     */
    public Set<String> getAllowed() {
        return allowed;
    }

    /**
     * Gets the list of all disallowed paths by user agent.
     *
     * @return
     */
    public Map<String, Set<String>> getDisallowed() {
        return disallowed;
    }

    /**
     * Scans the classpath for resource methods and maps allowed and disallowed endpoints.
     *
     * @param beanFactory
     */
    private void doMapping(BeanFactory beanFactory) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

        for (String basePackage : AutoConfigurationPackages.get(beanFactory)) {
            for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
                try {
                    Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());

                    for (Method method : clazz.getDeclaredMethods()) {
                        if (isMappingMethod(clazz, method)) {
                            if (isMappingMethodDisallowed(clazz, method)) {
                                disallowMethod(clazz, method);
                            } else {
                                allowMethod(clazz, method);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error("Error during bean scanning", e);
                }
            }
        }
    }

    /**
     * Checks to see if the supplied method is a method that should be mapped.
     *
     * @param clazz
     * @param method
     * @return
     */
    private boolean isMappingMethod(Class clazz, Method method) {
        if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RestController.class)) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                return true;
            }

            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);

                if (Arrays.asList(annotation.method()).contains(RequestMethod.GET)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks to see if the supplied method should be disallowed.
     *
     * @param clazz
     * @param method
     * @return
     */
    private boolean isMappingMethodDisallowed(Class clazz, Method method) {
        if (clazz.isAnnotationPresent(DisallowRobots.class) || method.isAnnotationPresent(DisallowRobots.class)) {
            return true;
        }

        return false;
    }

    /**
     * Adds the supplied method to the allowed list.
     *
     * @param clazz
     * @param method
     */
    private void allowMethod(Class clazz, Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            List<String> paths = Arrays.asList(method.getAnnotation(GetMapping.class).value());
            allowed.addAll(paths);
        }

        if (method.isAnnotationPresent(RequestMapping.class)) {
            List<String> paths = Arrays.asList(method.getAnnotation(RequestMapping.class).value());
            allowed.addAll(paths);
        }
    }

    /**
     * Adds the supplied method to the disallow list.
     *
     * @param clazz
     * @param method
     */
    private void disallowMethod(Class clazz, Method method) {
        if (clazz.isAnnotationPresent(DisallowRobots.class)) {
            Set<String> paths = new HashSet<>();

            if (method.isAnnotationPresent(GetMapping.class)) {
                paths.addAll(Arrays.asList(method.getAnnotation(GetMapping.class).value()));
            }

            if (method.isAnnotationPresent(RequestMapping.class)) {
                paths.addAll(Arrays.asList(method.getAnnotation(RequestMapping.class).value()));
            }

            DisallowRobots clazzAnnotation = (DisallowRobots) clazz.getAnnotation(DisallowRobots.class);
            for (String userAgent : clazzAnnotation.userAgents()) {
                if (disallowed.containsKey(userAgent)) {
                    disallowed.get(userAgent).addAll(paths);
                } else {
                    disallowed.put(userAgent, paths);
                }
            }
        }

        // Method level configurations override class level configurations
        if (method.isAnnotationPresent(DisallowRobots.class)) {
            Set<String> paths = new HashSet<>();

            if (method.isAnnotationPresent(GetMapping.class)) {
                paths.addAll(Arrays.asList(method.getAnnotation(GetMapping.class).value()));
            }

            if (method.isAnnotationPresent(RequestMapping.class)) {
                paths.addAll(Arrays.asList(method.getAnnotation(RequestMapping.class).value()));
            }

            DisallowRobots methodAnnotation = method.getAnnotation(DisallowRobots.class);
            for (String userAgent : methodAnnotation.userAgents()) {
                if (disallowed.containsKey(userAgent)) {
                    disallowed.get(userAgent).addAll(paths);
                } else {
                    disallowed.put(userAgent, paths);
                }
            }
        }
    }
}
