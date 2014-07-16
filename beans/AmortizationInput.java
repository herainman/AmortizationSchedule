public class AmortizationInput {
	private final double amount;
	private final double apr;
	private final int years;

	public AmortizationInput(double amount, double apr, int years) {
		if (!RangeValidator.isValid(amount, Range.BORROW_AMOUNT) || !RangeValidator.isValid(apr, Range.APR) || !RangeValidator.isValid(years, Range.TERM)) {
			throw new IllegalArgumentException("invalid input"); 
		}
		
		this.amount = amount;
		this.apr = apr;
		this.years = years;
	}

	public double getAmount() {
		return this.amount;
	}

	public double getApr() {
		return this.apr;
	}

	public int getYears() {
		return this.years;
	}
}
