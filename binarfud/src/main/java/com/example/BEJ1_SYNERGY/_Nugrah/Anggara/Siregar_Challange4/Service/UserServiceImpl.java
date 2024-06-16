package com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Service;

import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Model.Dto.UserRequestDto;
import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Model.Dto.UserResponseDto;
import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Model.account.User;
import com.example.BEJ1_SYNERGY._Nugrah.Anggara.Siregar_Challange4.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User getUserById(UUID userID) {
        Optional<User> dataUser = userRepository.findById(userID);
        return dataUser.orElse(null);
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User dataUser = new User();
        dataUser.setUsername(userRequestDto.getUsername());
        dataUser.setEmail_address(userRequestDto.getEmail());
        dataUser.setPassword(userRequestDto.getPassword());
        User user = userRepository.save(dataUser);

        kafkaTemplate.send("addUser","Berhasil Menambahkan User " + userRequestDto.getUsername());
        return this.mapper.map(user, UserResponseDto.class);
    }

    @Override
    public Map<String,Object> updateUser(UserRequestDto userRequestDto, UUID id) {
        Map<String,Object> body = new HashMap<>();
        User user = new User();

        Optional<User> data = userRepository.findById(id);

        if (data.isEmpty()){
            body.put("statuscode", HttpStatus.NOT_FOUND.value());
            body.put("message", HttpStatus.NOT_FOUND.getReasonPhrase());
            body.put("data",null);
            return body;
        }

        user.setId(id)
                .setUsername(userRequestDto.getUsername())
                .setPassword(userRequestDto.getPassword())
                .setEmail_address(userRequestDto.getEmail());

        User updateData = userRepository.save(user);

        body.put("statuscode", HttpStatus.OK.value());
        body.put("message", HttpStatus.OK.getReasonPhrase());
        body.put("data",this.mapper.map(updateData, UserResponseDto.class));
        return body;
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }

    @Override
    public Map<String,Object> deleteUser(UUID id) {
        Map<String,Object> body = new HashMap<>();
        User findUser = userRepository.findById(id).orElse(null);

        if (findUser != null){
            kafkaTemplate.send("deleteUser","Berhasil Menghapus User " + findUser.getUsername());
        }

        userRepository.deleteById(id);
        body.put("statuscode",HttpStatus.OK.value());
        body.put("message",HttpStatus.OK.getReasonPhrase());
        return body;
    }
}
