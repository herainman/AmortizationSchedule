
public class AmortizationOutput {
	private final int paymentNumber;
	private final double paymentAmount;
	private final double paymentInterest;
	private final double currentBalance;
	private final double totalPayments;
	private final double totalInterestPaid;
	
	public AmortizationOutput(int paymentNumber, double paymentAmount, double paymentInterest,
			double currentBalance, double totalPayments, double totalInterestPaid) {
		this.paymentNumber = paymentNumber;
		this.paymentAmount = paymentAmount;
		this.paymentInterest = paymentInterest;
		this.currentBalance = currentBalance;
		this.totalPayments = totalPayments;
		this.totalInterestPaid = totalInterestPaid;
	}
	
	public int getPaymentNumber() {
		return this.paymentNumber;
	}
	
	public double getPaymentAmount() {
		return this.paymentAmount;
	}
	
	public double getPaymentInterest() {
		return this.paymentInterest;
	}
	
	public double getCurrentBalance() {
		return this.currentBalance;
	}
	
	public double getTotalPayments() {
		return this.totalPayments;
	}
	
	public double getTotalInterestPaid() {
		return this.totalInterestPaid;
	}
}
