package countlines;
import java.io.*;

class JavaCounter extends Counter {
    public JavaCounter(BufferedReader _reader) {
        super(_reader);
    }

    @Override
    public LineCounts count() {
        char ch = ' ';
        char lastCh = ' ';
        boolean commentLine = false;
        boolean inSlashStarComment = false;
        boolean stillEmptyLine = true;

        readNextLine();
        ch = getChar();
        while (ch != EOF) {

            if (ch == EOL) {
                if (stillEmptyLine) {
                    emptyLineCount++;
                }
                else if (commentLine || inSlashStarComment) {
                    commentLineCount++;
                }
                stillEmptyLine = true;
                commentLine = false;
            }
            else {
                switch (ch) {
                    case '*': 
                        if ((lastCh == '/') && (!commentLine)) {
                            inSlashStarComment = true;
                            if (stillEmptyLine) {
                                commentLine = true;
                            }
                        }
                        break;
                    case '/': 
                        if (lastCh == '*') {
                            inSlashStarComment = false;
                            if (stillEmptyLine) {
                                commentLine = true;
                            }
                        }
                        else if ((lastCh == '/') && stillEmptyLine) {
                            commentLine = true;
                        }
                        break;
                    default:
                        // all other characters are consumed without counting
                        ;
                }
                if (stillEmptyLine) {
                    stillEmptyLine = checkIfLineIsStillEmpty( ch );
                }
            }
            lastCh = ch;
            ch = getChar();
        }
    return new LineCounts(lineCount, emptyLineCount, commentLineCount);
    }
}