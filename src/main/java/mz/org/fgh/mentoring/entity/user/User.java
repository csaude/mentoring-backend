package mz.org.fgh.mentoring.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import io.micronaut.core.annotation.Creator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mz.org.fgh.mentoring.base.BaseEntity;
import mz.org.fgh.mentoring.dto.user.UserDTO;
import mz.org.fgh.mentoring.entity.employee.Employee;
import mz.org.fgh.mentoring.entity.healthfacility.HealthFacility;
import mz.org.fgh.mentoring.entity.location.District;
import mz.org.fgh.mentoring.entity.location.Location;
import mz.org.fgh.mentoring.entity.location.Province;
import mz.org.fgh.mentoring.entity.role.Role;
import mz.org.fgh.mentoring.entity.role.UserRole;
import mz.org.fgh.mentoring.util.LifeCycleStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jose Julai Ritsure
 */
@Schema(name = "User", description = "This entity stores data for the user authentication and login")
@Entity(name = "User")
@Table(name = "users")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class User extends BaseEntity {

    @NotEmpty
    @Column(name = "USERNAME", nullable = false, length = 250)
    private String username;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false, length = 500)
    private String password;

    @NotEmpty
    @Column(name = "SHOULD_RESET_PASSWORD", nullable = false, length = 500)
    private boolean shouldResetPassword;

    @NotEmpty
    @Column(name = "SALT", nullable = false, length = 500)
    private String salt;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    @Creator
    public User() {}
    
    public User(UserDTO userDTO) {
        super(userDTO);
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.shouldResetPassword = userDTO.isShouldResetPassword();
        this.salt = userDTO.getSalt();
        this.employee = new Employee(userDTO.getEmployeeDTO());
        if (userDTO.getUserRoleDTOS() != null && !userDTO.getUserRoleDTOS().isEmpty()) {
            this.userRoles = userDTO.getUserRoleDTOS().stream()
                    .map(dto -> new UserRole(this, new Role(dto.getRoleDTO())))
                    .collect(Collectors.toList());
        }
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public boolean hasNationalAccess() {
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().getHierarchyLevel() == 1) return true;
        }
        return false;
    }

    public boolean hasProvincialAccess() {
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().getHierarchyLevel() == 2) return true;
        }
        return false;
    }

    public boolean hasDistrictAccess() {
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().getHierarchyLevel() == 3) return true;
        }
        return false;
    }

    public boolean hasHFAccess() {
        for (UserRole userRole : this.userRoles) {
            if (userRole.getRole().getHierarchyLevel() == 4) return true;
        }
        return false;
    }
    @Transient
    public List<Province> getGrantedProvinces() {
        List<Province> locations = new ArrayList<>();
        for (Location location : this.employee.getLocations()) {
            if (location.getProvince() != null) locations.add(location.getProvince());
        }
        return locations;
    }
    @Transient
    public List<District> getGrantedDistricts() {
        List<District> locations = new ArrayList<>();
        for (Location location : this.employee.getLocations()) {
            if (location.getDistrict()!= null) locations.add(location.getDistrict());
        }
        return locations;
    }
    @Transient
    public List<HealthFacility> getGrantedHFs() {
        List<HealthFacility> locations = new ArrayList<>();
        for (Location location : this.employee.getLocations()) {
            if (location.getHealthFacility() != null) locations.add(location.getHealthFacility());
        }
        return locations;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", shouldResetPassword='" + shouldResetPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", employee=" + employee +
                ", userRoles=" + userRoles +
                '}';
    }

    public boolean isActive() {
        return this.getLifeCycleStatus().equals(LifeCycleStatus.ACTIVE);
    }
}
