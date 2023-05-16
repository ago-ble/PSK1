package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
@ApplicationScoped
public class PositionGenerator implements Serializable, IPositionGenerator {
    @Override
    public String generatePosition() {
        System.out.println("Using default implementation of PositionGenerator");

        try {
            Thread.sleep(2000); // Simulate intensive work
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return "employee";
    }
}
