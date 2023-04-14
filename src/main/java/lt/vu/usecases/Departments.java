package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Department;
import lt.vu.persistence.DepartmentsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
@Model
public class Departments {

    @Inject
    private DepartmentsDAO departmentsDAO;

    @Getter
    @Setter
    private Department departmentToCreate = new Department();

    @Getter
    private List<Department> allDepartments;

    @PostConstruct
    public void init(){
        loadAllDepartments();
    }

    @Transactional
    public void createDepartment(){
        this.departmentsDAO.persist(departmentToCreate);
    }

    private void loadAllDepartments(){
        this.allDepartments = departmentsDAO.loadAll();
    }
}
