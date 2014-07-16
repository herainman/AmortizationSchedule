public class RangeValidator {
	public static boolean isValid(int amount, int[] range) {
		return ((range[0] <= amount) && (amount <= range[1]));
	}
	
	public static boolean isValid(double amount, double[] range) {
		return ((range[0] <= amount) && (amount <= range[1]));
	}
}
