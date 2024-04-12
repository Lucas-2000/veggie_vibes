package lucasdev.com.veggievibes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordRequest;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordTokenDTO;
import lucasdev.com.veggievibes.services.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Operation(description = "Generate Reset Password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reset Password Generated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping
    public ResponseEntity<ResetPasswordTokenDTO> generate(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        ResetPasswordTokenDTO resetPasswordTokenDTO = this.resetPasswordService.generate(resetPasswordRequest);

        var uri = UriComponentsBuilder.fromPath("/reset-password/{token}").buildAndExpand(resetPasswordTokenDTO.token()).toUri();

        return ResponseEntity.created(uri).body(resetPasswordTokenDTO);
    }

}
