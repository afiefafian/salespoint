/*
 * Copyright 2013 the original author or authors.
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
package videoshop;

import java.util.List;

import org.salespointframework.Salespoint;
import org.salespointframework.web.spring.converter.JpaEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Central application class for VideoShop.
 * 
 * @author Oliver Gierke
 */
@Configuration
@EnableAutoConfiguration
@Import({ Salespoint.class, VideoShop.WebConfiguration.class })
@ComponentScan
public class VideoShop {

	/**
	 * runs the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(VideoShop.class, args);
	}

	/**
	 * Custom web configuration for Spring MVC. 
	 *
	 * @author Oliver Gierke
	 */
	@Configuration
	static class WebConfiguration extends WebMvcConfigurerAdapter {

		// Web application configuration
		@Autowired JpaEntityConverter entityConverter;

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			argumentResolvers.add(new org.salespointframework.web.spring.support.LoggedInUserArgumentResolver());
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new org.salespointframework.web.spring.support.LoggedInUserInterceptor());
			registry.addInterceptor(new org.salespointframework.web.spring.support.CapabilitiesInterceptor());
		}

		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addConverter(entityConverter);
			registry.addConverter(new org.salespointframework.web.spring.converter.StringToCapabilityConverter());
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/res/**").addResourceLocations("/resources/");
		}
	}
}