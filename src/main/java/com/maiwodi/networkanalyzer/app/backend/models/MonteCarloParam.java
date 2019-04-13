package com.maiwodi.networkanalyzer.app.backend.models;

public class MonteCarloParam {
	double S = 60;
	double K = 10;
	double T = 1 / 1.0;
	double r = 0.12;
	int N = 100;
	int M = 1000;

	public MonteCarloParam() {
		super();
	}

	public MonteCarloParam(double s, double k, double t, double r, int n, int m) {
		super();
		S = s;
		K = k;
		T = t;
		this.r = r;
		N = n;
		M = m;
	}

	public double getS() {
		return S;
	}

	public void setS(double s) {
		S = s;
	}

	public double getK() {
		return K;
	}

	public void setK(double k) {
		K = k;
	}

	public double getT() {
		return T;
	}

	public void setT(double t) {
		T = t;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

}
