package nl.wolfpackit.backend_assessment.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface PacksSwagger {

    @ApiOperation(value = "Adds a wolf to a pack")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully added wolf"),
            @ApiResponse(code = 404, message = "Wolf or pack not found")
    })
    void addWolfToPack(@ApiParam(value = "Id of the pack", required = true) Long packId, @ApiParam(value = "Id of the wolf", required = true) Long wolfId);

    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully removed wolf"),
            @ApiResponse(code = 404, message = "Wolf or pack not found")
    })
    @ApiOperation(value = "Removes a wolf from a pack")
    void deleteWolfToPack(@ApiParam(value = "Id of the pack", required = true) Long packId, @ApiParam(value = "Id of the wolf", required = true) Long wolfId);
}
