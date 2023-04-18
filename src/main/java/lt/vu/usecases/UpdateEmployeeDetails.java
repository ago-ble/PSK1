package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Employee;
import lt.vu.entities.Project;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.EmployeesDAO;
import lt.vu.persistence.ProjectsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
public class UpdateEmployeeDetails implements Serializable {
    @Inject
    private ProjectsDAO projectsDAO;

    @Inject
    private EmployeesDAO employeesDAO;

    @Getter
    @Setter
    private Employee employee;

    @Getter @Setter
    private Project projectToCreate = new Project();

    @PostConstruct
    public void init() {
        System.out.println("UpdateEmployeeDetails INIT CALLED");
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer employeeId = Integer.parseInt(requestParameters.get("employeeId"));
        this.employee = employeesDAO.findOne(employeeId);
    }

    @Transactional
    @LoggedInvocation
    public void createProject() {
        projectToCreate.getEmployees().add(this.employee);
        projectsDAO.persist(projectToCreate);
        updateEmployeeProjects();
        System.out.println("UpdateEmployeeDetails create project called");
    }
    @Transactional
    @LoggedInvocation
    public String updateEmployeeProjects() {
        try{
            employee.getProjects().add(projectToCreate);
            employeesDAO.update(this.employee);
            System.out.println("UpdateEmployeeDetails update employee called");
        } catch (OptimisticLockException e) {
            return "/employeeDetails.xhtml?faces-redirect=true&employeeId=" + this.employee.getId() + "&error=optimistic-lock-exception";
        }
        return "employees.xhtml?departmentId=" + this.employee.getDepartment().getId() + "&faces-redirect=true";
    }

}
