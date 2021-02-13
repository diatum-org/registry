/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.19).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.coredb.registry.api;

import org.coredb.registry.model.SystemInfo;
import org.coredb.registry.model.SystemStat;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@Api(value = "console", description = "the console API")
public interface ConsoleApi {

    @ApiOperation(value = "", nickname = "checkToken", notes = "Check if token is valid", tags={ "console", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation"),
        @ApiResponse(code = 404, message = "token not found"),
        @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/console/access",
        method = RequestMethod.PUT)
    ResponseEntity<Void> checkToken(@NotNull @ApiParam(value = "access token", required = true) @Valid @RequestParam(value = "token", required = true) String token
);


    @ApiOperation(value = "", nickname = "getInfo", notes = "Retrieve info of server", response = SystemInfo.class, tags={ "console", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = SystemInfo.class),
        @ApiResponse(code = 404, message = "token not found"),
        @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/console/info",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<SystemInfo> getInfo(@NotNull @ApiParam(value = "access token", required = true) @Valid @RequestParam(value = "token", required = true) String token
);


    @ApiOperation(value = "", nickname = "getStats", notes = "Retrieve stats of server", response = SystemStat.class, responseContainer = "List", tags={ "console", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = SystemStat.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "token not found"),
        @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/console/stats",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<SystemStat>> getStats(@NotNull @ApiParam(value = "access token", required = true) @Valid @RequestParam(value = "token", required = true) String token
);

}

