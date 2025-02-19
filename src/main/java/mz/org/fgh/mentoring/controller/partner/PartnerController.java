package mz.org.fgh.mentoring.controller.partner;

import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mz.org.fgh.mentoring.api.RESTAPIMapping;
import mz.org.fgh.mentoring.base.BaseController;
import mz.org.fgh.mentoring.dto.partner.PartnerDTO;
import mz.org.fgh.mentoring.entity.partner.Partner;
import mz.org.fgh.mentoring.error.MentoringAPIError;
import mz.org.fgh.mentoring.service.partner.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(RESTAPIMapping.PARTNER)
public class PartnerController extends BaseController {

    private final PartnerService partnerService;

    public static final Logger LOG = LoggerFactory.getLogger(PartnerController.class);

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Operation(summary = "Return a list off all Partners")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Partner")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("getall")
    public HttpResponse<?> getAll(
            Pageable pageable
    ) {
        try {
            return HttpResponse.ok(partnerService.findAllPartners(pageable));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return HttpResponse.badRequest().body(
                    MentoringAPIError.builder()
                            .status(HttpStatus.BAD_REQUEST.getCode())
                            .error(e.getLocalizedMessage())
                            .message(e.getMessage())
                            .build()
            );
        }
    }


    @Post(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    public Partner create (@Body Partner partner, Authentication authentication) {

         Partner partnerResult = this.partnerService.createPartner(partner, (Long) authentication.getAttributes().get("userInfo"));

        LOG.debug("Created tutor {}", partnerResult);
        return partnerResult;
    }

    @Patch( consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Partner updatePartner(@Body Partner partner, Authentication authentication){
        return this.partnerService.updatePartner(partner, (Long) authentication.getAttributes().get("userInfo"));
    }

    @Operation(summary = "Get Partner from database")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Partner")
    @Get("/{id}")
    public PartnerDTO findTartnerById(@PathVariable("id") Long id){
        Partner partner = this.partnerService.getById(id);
        return  new PartnerDTO(partner);
    }

    @Operation(summary = "Delete Partner from database")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Partner")
    @Patch("/{id}")
    public PartnerDTO deleteProgram(@PathVariable("id") Long id, Authentication authentication){

        Partner partner = this.partnerService.findPartnerById(id);        
        partner = this.partnerService.delete(partner, (Long) authentication.getAttributes().get("userInfo"));       

        return new PartnerDTO(partner);
    }

    @Operation(summary = "Destroy Partner from database")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Partner")
    @Delete("/{id}")
    public PartnerDTO destroyProgram(@PathVariable("id") Long id, Authentication authentication){

        Partner partner = this.partnerService.findPartnerById(id);        
        this.partnerService.destroy(partner);  
        
        return new PartnerDTO(partner);
    }
}
