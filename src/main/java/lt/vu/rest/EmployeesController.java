package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Employee;
import lt.vu.persistence.EmployeesDAO;
import lt.vu.rest.contracts.EmployeeDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/employees")
public class EmployeesController {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer id) {
        Employee employee = employeesDAO.findOne(id);
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employee.getName());
        employeeDto.setProjects(employee.getProjects());
        employeeDto.setDepartmentName(employee.getDepartment().getName());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setEmploymentStatus(employee.getEmploymentStatus());

        return Response.ok(employeeDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response add(EmployeeDto employeeData) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Employee newEmployee = new Employee();

        newEmployee.setName(employeeData.getName());
        newEmployee.setProjects(employeeData.getProjects());
        newEmployee.setPosition(employeeData.getPosition());
        newEmployee.setEmploymentStatus(employeeData.getEmploymentStatus());

        employeesDAO.persist(newEmployee);

        return Response.ok().build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer employeeId,
            EmployeeDto employeeData) {
        try {
            Employee existingEmployee = employeesDAO.findOne(employeeId);
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            if (existingEmployee == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingEmployee.setName(employeeData.getName());
            existingEmployee.setProjects(employeeData.getProjects());
            if(!employeeData.getPosition().isEmpty()){
                existingEmployee.setPosition(employeeData.getPosition());
            }
            if(!employeeData.getEmploymentStatus().isEmpty()){
                existingEmployee.setEmploymentStatus(employeeData.getEmploymentStatus());
            }
            employeesDAO.update(existingEmployee);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }



}
