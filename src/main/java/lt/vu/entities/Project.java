package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "select t from Project as t")
})
@Table(name = "PROJECT")
@Getter
@Setter
public class Project {

    public Project(){

    }

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "projects")
    public List<Employee> employees = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
