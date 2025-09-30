package mz.org.fgh.mentoring.dto.mentorship;

import io.micronaut.core.annotation.Creator;
import lombok.Data;
import mz.org.fgh.mentoring.base.BaseEntityDTO;
import mz.org.fgh.mentoring.dto.answer.AnswerDTO;
import mz.org.fgh.mentoring.dto.form.FormDTO;
import mz.org.fgh.mentoring.dto.location.CabinetDTO;
import mz.org.fgh.mentoring.dto.session.SessionDTO;
import mz.org.fgh.mentoring.dto.tutor.TutorDTO;
import mz.org.fgh.mentoring.dto.tutored.TutoredDTO;
import mz.org.fgh.mentoring.entity.mentorship.Mentorship;
import mz.org.fgh.mentoring.util.Utilities;

import java.util.Date;
import java.util.List;

@Data
public class MentorshipDTO extends BaseEntityDTO {
    private Integer iterationNumber;
    private Date startDate;
    private Date endDate;
    private TutorDTO mentor;
    private TutoredDTO mentee;
    private SessionDTO session;
    private FormDTO form;
    private CabinetDTO cabinet;
    private DoorDTO door;
    private EvaluationTypeDTO evaluationType;
    private List<AnswerDTO> answers;
    private boolean demonstration;
    private String demonstrationDetails;
    private Date performedDate;
    private EvaluationLocationDTO evaluationLocationDTO;

    private String mentorUuid;
    private String menteeUuid;
    private String sessionUuid;
    private String formUuid;
    private String cabinetUuid;
    private String doorUuid;
    private String evaluationTypeUuid;
    private String evaluationLocationUuid;

    @Creator
    public MentorshipDTO() {
    }

    public MentorshipDTO(Mentorship mentorship) {
        super(mentorship);
        this.setStartDate(mentorship.getStartDate());
        this.setEndDate(mentorship.getEndDate());
        this.setIterationNumber(mentorship.getIterationNumber());
        this.setDemonstration(mentorship.isDemonstration());
        this.setDemonstrationDetails(mentorship.getDemonstrationDetails());
        this.setPerformedDate(mentorship.getPerformedDate());
        if(mentorship.getTutor()!=null) {
            this.setMentor(new TutorDTO());
            this.getMentor().setUuid(mentorship.getTutor().getUuid());
        }
        if(mentorship.getTutored()!=null) {
            this.setMentee(new TutoredDTO());
            this.getMentee().setUuid(mentorship.getTutored().getUuid());
        }
        if(mentorship.getSession()!=null) {
            this.setSession(new SessionDTO());
            this.getSession().setUuid(mentorship.getSession().getUuid());
        }
        if(mentorship.getForm()!=null) {
            this.setForm(new FormDTO());
            this.getForm().setUuid(mentorship.getForm().getUuid());
        }
        if(mentorship.getCabinet()!=null) {
            this.setCabinet(new CabinetDTO(mentorship.getCabinet()));
        }
        if(mentorship.getDoor()!=null) {
            this.setDoor(new DoorDTO(mentorship.getDoor()));
        }
        if(mentorship.getEvaluationType()!=null) {
            this.setEvaluationType(new EvaluationTypeDTO(mentorship.getEvaluationType()));
            this.evaluationTypeUuid = mentorship.getEvaluationType().getUuid();
        }
        if(mentorship.getEvaluationLocation()!=null) {
            this.evaluationLocationDTO = new EvaluationLocationDTO(mentorship.getEvaluationLocation());
            this.evaluationLocationUuid = mentorship.getEvaluationLocation().getUuid();
        }
        if(mentorship.getAnswers()!=null) {
            List<AnswerDTO> answerDTOS = Utilities.parse(mentorship.getAnswers(), AnswerDTO.class);
            this.setAnswers(answerDTOS);
        }
    }
}
