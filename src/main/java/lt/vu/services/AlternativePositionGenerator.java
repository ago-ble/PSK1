package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
@Alternative
public class AlternativePositionGenerator implements Serializable, IPositionGenerator{
    @Override
    public String generatePosition() {
        System.out.println("Using alternative implementation of PositionGenerator");

        try {
            Thread.sleep(2000); // Simulate intensive work
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        String[] positions = {
                "Engineer",
                "Sales Representative",
                "Human Resources Specialist",
                "Graphic Designer",
                "Data Analyst",
                "Quality Assurance Tester",
                "IT Support Specialist",
                "Operations Manager",
                "Business Analyst",
                "Financial Analyst",
                "Administrative Assistant",
                "Research Scientist",
                "Product Manager",
                "Operations Coordinator",
                "Content Writer"};
        Random random = new Random();
        int randomIndex = random.nextInt(positions.length);
        String generatedPosition = positions[randomIndex];

        return generatedPosition;
    }
}
