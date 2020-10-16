package com.ndong.courseweb.controller.web.api;

import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.service.IMediaService;
import com.ndong.courseweb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MediaAPI {

  @Autowired
  private IMediaService mediaService;

//  @RequestMapping(path = "/web/api/media/avatar", method = RequestMethod.POST)
//  public ResponseEntity<MediaDTO> uploadMedia(MediaDTO mediaDTO) {
//    MediaDTO media = mediaService.saveAvatar(mediaDTO.getRawFile(), "${username}");
//    return (media != null) ? new ResponseEntity<>(media, HttpStatus.OK) :
//        new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//  }

  @RequestMapping(path = "/resource/{code}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> getResource(@PathVariable String code) {
    byte[] resource = mediaService.loadResource(code);
    if (resource != null) {
      HttpHeaders headers = new HttpHeaders();
      headers.setCacheControl(CacheControl.noCache().getHeaderValue());
      return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
