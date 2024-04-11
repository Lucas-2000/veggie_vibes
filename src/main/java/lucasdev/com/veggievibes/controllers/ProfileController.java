package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.profile.ProfileIdDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileRequestDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileResponseDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileUpdateDTO;
import lucasdev.com.veggievibes.dto.user.UserIdDTO;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.dto.user.UserResponseDTO;
import lucasdev.com.veggievibes.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Operation(description = "Create new profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created succesfully"),
            @ApiResponse(responseCode = "400", description = "User already have profile | Invalid CPF format, the correct is XXX.XXX.XXX-XX"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping
    public ResponseEntity<ProfileIdDTO> createProfile(@RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileIdDTO profileIdDTO = this.profileService.create(profileRequestDTO);

        var uri = UriComponentsBuilder.fromPath("/profiles/{id}").buildAndExpand(profileIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(profileIdDTO);
    }

    @Operation(description = "Find Profile By User Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile Found"),
            @ApiResponse(responseCode = "404", description = "Profile Not Found"),
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<ProfileResponseDTO> findProfileByUserId(@PathVariable String id) {
        var user = this.profileService.findProfileByUserId(id);

        return ResponseEntity.ok(user);
    }

    @Operation(description = "Update profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile Found"),
            @ApiResponse(responseCode = "400", description = "Invalid CPF format, the correct is XXX.XXX.XXX-XX | CPF already exists"),
            @ApiResponse(responseCode = "404", description = "Profile Not Found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ProfileIdDTO> updateProfile(@PathVariable String id, @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        ProfileIdDTO profileIdDTO = this.profileService.update(id, profileUpdateDTO);

        return ResponseEntity.ok(profileIdDTO);
    }
}
