package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Department;
import lt.vu.entities.Employee;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.DepartmentsDAO;
import lt.vu.persistence.EmployeesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;
@Model
public class EmployeesInDep implements Serializable {
    @Inject
    private DepartmentsDAO departmentsDAO;

    @Inject
    private EmployeesDAO employeesDAO;

    @Getter
    @Setter
    private Department department;

    @Getter @Setter
    private Employee employeeToCreate = new Employee();

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer departmentId = Integer.parseInt(requestParameters.get("departmentId"));
        this.department = departmentsDAO.findOne(departmentId);
    }

    @Transactional
    @LoggedInvocation
    public void createEmployee() {
        employeeToCreate.setDepartment(this.department);
        employeesDAO.persist(employeeToCreate);
    }



}
