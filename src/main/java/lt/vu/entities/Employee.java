package lt.vu.entities;

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

    private String name;

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

}
