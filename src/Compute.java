import java.math.*;
import java.util.Arrays;
import java.text.*;

public class Compute {
	
	public static DecimalFormat zinc = new DecimalFormat("0.000%");
	
	// This method will be responsible for storing your previous prices
	
	public static double[] prePrice(double[] prices){
		double[] result = new double[prices.length];
		for(int i = 0; i < prices.length; i++){
			result[i] = prices[i];
		}
		return result;
	}
	
	// This method will be responsible for computing the delta between the prices
	
	public static double[] Delta(double[] curr, double[] pre){
		
		double[] result = new double[curr.length];
		
		for(int i = 0; i < curr.length; i++){
			result[i] = (curr[i]/pre[i]) - 1.0;
		}
		
		return result;
	}
	
	// This method will be responsible for storing all of the deltas to be analyzed
	
	public static double[][] StoreData(double[][] store, double[] delta, int xz){
		
		for(int i = 0; i < delta.length; i++){
			store[i][xz] = delta[i];
		}
		
		
		return store;
	}
	
	
	// This method will give you your expected return and volatility
	
	public static double[][] RR(double[][] store, int dx){
		
		double[][] rr = new double[store.length][dx];
		
		for(int i = 0; i < store.length; i++){
			for(int j = 0; j < dx; j++){
				rr[i][0] += store[i][j];
			}
			rr[i][0] /= (double) dx;
		}
		
		for(int i = 0; i < store.length; i++){
			for(int j = 0; j < dx; j++){
				rr[i][1] += Math.pow(store[i][j] - rr[i][0], 2);
			}
			rr[i][1] /= (double) dx;
			rr[i][1] = Math.sqrt(rr[i][1]);
		}
		
		
		return rr;
	}
	
	// This method will compute your covariance matrix
	
	public static double[][] Covariance(double[][] store, double[][] rr, int dx){
		
		double[][] covA = new double[store.length][dx];
		double[][] covB = new double[dx][store.length];
		double[][] covar = new double[store.length][store.length];
		
		for(int i = 0; i < store.length; i++){
			for(int j = 0; j < dx; j++){
				covA[i][j] = store[i][j] - rr[i][0];
				covB[j][i] = covA[i][j];
			}
		}
		
		for(int i = 0; i < store.length; i++){
			for(int j = 0; j < store.length; j++){
				for(int k = 0; k < dx; k++){
					covar[i][j] += covA[i][k] * covB[k][j];
				}
				covar[i][j] /= (double) dx;
			}
		}
		
		return covar;
	}
	
	// This method will compute your correlation matrix
	
	public static double[][] Correlation(double[][] covar, double[][] rr){
		
		double[][] result = new double[covar.length][covar.length];
		
		for(int i = 0; i < covar.length; i++){
			for(int j = 0; j < covar.length; j++){
				result[i][j] = covar[i][j] / (rr[i][1] * rr[j][1]);
			}
		}
		
		return result;
	}
	
	// This method will calculate all of your sharpe ratios
	
	public static double[] Sharpe(double[][] rr){
		
		double[] result = new double[rr.length];
		
		for(int i = 0; i < result.length; i++){
			result[i] = rr[i][0] / rr[i][1];
		}
		
		return result;
	}
	
	// This is your sigmoid function for your Neural Net
	
	public static double Sigmoid(double x, int d){
		
		double y = 1.0 / (1.0 + Math.exp(-x));
		
		if(d == 1){
			y *= (1.0 - y);  // Calculates the sigmoid function derivative
		}
		
		return y;
	}
	
	// This method contains a neural net that your program will use to optimize your portfolio 
	// weights across the financial markets
	
