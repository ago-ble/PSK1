package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "select t from Department as t")
})
@Entity
public class Employee {
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    @Getter
    @Setter
    private String name;

    private Department department;

    @ManyToOne
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    private List<Project> projects;


    @ManyToMany
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Getter
    @Setter
    private String position;

//    @Version
//    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;
    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer getVersion(){return version;}

    private void setVersion(Integer version){this.version = version;}


    @Getter
    @Setter
    private String employmentStatus;


}
