package mz.org.fgh.mentoring.dto.ronda;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import mz.org.fgh.mentoring.base.BaseEntityDTO;
import mz.org.fgh.mentoring.dto.tutor.TutorDTO;
import mz.org.fgh.mentoring.entity.ronda.RondaMentor;
import mz.org.fgh.mentoring.util.LifeCycleStatus;
import mz.org.fgh.mentoring.util.Utilities;

@Data
@NoArgsConstructor
public class RondaMentorDTO extends BaseEntityDTO {
    private Date startDate;

    private Date endDate;

    private TutorDTO mentor;

    private RondaDTO ronda;

    public RondaMentorDTO(RondaMentor rondaMentor) {
        super(rondaMentor);
        this.setMentor(new TutorDTO(rondaMentor.getMentor()));
        this.setStartDate(rondaMentor.getStartDate());
        this.setEndDate(rondaMentor.getEndDate());
        //if ((rondaMentor.getRonda()!=null)) this.setRonda(new RondaDTO(rondaMentor.getRonda()));
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TutorDTO getMentor() {
        return mentor;
    }

    public void setMentor(TutorDTO mentor) {
        this.mentor = mentor;
    }

    public RondaDTO getRonda() {
        return ronda;
    }

    public void setRonda(RondaDTO ronda) {
        this.ronda = ronda;
    }
    public RondaMentor getRondaMentor() {
        RondaMentor rondaMentor = new RondaMentor();
        rondaMentor.setId(this.getId());
        rondaMentor.setUuid(this.getUuid());
        rondaMentor.setStartDate(this.getStartDate());
        rondaMentor.setEndDate(this.getEndDate());
        rondaMentor.setCreatedAt(this.getCreatedAt());
        rondaMentor.setUpdatedAt(this.getUpdatedAt());
        if (Utilities.stringHasValue(this.getLifeCycleStatus())) rondaMentor.setLifeCycleStatus(LifeCycleStatus.valueOf(this.getLifeCycleStatus()));
        if(this.getMentor()!=null) {
            rondaMentor.setMentor(this.getMentor().toEntity());
        }
        if(this.getRonda()!=null) {
            rondaMentor.setRonda(this.getRonda().getRonda());
        }
        return rondaMentor;
    }
}
