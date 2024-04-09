package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.user.*;
import lucasdev.com.veggievibes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created succesfully"),
            @ApiResponse(responseCode = "400", description = "Password and RePassword length must be higher than 7 characters | Password and RePassword must be equals | Invalid role"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
    })
    @PostMapping("/register")
    public ResponseEntity<UserIdDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserIdDTO userIdDTO = this.userService.create(userRequestDTO);

        var uri = UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(userIdDTO.userId()).toUri();

        return ResponseEntity.created(uri).body(userIdDTO);
    }

    @Operation(description = "Find User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found"),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserBydId(@PathVariable String id) {
        var user = this.userService.findUserById(id);

        return ResponseEntity.ok(user);
    }

    @Operation(description = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User updated succesfully"),
            @ApiResponse(responseCode = "400", description = "Password and RePassword length must be higher than 7 characters | Password and RePassword must be equals"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserIdDTO> updateUser(@PathVariable String id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserIdDTO userIdDTO = this.userService.update(id, userUpdateDTO);

        return ResponseEntity.ok(userIdDTO);
    }

    @Operation(description = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted succesfully"),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UserMessageDTO> deleteUser(@PathVariable String id) {
        this.userService.delete(id);

        UserMessageDTO userMessageDTO = new UserMessageDTO("User deleted succesfully");

        return ResponseEntity.ok(userMessageDTO);
    }


    @Operation(description = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged succesfully"),
            @ApiResponse(responseCode = "400", description = "Email and/or Password Incorrects"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO LoginResponseDTO = this.userService.login(loginRequestDTO);

        return ResponseEntity.ok(LoginResponseDTO);
    }
}
