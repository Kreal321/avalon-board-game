package me.kreal.avalon.controller;

import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;

@RestController
@CrossOrigin
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
            return ResponseEntity.badRequest()
                    .body(DataResponse.error(result.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .reduce("", String::concat)));
        }

        DataResponse response = this.userService.createNewPlayer(request);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    public ResponseEntity<DataResponse> handleUserLoginRequest(@Valid @RequestBody UserRequest request, BindingResult result) {

        // Validation failed
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.error(result.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .reduce("", String::concat)));
        }

        DataResponse response = this.userService.findUserByLoginRequest(request);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);

    }

    @GetMapping("/me")
    public ResponseEntity<DataResponse> handleUserMeRequest(@AuthenticationPrincipal AuthUserDetail userDetail) {

        DataResponse response = this.userService.getUserMeByAuthUserDetail(userDetail);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/update")
    public ResponseEntity<DataResponse> handleUserUpdateRequest(@AuthenticationPrincipal AuthUserDetail userDetail, @Valid @RequestBody UserRequest request, BindingResult result) {

        // Validation failed
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(DataResponse.error(result.getFieldErrors().stream()
                            .map(FieldError::getDefaultMessage)
                            .reduce("", String::concat)));
        }

        DataResponse response = this.userService.updateUserByAuthUserDetail(userDetail, request);

        if (!response.getSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);

    }

}
