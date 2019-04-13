package com.maiwodi.networkanalyzer.app.backend;

import java.util.ArrayList;
import java.util.List;

public class MonteCarloSimulation {

	public static void main(String[] args) {
		double S = 60;
		double K = 10;
		double T = 1 / 1.0;
		double r = 0.12;
		int N = 1000;
		int M = 100000;

		MonteCarloSimulation monteCarloSimulation = new MonteCarloSimulation();
		double optionPrice = monteCarloSimulation.mcSimulation(K, T, S, r, 0.4, 0.0, N, M);

		System.out.println(optionPrice);

//		 monteCarloSimulation.timeStepVsOption(K, T, S, r, 0.4, 0.0, N, M);
		// monteCarloSimulation.strikepricesVsOption(K, T, S, r, 0.4, 0.0, N, M);
//		 monteCarloSimulation.numSimVsOption(K, T, S, r, 0.4, 0.0, N, M);
	}

	public static double mcSimulation(double K, double T, double S, double r, double sigma, double theta, int N,
			int M) {

		// sigma should be 0.4 volatility

		// Compute the constant

		double dt = T / N;
		double nudt = (r - theta - (Math.pow(sigma, 2) / 2.0)) * dt;
		double sigsdt = sigma * Math.pow(dt, 0.5);

		double lnS = Math.log(S);

		double sum_CT = 0;
		double sum_CT2 = 0;

		List<MCRun> mcRuns = new ArrayList<MCRun>();

		// Start Simulations
		for (int j = 1; j <= M; j++) {
			double lnSt = lnS;

			double[] x = new double[N];
			double[] y = new double[N];

			for (int i = 1; i <= N; i++) {
				double eps = Math.random() * 2 - 1;
				// involve the stock price
				lnSt = lnSt + nudt + sigsdt * eps;

				x[i - 1] = i;
				y[i - 1] = lnSt;

			}
			mcRuns.add(new MCRun(x, y));
			double ST = Math.exp(lnSt);
			double CT = Math.max(0, ST - K);
			sum_CT = sum_CT + CT;
			sum_CT2 = sum_CT2 + Math.pow(CT, 2);
		}
//		this.plotMonteCarlo(mcRuns);
//		this.analyseMCRuns(mcRuns);

		double option_value = sum_CT / M * Math.exp(-r * T);

		double SD = Math.sqrt((sum_CT2 - (Math.pow(sum_CT, 2) / M)) * (Math.exp(-2 * r * T) / (M - 1)));
		double SE = SD / Math.sqrt(M);

		System.out.println("SD: " + SD);
		System.out.println("SE: " + SE);

		return option_value;
	}
}

class MCRun {

	double[] x;
	double[] y;

	public MCRun(double[] x, double[] y) {
		this.x = x;
		this.y = y;
	}

	public double[] getX() {
		return x;
	}

	public double[] getY() {
		return y;
	}

}
