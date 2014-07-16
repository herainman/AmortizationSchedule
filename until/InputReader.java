import java.io.IOException;


public class InputReader {
	private IO io;

	public InputReader() {
		try {
			io = new ConsoleIO();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			io = new BufferReaderIO();
		}
	}
	
	public int readInput(String UserFacingString, int[] range) throws IOException {
		int result = 0;
		boolean valid = false;
		
		while(!valid) {
			String line = io.readLine(UserFacingString + ", "  + String.format(UserFacingStrings.INPUT_RANGE_INTEGER, range[0], range[1]));
			if (line != null) {
				try {
					result = Integer.parseInt(line);
					valid = RangeValidator.isValid(result, range);
				} catch (NumberFormatException e) {
				}
			}
		}
		
		return result;
	}
	
	public double readInput(String UserFacingString, double[] range) throws IOException {
		double result = 0;
		boolean valid = false;
		
		while(!valid) {
			String line = io.readLine(UserFacingString + ", "  + String.format(UserFacingStrings.INPUT_RANGE_DOUBLE, range[0], range[1]));
			if (line != null) {
				try {
					result = Double.parseDouble(line);
					valid = RangeValidator.isValid(result, range);
				} catch (NumberFormatException e) {
				}			
			}
		}
		
		return result;
	}
}
