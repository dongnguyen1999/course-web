package com.ndong.courseweb.controller.web.api;

import com.ndong.courseweb.dto.UserDTO;
import com.ndong.courseweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI {

  @Autowired
  private IUserService userService;

  @RequestMapping(path = "/web/api/user/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> findOneUser(@PathVariable Long id) {
    UserDTO user = userService.findOneUser(id);
    return (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(path = "/web/api/user", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> getUserInfo(@RequestParam String username) {
    UserDTO user = userService.findOneUser(username);
    return (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
