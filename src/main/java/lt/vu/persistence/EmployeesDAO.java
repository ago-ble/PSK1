package lt.vu.persistence;

import lt.vu.entities.Department;
import lt.vu.entities.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EmployeesDAO {

    @Inject
    private EntityManager em;

    public void persist(Employee employee){
        this.em.persist(employee);
    }

    public List<Employee> loadAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }
    public Employee findOne(Integer id){
        return em.find(Employee.class, id);
    }

    public Employee update(Employee employee){
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        return em.merge(employee);
    }

}
