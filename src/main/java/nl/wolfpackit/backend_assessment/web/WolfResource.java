package nl.wolfpackit.backend_assessment.web;

import nl.wolfpackit.backend_assessment.model.WolfEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class WolfResource {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @ApiModelProperty(value = "Name of the wolf", required = true)
    private String name;

    @Getter
    @Setter
    @ApiModelProperty(value = "Gender of the wolf", allowableValues = "M,F,X", required = true)
    private String gender;

    @Getter
    @Setter
    @ApiModelProperty(value = "Birth date", required = true, example = "1978-08-25")
    private String birthDate;

    protected WolfResource() {
        // default constructor to satisfy Jackson
    }

    public WolfResource(WolfEntity wolfEntity) {
        id = wolfEntity.getId();
        name = wolfEntity.getName();
        gender = wolfEntity.getGender().getLabel();
        birthDate = DateUtilities.formatISO8601Date(wolfEntity.getBirthDate());
    }

}
