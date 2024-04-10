package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.profile.ProfileIdDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileRequestDTO;
import lucasdev.com.veggievibes.dto.user.UserIdDTO;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
