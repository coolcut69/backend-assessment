package nl.wolfpackit.backend_assessment.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.wolfpackit.backend_assessment.model.PackEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class PackResource {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @ApiModelProperty(value = "Name of the pack", required = true)
    private String name;

    @Getter
    @Setter
    @ApiModelProperty(value = "Wolves of the pack", required = true)
    private List<WolfResource> wolves = new ArrayList<>();

    protected PackResource() {
        // default constructor to satisfy Jackson
    }

    public PackResource(PackEntity packEntity) {
        this.id = packEntity.getId();
        this.name = packEntity.getName();
        this.wolves.addAll(packEntity.getWolves().stream().map(WolfResource::new).collect(Collectors.toList()));
    }
}
