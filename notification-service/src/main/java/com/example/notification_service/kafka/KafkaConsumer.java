package com.example.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "addUser",groupId = "binarfud")
    public void notificationAddUser(String message){
        System.out.println(message);
    }


    @KafkaListener(topics = "deleteUser",groupId = "binarFud")
    public void notificationDeleteUser(String message){
        System.out.println(message);
    }
}
