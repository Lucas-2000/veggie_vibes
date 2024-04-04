package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.user.UserIdDTO;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping
    public ResponseEntity<UserIdDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserIdDTO userIdDTO = this.userService.create(userRequestDTO);

        var uri = UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(userIdDTO.userId()).toUri();

        return ResponseEntity.created(uri).body(userIdDTO);
    }
}
