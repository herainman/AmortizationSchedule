import java.util.ArrayList;
import java.util.List;


public class AmortizationSchedule {
	private long amountBorrowed;
	private int initialTermMonths;
	private double monthlyInterest;
	private long monthlyPaymentAmount;
	private List<AmortizationOutput> outputList;
	
	public AmortizationSchedule(AmortizationInput input) {
		if (input == null) {
			throw new IllegalArgumentException("invalid input"); 
		}
		
		this.outputList = new ArrayList<AmortizationOutput>();
		this.amountBorrowed = Math.round(input.getAmount() * 100);
		this.initialTermMonths = input.getYears() * 12;
		calculate(input.getApr());
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

		io.printf(Format.HEADER, "PaymentNumber", "PaymentAmount", "PaymentInterest", "CurrentBalance", "TotalPayments", "TotalInterestPaid");   
		for (AmortizationOutput a : this.outputList) {
			io.printf(Format.BODY, a.getPaymentNumber(), a.getPaymentAmount(), a.getPaymentInterest(),
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
}
