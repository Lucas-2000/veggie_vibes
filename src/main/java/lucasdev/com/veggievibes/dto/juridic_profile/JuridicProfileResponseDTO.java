package lucasdev.com.veggievibes.dto.juridic_profile;

import lucasdev.com.veggievibes.dto.user.UserResponseDTO;

public record JuridicProfileResponseDTO(
        String id,
        UserResponseDTO user,
        String legalName,
        String cnpj,
        String stateRegistration,
        String tradeName,
        String activitySector,
        String commercialAddress,
        String commercialPhone,
        String website,
        String commercialEmail,
        String companyDescription,
        String companyLogo,
        String legalRepresentativeContactName,
        String legalRepresentativeContactEmail,
        String legalRepresentativeContactPhone
) {
}
