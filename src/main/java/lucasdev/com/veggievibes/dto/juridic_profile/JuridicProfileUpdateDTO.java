package lucasdev.com.veggievibes.dto.juridic_profile;

public record JuridicProfileUpdateDTO(
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
