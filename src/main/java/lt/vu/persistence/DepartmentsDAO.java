package lt.vu.persistence;

import lt.vu.entities.Department;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class DepartmentsDAO {

    @Inject
    private EntityManager em;

    public List<Department> loadAll() {
        return em.createNamedQuery("Department.findAll", Department.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist (Department department){
        this.em.persist(department);
    }

    public Department findOne(Integer id) {
        return em.find(Department.class, id);
    }

}
