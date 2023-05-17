package lt.vu.services;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
@Decorator
public class ManagerPositionGenerator implements IPositionGenerator{
    @Inject
    @Delegate
    @Any
    private IPositionGenerator delegate;
    @Override
    public String generatePosition() {
        System.out.println("Using decorated implementation of Position Generator");
        String originalPosition = delegate.generatePosition();
        return "Manager " + originalPosition;
    }
}
