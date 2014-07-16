import java.io.IOException;


public class ExcuteAmortizationSchedule {
	public static void main(String args[]) {
		excute();
	}
	
	public static void excute() {
		InputReader reader = new InputReader();
		double amount = 0d;
		double apr = 0d;
		int years = 0;
		AmortizationInput input = null;
		
		try {
			amount = reader.readInput(UserFacingStrings.INPUT_AMOUNT, Range.BORROW_AMOUNT);
			apr = reader.readInput(UserFacingStrings.INPUT_PERCENTAGE_RATE, Range.APR);
			years = reader.readInput(UserFacingStrings.INPUT_TERM, Range.TERM);
			input = new AmortizationInput(amount, apr, years);
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
			return;
		} catch (IllegalArgumentException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
			return;
		}
			
		try {
			AmortizationSchedule as = new AmortizationSchedule(input);
			as.outputAmortizationSchedule();
		} catch (IllegalArgumentException e) {
			System.err.println("Unable to process the values entered. Terminating program.\n");
		}
	}
}