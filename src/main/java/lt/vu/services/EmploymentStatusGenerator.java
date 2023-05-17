package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
@Production
public class EmploymentStatusGenerator implements IEmplpoymentStatusGenerator{
    @Override
    public String generateEmplpoymentStatus() {
        System.out.println("Using default implementation of Employment Status Generator");

        return "Full-time";
    }
}
