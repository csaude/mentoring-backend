package mz.org.fgh.mentoring.dto.partner;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.core.annotation.Creator;
import lombok.AllArgsConstructor;
import lombok.Data;
import mz.org.fgh.mentoring.base.BaseEntityDTO;
import mz.org.fgh.mentoring.entity.partner.Partner;
import mz.org.fgh.mentoring.util.LifeCycleStatus;
import mz.org.fgh.mentoring.util.Utilities;

@Data
@AllArgsConstructor
public class PartnerDTO extends BaseEntityDTO {
    private String name;

    private String description;

    @Creator
    public PartnerDTO() {
        super();
    }

    public PartnerDTO(Partner partner){
        super(partner);
        this.setName(partner.getName());
        this.setDescription(partner.getDescription());
    }

    @JsonIgnore
    public Partner toEntity() {
        Partner partner = new Partner();
        partner.setId(this.getId());
        partner.setUpdatedAt(this.getUpdatedAt());
        partner.setName(this.getName());
        partner.setUuid(this.getUuid());
        partner.setDescription(this.getDescription());
        partner.setCreatedAt(this.getCreatedAt());
        if (Utilities.stringHasValue(this.getLifeCycleStatus())) partner.setLifeCycleStatus(LifeCycleStatus.valueOf(this.getLifeCycleStatus()));
        return partner;
    }
}
