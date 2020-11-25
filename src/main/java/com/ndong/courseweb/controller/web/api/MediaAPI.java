package com.ndong.courseweb.controller.web.api;

import java.util.List;

import com.ndong.courseweb.dto.LessonDTO;
import com.ndong.courseweb.dto.MediaDTO;
import com.ndong.courseweb.service.ICourseService;
import com.ndong.courseweb.service.IMediaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaAPI {

  @Autowired
  private IMediaService mediaService;

  @Autowired
  private ICourseService courseService;

  @RequestMapping(path = "/web/api/media/{mediaId}", method = RequestMethod.DELETE)
  public ResponseEntity<MediaDTO> deleteMedia(@PathVariable Long mediaId) {
    MediaDTO media = mediaService.deleteMedia(mediaId);
    if (media != null) return new ResponseEntity<>(media, HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(path = "/web/api/media", method = RequestMethod.GET)
  public ResponseEntity<List<MediaDTO>> listMedia(@RequestParam Long courseId, @RequestParam Integer lessonNo) {
    List<MediaDTO> medias = mediaService.listMedia(courseId, lessonNo);
    if (medias != null) return new ResponseEntity<>(medias, HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/web/api/media/upload", method = RequestMethod.POST)
  public ResponseEntity<MediaDTO> uploadMedia(@ModelAttribute MediaDTO mediaDTO) {
    LessonDTO lesson = null;
    if (mediaDTO.getLessonNo() == null) {
      LessonDTO newLesson = LessonDTO.createDefault();
      newLesson.setCourseId(mediaDTO.getCourseId());
      lesson = courseService.tryCreateNewLesson(newLesson);
    }
    MediaDTO media = mediaService.uploadMedia(mediaDTO, lesson);
    if (media != null) return new ResponseEntity<>(media, HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

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
