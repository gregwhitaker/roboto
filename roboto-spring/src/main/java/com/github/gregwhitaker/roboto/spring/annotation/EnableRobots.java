package com.github.gregwhitaker.roboto.spring.annotation;

import com.github.gregwhitaker.roboto.spring.RobotoConfiguration;
import org.springframework.context.annotation.Import;

@RobotoAnnotation
@Import(RobotoConfiguration.class)
public @interface EnableRobots {

}
