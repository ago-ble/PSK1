package lt.vu.persistence;
import lt.vu.entities.Project;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
@ApplicationScoped
public class ProjectsDAO {

    @Inject
    private EntityManager em;

    public List<Project> loadAll() {
        return em.createNamedQuery("Project.findAll", Project.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist (Project project){
        this.em.persist(project);
    }

    public Project findOne(Integer id) {
        return em.find(Project.class, id);
    }

    public Project findByName(String name) {
        return em.find(Project.class, name);
    }

    public Project update(Project project) {
        return em.merge(project);
    }
}
