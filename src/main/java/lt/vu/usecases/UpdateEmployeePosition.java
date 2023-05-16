package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Employee;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.EmployeesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@Model
@RequestScoped
public class UpdateEmployeePosition implements Serializable {
    @Inject
    private EmployeesDAO employeesDAO;

    @Getter
    private Employee employee;

    @Getter
    @Setter
    private String position;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer employeesId = Integer.parseInt(requestParameters.get("employeeId"));
        this.employee = employeesDAO.findOne(employeesId);
    }

    @Transactional
    @LoggedInvocation
    public String updateEmployeePosition() {
        this.employee.setPosition(position);
        System.out.println("position1  "+this.employee.getName() + " position " + this.employee.getPosition() );
        try{
            employeesDAO.update(this.employee);
        } catch (OptimisticLockException e) {
            return "/employeeDetails.xhtml?faces-redirect=true&employeeId=" + employee.getId() + "&error=optimistic-lock-exception";
        } catch (Exception e){
            System.out.println("Exception been thrown");
        }

        return "employeeDetails?employeeId=" + this.employee.getId() + "&faces-redirect=true";
    }

}
