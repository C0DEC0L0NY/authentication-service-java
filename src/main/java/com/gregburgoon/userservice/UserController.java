package com.gregburgoon.userservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
    @RequestMapping(value = "/user/id", method = RequestMethod.GET)
    public String getUserId() {
        return "User Id";
    }

    @RequestMapping(value = "/user/photo", method = RequestMethod.GET)
    public String getUserPhoto() {
        return "User Photo";
    }
}
