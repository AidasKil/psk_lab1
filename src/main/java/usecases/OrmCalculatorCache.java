package usecases;

import lombok.var;
import services.CustomAnnotation;
import services.IORMCalculator;
import services.SlowORMCalculator;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SessionScoped
@Model
public class OrmCalculatorCache implements Serializable {
	private @Inject @CustomAnnotation IORMCalculator ormCalculator;

	private class Orm {
		public Integer reps;
		public double weight;
		public CompletableFuture<Double> calculationTask;
	}

	private List<Orm> ormCalculationTasks = new ArrayList<>();

	public boolean isCalculating(int reps, int weight) {
		var ormTask = ormCalculationTasks.stream()
				.filter(x -> x.reps == reps && x.weight == x.weight)
				.findFirst()
				.orElse(null);

		return ormTask != null && !ormTask.calculationTask.isDone();
	}

	public String getOrm(int reps, double weight) {
		var ormTask = ormCalculationTasks.stream()
				.filter(x -> x.reps == reps && x.weight == x.weight)
				.findFirst()
				.orElse(null);

		if (ormTask == null) {
			ormTask = new Orm();
			ormTask.weight = weight;
			ormTask.reps = reps;
			ormTask.calculationTask = ormCalculator.CalculateOneRepMax(reps, weight);

			ormCalculationTasks.add(ormTask);
		}

		if (!ormTask.calculationTask.isDone())
			return "Calculating...";
		else {
			try {
				return String.format("%.2f kg", ormTask.calculationTask.get());
			} catch (Exception e) {
				return e.getMessage();
			}
		}
	}
}
