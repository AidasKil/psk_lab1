package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@CustomAnnotation
public class QuickOrmCalculator implements IORMCalculator {
	public CompletableFuture<Double> CalculateOneRepMax(int reps, double weight) {
		return CompletableFuture.completedFuture(weight * (1 + (double) reps / 30));
	}
}
