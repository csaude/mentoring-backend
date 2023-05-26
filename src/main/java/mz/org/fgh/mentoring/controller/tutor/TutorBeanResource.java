package mz.org.fgh.mentoring.controller.tutor;

import lombok.Data;
import mz.org.fgh.mentoring.entity.healthfacility.HealthFacility;
import mz.org.fgh.mentoring.entity.tutor.Tutor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TutorBeanResource {
    private Tutor tutor;
    private List<HealthFacility> locations;

    public TutorBeanResource(){

    }
    public TutorBeanResource(Tutor tutor, List<HealthFacility> locations){
        this.tutor = tutor;
        this.locations = locations;
    }

    public Tutor getTutor(){
        return this.tutor;
    }
    public List<HealthFacility> getLocations(){
        return this.locations;
    }
}
