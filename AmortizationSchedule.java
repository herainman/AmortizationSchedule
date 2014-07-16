import java.util.ArrayList;
import java.util.List;


public class AmortizationSchedule {
	private long amountBorrowed;
	private int initialTermMonths;
	private double monthlyInterest;
	private long monthlyPaymentAmount;
	private List<AmortizationOutput> outputList;
	
	public AmortizationSchedule(double amount, double interestRate, int years) {
		if (!RangeValidator.isValid(amount, Range.BORROW_AMOUNT) || !RangeValidator.isValid(interestRate, Range.APR) 
				|| !RangeValidator.isValid(years, Range.TERM)) {
			throw new IllegalArgumentException("invalid input"); 
		}
		
		this.outputList = new ArrayList<AmortizationOutput>();
		this.amountBorrowed = Math.round(amount * 100);
		this.initialTermMonths = years * 12;
		calculate(interestRate);
	}
	
	private void calculate(double interestRate) {
		this.monthlyPaymentAmount = calculateMonthlyPayment(interestRate);
		if (this.monthlyPaymentAmount > this.amountBorrowed) { 
			throw new IllegalArgumentException();   
		}
		
		generateOutput();
	}
	
	private long calculateMonthlyPayment(double interestRate) { 
		double monthlyInterestDivisor = 12d * 100d; 
		this.monthlyInterest = interestRate / monthlyInterestDivisor; 
		double tmp = Math.pow(1d + this.monthlyInterest, -1); 
		tmp = Math.pow(tmp, this.initialTermMonths);
		tmp = Math.pow(1d - tmp, -1); 
		double rc = this.amountBorrowed * this.monthlyInterest * tmp; 
		return Math.round(rc);
	}
	
	public synchronized void outputAmortizationSchedule() {
		IO io;
		try {
			io = new ConsoleIO();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			io = new BufferReaderIO();
		}
		
		String formatString = "%1$-20s%2$-20s%3$- 20s%4$s,%5$s,%6$s\n";  
		io.printf(formatString, "PaymentNumber", "PaymentAmount", "PaymentInterest", "CurrentBalance", "TotalPayments", "TotalInterestPaid");   
		
		formatString = "%1$-20d%2$-20.2f%3$- 20.2f%4$.2f,%5$.2f,%6$.2f\n"; 
		for (AmortizationOutput a : this.outputList) {
			io.printf(formatString, a.getPaymentNumber(), a.getPaymentAmount(), a.getPaymentInterest(),
					a.getCurrentBalance(), a.getTotalPayments(), a.getTotalInterestPaid()); 
		}
	}
	
	private void generateOutput() {
		long balance = this.amountBorrowed;   
		int paymentNumber = 0;  
		long totalPayments = 0;  
		long totalInterestPaid = 0; 
		
		this.outputList.add(new AmortizationOutput(paymentNumber++, 0d, 0d, ((double) this.amountBorrowed)/100d,
				((double) totalPayments)/100d, ((double) totalInterestPaid)/100d));
	
		final int maxNumberOfPayments = this.initialTermMonths + 1;  
		while ((balance > 0) && (paymentNumber <= maxNumberOfPayments)) { 
			long curMonthlyInterest = Math.round(((double) balance) * this.monthlyInterest); 
			long curPayoffAmount = balance + curMonthlyInterest; 
			long curMonthlyPaymentAmount = Math.min(this.monthlyPaymentAmount, curPayoffAmount); 
			if ((paymentNumber == maxNumberOfPayments) && ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) { 
				curMonthlyPaymentAmount = curPayoffAmount;  
			}
			long curMonthlyPrincipalPaid = curMonthlyPaymentAmount - curMonthlyInterest; 
			long curBalance = balance - curMonthlyPrincipalPaid;     
			totalPayments += curMonthlyPaymentAmount;    
			totalInterestPaid += curMonthlyInterest; 
			this.outputList.add(new AmortizationOutput(paymentNumber++, ((double) curMonthlyPaymentAmount)/100d, ((double) curMonthlyInterest)/100d,
					((double) curBalance)/100d, ((double) totalPayments)/100d, ((double) totalInterestPaid) / 100d)); 
			balance = curBalance;
		}
	}
	
	private class AmortizationOutput {
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
}
