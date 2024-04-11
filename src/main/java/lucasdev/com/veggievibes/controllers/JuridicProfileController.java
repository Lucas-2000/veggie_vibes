package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileIdDTO;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileRequestDTO;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileResponseDTO;
import lucasdev.com.veggievibes.services.JuridicProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/juridic-profiles")
public class JuridicProfileController {

    @Autowired
    private JuridicProfileService juridicProfileService;

    @Operation(description = "Create new juridic profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juridic Profile created succesfully"),
            @ApiResponse(responseCode = "400", description = "User already have profile | Invalid CNPJ format, the correct is XX.XXX.XXX/XXXX-XX | Invalid user role, needs to be SELLER"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping
    public ResponseEntity<JuridicProfileIdDTO> createJuridicProfile(@RequestBody JuridicProfileRequestDTO juridicProfileRequestDTO) {
        JuridicProfileIdDTO juridicProfileIdDTO = this.juridicProfileService.create(juridicProfileRequestDTO);

        var uri = UriComponentsBuilder.fromPath("/juridic-profiles/{id}").buildAndExpand(juridicProfileIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(juridicProfileIdDTO);
    }

    @Operation(description = "Find Juridic Profile By User Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juridic Profile Found"),
            @ApiResponse(responseCode = "404", description = "Juridic Profile Not Found"),
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<JuridicProfileResponseDTO> findJuridicProfileByUserId(@PathVariable String id) {
        var user = this.juridicProfileService.findJuridicProfileByUserId(id);

        return ResponseEntity.ok(user);
    }
}
