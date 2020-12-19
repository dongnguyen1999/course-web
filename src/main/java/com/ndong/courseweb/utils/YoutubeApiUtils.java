package com.ndong.courseweb.utils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.ndong.courseweb.constant.YoutubeApiConstant;
import com.ndong.courseweb.service.impl.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class YoutubeApiUtils {

    @Autowired
    private YouTube youtubeService;

    public String upload(MultipartFile file, String title)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        // Define the Video object, which will be uploaded as the request body.
        Video video = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setCategoryId("27");
        snippet.setDescription("Private lesson");
        snippet.setTags(List.of("course", "education", "online course", "lesson", "kourse"));
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus("private");
        video.setSnippet(snippet);
        video.setStatus(status);

//        File mediaFile = new File("/home/ndong/Videos/video.mp4");
        InputStreamContent mediaContent =
                new InputStreamContent("application/octet-stream",
                        new BufferedInputStream(file.getInputStream()));
        mediaContent.setLength(file.getSize());

        // Define and execute the API request
        YouTube.Videos.Insert request = youtubeService.videos()
                .insert("snippet,status", video, mediaContent);
        Video response = request.execute();
        return response.getId();
    }

    public String getThumbnailImageLink(String videoId)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        // Define and execute the API request
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet");
        VideoListResponse response = request.setId(videoId).execute();
        return response.getItems().get(0).getSnippet().getThumbnails().getDefault().getUrl();
    }

    public byte[] getThumbnailResource(String resourceLink) {
        BufferedImage bImage = null;
        try {
            URL url = new URL(resourceLink);
            bImage = ImageIO.read(url);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos );
            return bos.toByteArray();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}