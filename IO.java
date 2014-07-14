
import java.io.IOException;

public abstract class IO {
	public abstract String readLine(String userPrompt) throws IOException;

	public abstract void printf(String formatString, Object... args);

	public void print(String s) {
		printf("%s", s);
	}

	public String trim(String s) {
		return (s == null) ? null : s.trim();
	}
}
