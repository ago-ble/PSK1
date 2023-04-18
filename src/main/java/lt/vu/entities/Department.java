package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = "Department.findAll", query = "select t from Department as t")
})
@Table(name = "DEPARTMENT")
@Getter
@Setter
public class Department {
    public Department(){

    }

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equals(name, department.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

}
