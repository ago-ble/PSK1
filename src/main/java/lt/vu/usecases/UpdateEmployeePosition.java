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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
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
        employee.setPosition(position);
        try{
            employeesDAO.update(this.employee);
        } catch (OptimisticLockException e) {
            return handleOptimisticLockException();
        } catch (Exception e){
            System.out.println("Exception been thrown");
        }
        return "employeeDetails?employeeId=" + this.employee.getId() + "&faces-redirect=true";
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @LoggedInvocation
    public String handleOptimisticLockException(){
        this.employee = employeesDAO.findOne(this.employee.getId());
        return "/employeeDetails.xhtml?faces-redirect=true&employeeId=" + employee.getId() + "&error=optimistic-lock-exception";

    }

}
