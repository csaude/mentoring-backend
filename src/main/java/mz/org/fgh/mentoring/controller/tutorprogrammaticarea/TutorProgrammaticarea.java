package mz.org.fgh.mentoring.controller.tutorprogrammaticarea;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import mz.org.fgh.mentoring.api.RESTAPIMapping;
import mz.org.fgh.mentoring.base.BaseController;
import mz.org.fgh.mentoring.controller.tutor.TutorController;
import mz.org.fgh.mentoring.dto.programmaticarea.ProgrammaticAreaDTO;
import mz.org.fgh.mentoring.dto.programmaticarea.TutorProgrammaticAreaDTO;
import mz.org.fgh.mentoring.entity.tutorprogramaticarea.TutorProgrammaticArea;
import mz.org.fgh.mentoring.service.tutorprogrammaticarea.TutorProgrammaticAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(RESTAPIMapping.TUTOR_PROGRAMMATIC_AREAS)
public class TutorProgrammaticarea extends BaseController {

    public static final Logger LOG = LoggerFactory.getLogger(TutorController.class);

    @Operation(summary = "Return a list off all TutorProgrammaticArea")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Get("")
    public List<TutorProgrammaticAreaDTO> getAll() {
        LOG.debug("Searching TutorProgrammaticAreas version 1");
        return this.tutorProgrammaticAreaService.findTutorProgrammaticAreaAll();
    }

    @Inject
    private TutorProgrammaticAreaService tutorProgrammaticAreaService;
    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public TutorProgrammaticArea create(@Body TutorProgrammaticArea tutorProgrammaticArea){
         return this.tutorProgrammaticAreaService.create(tutorProgrammaticArea);
    }

    @Put(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public TutorProgrammaticArea update(@Body TutorProgrammaticArea tutorProgrammaticArea){
        return this.tutorProgrammaticAreaService.update(tutorProgrammaticArea);
    }
}