	public static double[] NeuralNet(double[] sharpe){
		
		int n = sharpe.length;
		
		double[] w = new double[n];
		
		double O = 0, O2 = 0, d1 = 0, d2 = 0, e1 = 0, e2 = 0;
		
		double err = 0.0001;
		double b1 = -1;
		
		// The Artificial Intelligence portion of the program starts running
		
		for(int i = 0; i < n; i++){w[i] = Math.random();}		
		
		while(true){
			
			//O = 0;
			O2 = 0;
			
			// Conducting forward propigation to minimize risk and maximize return
			
			for(int i = 0; i < n; i++){
				if(w[i] < 0){
					w[i] = 0;
				}
				if(Double.isNaN(sharpe[i])){
					
				} else {
					O += w[i] * sharpe[i];
					O2 += w[i];
				}
				
			}
			
			O += b1;
			O = Sigmoid(O,0);
			
			O2 = Sigmoid(O2,0);
			
			e1 = 1.0 - O;
			e2 = Sigmoid(1.0,0) - O2;
			
			d1 = e1 * Sigmoid(O,1);
			d2 = e2 * Sigmoid(O2,1);
			
			if(Math.abs(e1) >= err || Math.abs(e2) >= err){
				for(int i = 0; i < n; i++){
					w[i] += d1 * sharpe[i];
					w[i] += d2;
				}
				b1 += (e1 + e2);
				
			} else {
				
				return w;
			}
			
		}
		
		
	}
	
	
	// This method computes your Value At Risk
	
	public static double[] VaR(double[][] KK){
		
		double[][] ror = new double[KK.length][KK[0].length];
		
		double[] result = new double[ror.length];
		
		double p = 0.95 * (double) ror[0].length;
		int d = (int) Math.round(p);
		
		for(int i = 0; i < KK.length; i++){
			for(int j = 0; j < KK[i].length; j++){
				ror[i][j] = KK[i][j];
			}
		}
		
		for(int i = 0; i < ror.length; i++){
			Arrays.sort(ror[i]);
			result[i] = ror[i][ror[0].length - d];
		}
		
		return result;
	}
	
	// Converts your correlation table to words
	
	public static double[][] FixCorr(double[][] corr){
		double[][] result = new double[corr.length][corr.length];
		
		for(int i = 0; i < corr.length; i++){
			for(int j = i + 1; j < corr.length; j++){
				result[i][j] = corr[i][j];
			}
		}
						
		return result;
	}
	
	// This method finds all of your ranks and constructs them into a string\
	
	public static String MainPage(double[][] corr, double[][] rr, double[] VaR, double[] w, String[] names){
		
		String r = "<html>";
		
		double temp = -5000, kemp = 5000;
		int a = 0, b = 0, c = 0, d = 0;
		
		// Rank correlations
		
		for(int i = 0; i < corr.length; i++){
			for(int j = 0; j < corr.length; j++){
				if(i != j){
					if(temp < corr[i][j]){
						temp = corr[i][j];
						a = i;
						b = j;
					}
					if(kemp > corr[i][j]){
						kemp = corr[i][j];
						c = i;
						d = j;
					}
				}
			}
		}
		
		// Rank lowest and highest VaR
		
		temp = -5000;
		kemp = 5000;
		
		int e = 0, f = 0, g = 0;
		
		for(int i = 0; i < VaR.length; i++){
			if(temp < VaR[i]){
				temp = VaR[i];
				e = i;
			}
			if(kemp > VaR[i]){
				kemp = VaR[i];
				f = i;
			}
		}
		
		// Rank lowest and highest Volatilities
		
		temp = -5000;
		kemp = 5000;
		
		int h = 0, k = 0, l = 0;
		
		for(int i = 0; i < VaR.length; i++){
			if(temp < rr[i][1]){
				temp = rr[i][1];
				h = i;
			}
			if(kemp > rr[i][1]){
				kemp = rr[i][1];
				k = i;
			}
		}
		
		
		
		
		r += names[a] + " and " + names[b] + " are the most positively correlated markets<BR>";
		r += names[c] + " and " + names[d] + " are the most negatively correlated markets<BR>";
		r += names[e] + " has the lowest Value at Risk (95%) while " + names[f] + " has the highest<BR>";
		r += names[h] + " is the most volatile market while " + names[k] + " is the least<BR>";
		
		
		r += "</html>";
		
		return r;
	}
	
	// This method calculates all of your 'Beta' metrics for each sector in relation to the S&P 500
	
	public static double[] Beta(double[][] storage, double[][] rr, int dx){
		
		int n = storage.length;
		
		double[] beta = new double[n];
		
		double varSP = Math.pow(rr[0][1],2);
		
		for(int i = 0; i < n; i++){
			for(int j = 0; j < dx; j++){
				beta[i] += (storage[i][j] - rr[i][0]) * (storage[0][j] - rr[0][0]);
			}
			beta[i] /= (double) dx;
			beta[i] /= varSP;
		}
		
		
		return beta;
	}
	
	
}
