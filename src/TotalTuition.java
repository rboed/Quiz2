// Ryan Boedeker

import java.util.Scanner;
import org.apache.poi.ss.formula.functions.FinanceLib;

public class TotalTuition {
	
	/**
	 * Repeatedly display the prompt until a value within the specified range (inclusive) is entered
	 * @param input the scanner which collects the input
	 * @param prompt the prompt to display to the user
	 * @param min the lowest acceptable value
	 * @param max the highest acceptable value
	 * @return the value entered 
	 */
	
	public static double getBoundedDouble(Scanner input, String prompt, double min, double max) {
	    double lmin = min;
	    double lmax = max;
	    if (lmin > lmax) {
	      lmin = max;
	      lmax = min;
	    }
	    double ans = lmin - 1.0;
	    while (ans < lmin || ans > lmax) {
	      System.out.print(prompt + " (enter a value between " + lmin + " and " + lmax + "): ");
	      ans = input.nextDouble();
	    }
	    return ans;
	  }
	
	/**
	 * 
	 * @param initialTuitionCost The initial cost of tuition
	 * @param percentageIncrease The percentage increase for tuition
	 * @param repaymentApr The repayment APR
	 * @param term The term
	 * @param tuition tuition
	 */
	public void finalTuition(double initialTuitionCost, double percentageIncrease, double repaymentApr, double term, double[] tuition){
		tuition[0] = initialTuitionCost;
		int nYears = tuition.length;
		for (int i = 1; i < nYears; i++) {
			tuition[i] = -FinanceLib.fv(percentageIncrease, (double) i, 0.0, initialTuitionCost, false);
		}
	}
	
	/**
	 * 
	 * @param repaymentApr the repayment APR
	 * @param tuition tuition
	 * @return debt
	 */
	public double calcDebt(double repaymentApr, double[] tuition) {
		double debt = 0.0;
		int nYears = tuition.length;
		for (int i = 0; i < nYears; i++) {
			debt += FinanceLib.fv(repaymentApr / 12.0, (double) (nYears - i) * 12.0, 0.0, tuition[i], false);
		}
		return debt;
	}
	
	/**
	 * 
	 * @param args None expected
	 */
	
	public static void main(String[] args){
		System.out.println("This program will calculate the total cost of tuition");
	    System.out.println("to complete a degree and determine the monthly payment");
	    System.out.println("for a student loan.");
	    
	    Scanner input = new Scanner(System.in);
	    
	    double initialTuitionCost = getBoundedDouble(input, "What is the first year tuition?", 0.0, 9999999.0);
	    double percentageIncrease = getBoundedDouble(input, "By what percent will the tuition increase each year?", 1.0, 100.0);
	    double repaymentApr = getBoundedDouble(input, "What is the annual interest rate for the student loan in a percentage?", 1.0, 100.0);
	    double term = getBoundedDouble(input, "How many years will it take to pay off the loans?", 1.0, 100.0);
	    /**
	     * 
	     */
	    double yearOne = initialTuitionCost;
		double yearTwo = initialTuitionCost + initialTuitionCost*percentageIncrease;
		double yearThree = yearTwo + initialTuitionCost*percentageIncrease;
		double yearFour = yearThree + initialTuitionCost*percentageIncrease;
		
		double total = yearOne+yearTwo+yearThree+yearFour;
		double monthlyPayment = FinanceLib.pmt(repaymentApr/12, term * 12, total, 0, false);
	    
	    System.out.printf("The total debt at the end of four years will be: $%.2f",yearFour);
	    System.out.printf(". The the total monthly payment will be: $%.2f", -1*monthlyPayment);
	    return;
	}
}
