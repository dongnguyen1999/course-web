package com.ndong.courseweb.controller.admin.api;

import java.util.List;

import com.ndong.courseweb.dto.MediaTypeDTO;
import com.ndong.courseweb.service.IMediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaTypeAPI {

  @Autowired
  private IMediaTypeService mediaTypeService;

  @RequestMapping(path = "/admin/api/media-type", method = RequestMethod.PUT)
  public ResponseEntity<MediaTypeDTO> updateMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
    MediaTypeDTO mediaType = mediaTypeService.updateMediaType(mediaTypeDTO);
    return (mediaType != null) ? new ResponseEntity<>(mediaType, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/admin/api/media-type", method = RequestMethod.DELETE)
  public ResponseEntity<List<MediaTypeDTO>> deleteCategory(@RequestBody Long[] ids) {
    List<MediaTypeDTO> mediaTypes = mediaTypeService.deleteMediaTypes(ids);
    return (mediaTypes != null) ? new ResponseEntity<>(mediaTypes, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(path = "/admin/api/media-type", method = RequestMethod.POST)
  public ResponseEntity<MediaTypeDTO> addNewCategory(@RequestBody MediaTypeDTO mediaTypeDTO) {
    return updateMediaType(mediaTypeDTO);
  }

  @RequestMapping(path = "/admin/api/media-type", method = RequestMethod.GET)
  public ResponseEntity<MediaTypeDTO> getMediaTypeInfo(@RequestParam String code) {
    MediaTypeDTO mediaType = mediaTypeService.findByCode(code);
    return (mediaType != null) ? new ResponseEntity<>(mediaType, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
