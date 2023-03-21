package me.kreal.avalon.controller;

import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<DataResponse> handleUserRegisterRequest(@Valid @RequestBody UserRequest request, BindingResult result) {

        // Validation failed
        if (result.hasErrors()) {
            return ResponseEntity.ok(DataResponse.builder()
                    .success(false)
                    .message(result.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .reduce("", String::concat))
                    .build());
        }


        return ResponseEntity.ok(this.userService.createNewPlayer(request));

    }

    @PostMapping("/login")
    public ResponseEntity<DataResponse> handleUserLoginRequest(@Valid @RequestBody UserRequest request, BindingResult result) {

        // Validation failed
        if (result.hasErrors()) {
            return ResponseEntity.ok(DataResponse.builder()
                    .success(false)
                    .message("User information is incorrect.")
                    .build());
        }

        return ResponseEntity.ok(this.userService.findUserByLoginRequest(request));

    }

}
