package countlines;
import java.io.*;

public class SimpleCounter extends Counter {
    public SimpleCounter(BufferedReader _reader) {
        super(_reader);
    }


	@Override
	public LineCounts count() {
        char ch = ' ';
        boolean stillEmptyLine = true;

        readNextLine();
        ch = getChar();
        while (ch != EOF) {
            
            if (ch == EOL) {
                if (stillEmptyLine) {
                    emptyLineCount++;
                }
                stillEmptyLine = true;
            }
            if (stillEmptyLine) {
                if (ch > ' ') {
                    stillEmptyLine = false;
                }
            }
            ch = getChar();
        }
	return new LineCounts(lineCount, emptyLineCount, commentLineCount);
	}

}
