package services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import java.util.concurrent.CompletableFuture;

@Specializes
@Alternative
@ApplicationScoped
public class SlowORMCalculator extends QuickOrmCalculator {
	@Override
	public CompletableFuture<Double> CalculateOneRepMax(int reps, double weight) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			return weight * (1 + (double) reps / 30);
		});
	}
}
