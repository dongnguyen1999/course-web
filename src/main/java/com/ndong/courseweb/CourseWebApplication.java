package com.ndong.courseweb;

import com.ndong.courseweb.util.ConverterFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class CourseWebApplication {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.addConverter(ConverterFactory.emptyStringToNull());
    mapper.addConverter(ConverterFactory.stringToDate());
    return mapper;
  }

  public static void main(String[] args) {
    SpringApplication.run(CourseWebApplication.class, args);
  }

}
