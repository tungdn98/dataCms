package vn.com.datamanager.service.dto;

import java.util.Set;
import vn.com.datamanager.domain.Employee;

/**
 * Employee DTO
 */
public class EmployeeDTO {

    private Long id;

    private String username;

    private String fullname;

    private boolean activated = true;

    private Set<String> authorities;

    public EmployeeDTO() {}

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public String toString() {
        return (
            "EmployeeDTO [id=" +
            id +
            ", username=" +
            username +
            ", fullname=" +
            fullname +
            ", activated=" +
            activated +
            ", authorities=" +
            authorities +
            "]"
        );
    }
}
