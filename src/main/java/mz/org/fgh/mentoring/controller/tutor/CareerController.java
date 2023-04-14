package mz.org.fgh.mentoring.controller.tutor;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import mz.org.fgh.mentoring.api.RESTAPIMapping;
import mz.org.fgh.mentoring.base.BaseController;
import mz.org.fgh.mentoring.entity.career.Career;
import mz.org.fgh.mentoring.entity.form.Form;
import mz.org.fgh.mentoring.service.career.CareerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static mz.org.fgh.mentoring.api.RESTAPIMapping.API_VERSION;

@Controller(RESTAPIMapping.CAREER_CONTROLLER)
public class CareerController extends BaseController {

    @Inject
    CareerService careerService;
    public CareerController(){
    }

    public static final Logger LOG = LoggerFactory
            .getLogger(CareerController.class);

    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Career")
    @Version(API_VERSION)
    @Get
    public List<Career> getAll() {
        LOG.debug("Searching careers version 2");
        return careerService.findAll();
    }

    @Get
    public List<Career> getAllV1() {
        LOG.debug("Searching careers version 1");
        return careerService.findAll();
    }

    @Post(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    public Career create (@Body Career career) {
        LOG.debug("Created career {} ", career);
        return this.careerService.create(career);
    }

}
