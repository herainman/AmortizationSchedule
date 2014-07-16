

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class BufferReaderIO extends IO {
	private final BufferedReader bufferedReader;

	public BufferReaderIO() {
		this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String readLine(String userPrompt) throws IOException {
		this.print(userPrompt);
		String line = bufferedReader.readLine();
		return this.trim(line);
	}

	public void printf(String formatString, Object... args) {
		try {
			System.out.print(String.format(formatString, args));
		} catch (IllegalFormatException e) {
			System.err.print("Error printing...\n");
		}
	}
}
