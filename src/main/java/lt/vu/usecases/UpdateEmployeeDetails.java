package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Employee;
import lt.vu.entities.Project;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.EmployeesDAO;
import lt.vu.persistence.ProjectsDAO;
import lt.vu.services.IEmplpoymentStatusGenerator;
import lt.vu.services.Production;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Inject
    @Production
    private IEmplpoymentStatusGenerator statusGenerator;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer employeeId = Integer.parseInt(requestParameters.get("employeeId"));
        this.employee = employeesDAO.findOne(employeeId);
    }
    @Transactional
    @LoggedInvocation
    public void addProject() {

        List<Project> existingProject =  projectsDAO.loadAll().stream().
                filter(c -> c.getName().equals(projectToCreate.getName())).collect(Collectors.toList());

        //projectsDAO.loadAll().stream().filter(c -> c.getName()== projectToCreate.getName())
        //Project existingProject = projectsDAO.findByName(projectToCreate.getName());

        if(existingProject.isEmpty()){
            createProject();
        }else{
            existingProject.get(0).getEmployees().add(this.employee);
            projectsDAO.update(existingProject.get(0));
            updateEmployeeProjects(existingProject.get(0));
        }
    }
    @Transactional
    @LoggedInvocation
    public void createProject() {
        projectToCreate.getEmployees().add(this.employee);
        projectsDAO.persist(projectToCreate);
        updateEmployeeProjects(projectToCreate);
        System.out.println("UpdateEmployeeDetails create project called");
    }
    @Transactional
    @LoggedInvocation
    public String updateEmployeeProjects(Project project) {
        try{
            if(this.employee.getProjects().contains(project)){
                System.out.println("Employee already has this project");
            }else{
                employee.getProjects().add(project);
                employeesDAO.update(this.employee);
            }
        } catch (OptimisticLockException e) {
            return "/employeeDetails.xhtml?faces-redirect=true&employeeId=" + this.employee.getId() + "&error=optimistic-lock-exception";
        }
        return "employees.xhtml?departmentId=" + this.employee.getDepartment().getId() + "&faces-redirect=true";
    }

    public String getStatus(){
        return statusGenerator.generateEmplpoymentStatus();
    }


}
