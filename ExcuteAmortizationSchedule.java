import java.io.IOException;


public class ExcuteAmortizationSchedule {
	public void excute() {
		InputReader reader = new InputReader();
		double amount = 0d;
		double apr = 0d;
		int years = 0;;
		
		try {
			amount = reader.readInput(UserFacingStrings.INPUT_AMOUNT, Range.BORROW_AMOUNT);
			apr = reader.readInput(UserFacingStrings.INPUT_PERCENTAGE_RATE, Range.APR);
			years = reader.readInput(UserFacingStrings.INPUT_TERM, Range.TERM);
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
			return;
		}
			
		try {
			AmortizationSchedule as = new AmortizationSchedule(amount, apr,	years);
			as.outputAmortizationSchedule();
		} catch (IllegalArgumentException e) {
			System.err.println("Unable to process the values entered. Terminating program.\n");
		}
	}
}