package mz.org.fgh.mentoring.entity.session;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mz.org.fgh.mentoring.base.BaseEntity;
import mz.org.fgh.mentoring.dto.session.SessionDTO;
import mz.org.fgh.mentoring.entity.form.Form;
import mz.org.fgh.mentoring.entity.mentorship.Mentorship;
import mz.org.fgh.mentoring.entity.ronda.Ronda;
import mz.org.fgh.mentoring.entity.tutored.Tutored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sessions")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
public class Session extends BaseEntity {
    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "PERFORMED_DATE")
    private Date performedDate;

    @Column(name = "NEXT_SESSION_DATE")
    private Date nextSessionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SESSION_STATUS_ID", nullable = false)
    private SessionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RONDA_ID", nullable = false)
    private Ronda ronda;

    @Column(name = "REASON")
    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MENTEE_ID", nullable = false)
    private Tutored mentee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FORM_ID", nullable = false)
    private Form form;

    @Column(name = "STRONG_POINTS")
    private String strongPoints;

    @Column(name = "POINTS_TO_IMPROVE")
    private String pointsToImprove;

    @Column(name = "WORK_PLAN")
    private String workPlan;

    @Column(name = "OBSERVATIONS")
    private String observations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "session")
    private List<Mentorship> mentorships;

    @OneToMany(mappedBy = "session")
    private List<SessionRecommendedResource> recommendedResources;

    @Column(name = "SESSION_SUMMARY", columnDefinition = "TEXT")
    private String sessionSummary;

    @Column(name = "PERFORMANCE_RISK", columnDefinition = "TEXT")
    private String performanceRisk;



    public Session() {
    }

    public Session(String uuid) {
        super(uuid);
    }

    public Session(SessionDTO sessionDTO) {
        super(sessionDTO);
        this.setStartDate(sessionDTO.getStartDate());
        this.setEndDate(sessionDTO.getEndDate());
        this.setPerformedDate(sessionDTO.getPerformedDate());
        this.setPointsToImprove(sessionDTO.getPointsToImprove());
        this.setStrongPoints(sessionDTO.getStrongPoints());
        this.setObservations(sessionDTO.getObservations());
        this.setNextSessionDate(sessionDTO.getNextSessionDate());
        this.setWorkPlan(sessionDTO.getWorkPlan());
        if(sessionDTO.getSessionStatus()!=null) {
            this.setStatus(new SessionStatus(sessionDTO.getSessionStatus()));
        }
        if(sessionDTO.getMentee()!=null) {
            this.setMentee(new Tutored(sessionDTO.getMentee()));
        }
        if(sessionDTO.getRonda()!=null) {
            this.setRonda(new Ronda(sessionDTO.getRonda()));
        }
        if(sessionDTO.getForm()!=null) {
            this.setForm(new Form(sessionDTO.getForm()));
        }
    }

    public void addMentorship(Mentorship mentorship) {
        if(mentorships==null) {
            mentorships = new ArrayList<>();
        }
        mentorships.add(mentorship);
    }
    @Override
    public String toString() {
        return "Session{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", performedDate=" + performedDate +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", strongPoints='" + strongPoints + '\'' +
                ", pointsToImprove='" + pointsToImprove + '\'' +
                ", workPlan='" + workPlan + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }

}
