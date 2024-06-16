package com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Controller;


import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Model.Dto.UserRequestDto;
import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Model.Dto.UserResponseDto;
import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping(path = "/registrasi")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto user){
        return new ResponseEntity<>(userService.addUser(user),HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") String id,
                                                      @ModelAttribute UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.updateUser(userRequestDto,UUID.fromString(id)),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        Map<String,Object> body = new HashMap<>();
        if (userService.getUserById(UUID.fromString(id)) == null){
            body.put("statuscode",HttpStatus.NOT_FOUND.value());
            body.put("message",HttpStatus.NOT_FOUND.getReasonPhrase());
            return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                userService.deleteUser(UUID.fromString(id)),
                HttpStatus.OK
        );
    }
}
