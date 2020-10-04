package com.ndong.courseweb;

import com.ndong.courseweb.converter.ConverterFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseWebApplication {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    // Add general converters
    mapper.addConverter(ConverterFactory.emptyStringToNull());
    mapper.addConverter(ConverterFactory.stringToDate());

    return mapper;
  }

  public static void main(String[] args) {
    SpringApplication.run(CourseWebApplication.class, args);
  }

}
