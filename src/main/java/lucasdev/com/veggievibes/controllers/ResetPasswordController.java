package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordRequestDTO;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordTokenDTO;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordValidateDTO;
import lucasdev.com.veggievibes.services.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Operation(description = "Generate Reset Password Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reset Password Generated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping
    public ResponseEntity<ResetPasswordTokenDTO> generate(@RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
        ResetPasswordTokenDTO resetPasswordTokenDTO = this.resetPasswordService.generate(resetPasswordRequestDTO);

        var uri = UriComponentsBuilder.fromPath("/reset-password/{token}").buildAndExpand(resetPasswordTokenDTO.token()).toUri();

        return ResponseEntity.created(uri).body(resetPasswordTokenDTO);
    }

    @Operation(description = "Validate Reset Password Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "400", description = "Token is expired"),
            @ApiResponse(responseCode = "404", description = "Token not found"),
    })
    @GetMapping("/validate/{token}")
    public ResponseEntity<ResetPasswordValidateDTO> validate(@PathVariable String token) {
        ResetPasswordValidateDTO resetPasswordValidateDTO = this.resetPasswordService.validate(token);

        return ResponseEntity.ok(resetPasswordValidateDTO);
    }

}
