package nl.wolfpackit.backend_assessment.web;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;

public interface WolvesSwagger {

    @ApiOperation(value = "Creates a wolf ")
    ResponseEntity<WolfResource> createWolf(WolfResource resource);

    @ApiOperation(value = "Update a wolf ")
    ResponseEntity<WolfResource> updateWolf(WolfResource resource, Long id);

    @ApiOperation(value = "Removes a wolf ")
    void deleteWolf(Long id);

}
