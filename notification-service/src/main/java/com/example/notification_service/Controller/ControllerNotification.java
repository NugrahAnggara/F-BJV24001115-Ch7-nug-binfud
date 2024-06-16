package com.example.notification_service.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/notif")
public class ControllerNotification {

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello Academy Binar";
    }
}
