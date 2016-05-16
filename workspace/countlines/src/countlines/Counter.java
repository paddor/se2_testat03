package countlines;
import java.io.*;

abstract class Counter {
    protected static final char EOL = '\n';
    protected static final char EOF = '\0';

    protected int lineCount = 0;
    protected int emptyLineCount = 0;
    protected int commentLineCount = 0;
    private int nextCharIndex = 0;

    private String currentLine = null;
    private BufferedReader reader;
    private boolean atEOF = false; // ugly because method name describes something else

    private boolean countBracketAsEmpty = true;

    public Counter(BufferedReader _reader) {
        lineCount = 0;
        emptyLineCount = 0;
        commentLineCount = 0;
        reader = _reader;
    }
    
    abstract public LineCounts count();

    protected void readNextLine() {
        nextCharIndex = 0;
        try {
            currentLine = reader.readLine();
            if (currentLine != null) {
                lineCount++;
            }
            else {
                atEOF = true;
            }
            // System.out.println( currentLine );
        }
        catch (IOException ioe) {
            System.out.println( "Error reading file. " + ioe.toString());
        }
    }
    
    protected char getChar() {
        char c = ' ';
        if (!atEOF) {
            if (nextCharIndex < currentLine.length()) {
                c = currentLine.charAt( nextCharIndex );
                nextCharIndex++;
            }
            else {
                c = EOL;
                readNextLine();
            }
        }
        else {
            c = EOF;
        }
        return c;
    }

    protected boolean checkIfLineIsStillEmpty( char ch ) {
        if ((ch > ' ') && (ch != '/') && (ch != '*') && (ch != '-') && (ch != '+') && (ch != '#')) {
            if ((ch == '{') && countBracketAsEmpty) {
                // do nothing, ignore a single opening curly bracket
            }
            else {
                return false;
            }
        }
        else {
            // All characters lower than SPACE do not count;
            // slashes, stars, minuses etc. do not count because they are
            // (usually) the comment delimiters or used to create a separating
            // line like: /*************************************
        }
        return true;
    }
}
