

import java.io.Console;
import java.io.IOException;
import java.util.IllegalFormatException;

public class ConsoleIO extends IO{
	private final Console console;

	public ConsoleIO() throws UnSupportException {
		this.console = System.console();
		if (console == null) {
			throw new UnSupportException("System do not support console IO");
		}
	}

	public String readLine(String userPrompt) throws IOException {
		this.print(userPrompt);
		String line = this.console.readLine(userPrompt);
		return this.trim(line);
	}

	public void printf(String formatString, Object... args) {
		try {
			this.console.printf(formatString, args);
		} catch (IllegalFormatException e) {
			System.err.print("Error printing...\n");
		}
	}
}
