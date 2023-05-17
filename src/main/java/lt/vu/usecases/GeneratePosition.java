package lt.vu.usecases;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.services.IPositionGenerator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GeneratePosition implements Serializable {
    @Inject
    IPositionGenerator positionGenerator;

    private CompletableFuture<String> positionGenerationTask = null;

    @LoggedInvocation
    public String generateNewPosition() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        positionGenerationTask = CompletableFuture.supplyAsync(() -> positionGenerator.generatePosition());

        return  "/employeeDetails.xhtml?faces-redirect=true&employeeId=" + requestParameters.get("employeeId");
    }

    public boolean isPositionGenerationRunning() {
        return positionGenerationTask != null && !positionGenerationTask.isDone();
    }

    public String getPositionGenerationStatus() throws ExecutionException, InterruptedException {
        if (positionGenerationTask == null) {
            return null;
        } else if (isPositionGenerationRunning()) {
            return "Position generation in progress";
        }
        return "Suggested position: " + positionGenerationTask.get();
    }


}
