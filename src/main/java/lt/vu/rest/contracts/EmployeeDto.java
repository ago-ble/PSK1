package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Project;

import java.util.List;

@Getter
@Setter
public class EmployeeDto {
    private String Name;

    private List<Project> Projects;

    private String DepartmentName;

    private String Position;
}
