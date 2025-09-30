package mz.org.fgh.mentoring.entity.ronda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mz.org.fgh.mentoring.base.BaseEntity;
import mz.org.fgh.mentoring.dto.ronda.RondaMenteeDTO;
import mz.org.fgh.mentoring.entity.tutored.Tutored;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "RondaMentee")
@Table(name = "ronda_mentee")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
public class RondaMentee extends BaseEntity {

    @EqualsAndHashCode.Include
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RONDA_ID")
    private Ronda ronda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MENTEE_ID")
    private Tutored tutored;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    public RondaMentee() {
    }

    public RondaMentee(RondaMenteeDTO rondaMenteeDTO) {
        super(rondaMenteeDTO);
        this.setStartDate(rondaMenteeDTO.getStartDate());
        this.setEndDate(rondaMenteeDTO.getEndDate());
        if(rondaMenteeDTO.getRonda()!=null) {
            this.setRonda(new Ronda(rondaMenteeDTO.getRonda()));
        }
        if(rondaMenteeDTO.getMentee()!=null) {
            this.setTutored(new Tutored(rondaMenteeDTO.getMentee()));
        }
    }

    @Override
    public String toString() {
        return "RondaMentee{" +
                "uuid=" + uuid +
                ", menteeUuid=" + (tutored != null ? tutored.getUuid() : "null") +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RondaMentee)) return false;
        RondaMentee that = (RondaMentee) o;
        return (tutored != null && tutored.getUuid().equals(that.tutored.getUuid()));
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(uuid, tutored != null ? tutored.getUuid() : null);
    }

}
