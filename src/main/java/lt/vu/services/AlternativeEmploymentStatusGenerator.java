package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
@ApplicationScoped
@Alternative
@Specializes
public class AlternativeEmploymentStatusGenerator extends EmploymentStatusGenerator{
    @Override
    public String generateEmplpoymentStatus() {
        System.out.println("Using specialized implementation of Employment Status Generator");

        return "Part-time";
    }
}
