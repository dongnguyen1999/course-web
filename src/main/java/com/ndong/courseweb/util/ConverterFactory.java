package com.ndong.courseweb.util;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class ConverterFactory {

  public static Converter<String, Date> stringToDate(String formatPattern) {
    return new AbstractConverter<String, Date>() {
      @Override
      protected Date convert(String source) {
        try {
          SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
          java.util.Date date = formatter.parse(source);
          return new Date(date.getTime());
        } catch (ParseException e) {
          return null;
        }
      }
    };
  }

  public static Converter<String, Date> stringToDate() {
    return stringToDate("dd/MM/yyyy");
  }

  public static Converter<String, String> stringToString(String prefix, String suffix) {
    return new AbstractConverter<String, String>() {
      @Override
      protected String convert(String source) {
        if (source == null) return null;
        String text = source.trim();
        return (text.isEmpty())? String.join(" ", prefix, suffix)
              : String.join(" ", prefix, text, suffix);
      }
    };
  }

  public static Converter<String, String> emptyStringToNull() {
    return new AbstractConverter<String, String>() {
      @Override
      protected String convert(String source) {
        if (source == null) return null;
        String text = source.trim();
        return (text.isEmpty())? null: text;
      }
    };
  }

}
