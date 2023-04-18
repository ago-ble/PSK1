package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.DepartmentMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import lt.vu.mybatis.model.Department;

@Model
public class DepartmentsMyBatis {
    @Inject
    private DepartmentMapper departmentMapper;

    @Getter
    private List<Department> allDepartments;

    @Getter @Setter
    private Department departmentToCreate = new Department();

    @PostConstruct
    public void init() {
        this.loadAllDepartments();
    }

    private void loadAllDepartments() {
        this.allDepartments = departmentMapper.selectAll();
    }

    @Transactional
    public String createDepartment() {System.out.println("KURIA DEPARTMENT");
        departmentMapper.insert(departmentToCreate);

        return "/myBatis/departments?faces-redirect=true";
    }
}
