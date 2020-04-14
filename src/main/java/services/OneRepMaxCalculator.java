package services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OneRepMaxCalculator {
	public double CalculateOneRepMax(int reps, double weight) {
		return weight*(1+(double)reps/30);
	}
}
