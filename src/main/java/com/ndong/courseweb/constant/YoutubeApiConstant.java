package com.ndong.courseweb.constant;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ndong.courseweb.service.impl.StorageService;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class YoutubeApiConstant {
    public static final String CLIENT_SECRETS= StorageService.STORAGE_LOCATION + File.separator + "client_secret.json";
    public static final Collection<String> SCOPES =
            List.of("https://www.googleapis.com/auth/youtube.upload", "https://www.googleapis.com/auth/youtube.readonly");
    public static final String APPLICATION_NAME = "Course Web";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
}
