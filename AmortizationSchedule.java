public class AmortizationSchedule {
	private long amountBorrowed;
	private int initialTermMonths;
	private double monthlyInterest;
	private long monthlyPaymentAmount;
	
	public AmortizationSchedule(double amount, double interestRate, int years) {
		if (!RangeValidator.isValid(amount, Range.BORROW_AMOUNT) || !RangeValidator.isValid(interestRate, Range.APR) 
				|| !RangeValidator.isValid(years, Range.TERM)) {
			throw new IllegalArgumentException("invalid input"); 
		}
		
		this.amountBorrowed = Math.round(amount * 100);
		this.initialTermMonths = years * 12;
		calculate(interestRate);
	}
	
	private void calculate(double interestRate) {
		this.monthlyPaymentAmount = calculateMonthlyPayment(interestRate);
		if (this.monthlyPaymentAmount > this.amountBorrowed) { 
			throw new IllegalArgumentException();   
		}
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
		
		long balance = this.amountBorrowed;   
		int paymentNumber = 0;  
		long totalPayments = 0;  
		long totalInterestPaid = 0; 
		
		formatString = "%1$-20d%2$-20.2f%3$- 20.2f%4$.2f,%5$.2f,%6$.2f\n"; 
		io.printf(formatString, paymentNumber ++, 0d, 0d, ((double) this.amountBorrowed)/100d, 
				((double) totalPayments)/100d, ((double) totalInterestPaid)/100d); 
	
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
			io.printf(formatString, paymentNumber++, ((double) curMonthlyPaymentAmount)/100d, ((double) curMonthlyInterest)/100d,
					((double) curBalance)/100d, ((double) totalPayments)/100d, ((double) totalInterestPaid) / 100d); 
			balance = curBalance;
		}
	}
}
