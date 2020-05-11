package services;

import java.util.concurrent.CompletableFuture;

public interface IORMCalculator {
	CompletableFuture<Double> CalculateOneRepMax(int reps, double weight);
}
