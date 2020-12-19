package com.ndong.courseweb;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.ndong.courseweb.constant.YoutubeApiConstant;
import com.ndong.courseweb.utils.ConverterFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.security.GeneralSecurityException;

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

//  @Bean
//  public YouTube getYoutubeService() throws GeneralSecurityException, IOException {
//    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//    Credential credential = authorize(httpTransport);
//    return new YouTube.Builder(httpTransport, YoutubeApiConstant.JSON_FACTORY, credential)
//            .setApplicationName(YoutubeApiConstant.APPLICATION_NAME)
//            .build();
//  }
//
//  private Credential authorize(final NetHttpTransport httpTransport) throws IOException {
//    // Load client secrets.
//    InputStream in = new FileInputStream(YoutubeApiConstant.CLIENT_SECRETS);
//    GoogleClientSecrets clientSecrets =
//            GoogleClientSecrets.load(YoutubeApiConstant.JSON_FACTORY, new InputStreamReader(in));
//    // Build flow and trigger user authorization request.
//    GoogleAuthorizationCodeFlow flow =
//            new GoogleAuthorizationCodeFlow.Builder(httpTransport, YoutubeApiConstant.JSON_FACTORY, clientSecrets, YoutubeApiConstant.SCOPES)
//                    .build();
//    Credential credential =
//            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//    return credential;
//  }

  public static void main(String[] args) {
    SpringApplication.run(CourseWebApplication.class, args);
  }

}
