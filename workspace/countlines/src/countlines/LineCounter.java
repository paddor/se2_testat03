package countlines;

import java.io.*;


public class LineCounter {
    
    private static final char EOL = '\n';
    private static final char EOF = '\0';

    private static int nextCharIndex = 0;
    private static String currentLine = null;
    private static int lineCount = 0;
    private static int emptyLineCount = 0;
    private static int commentLineCount = 0;
    private static BufferedReader theReader;
    private static boolean countBracketAsEmpty = true;
    private static boolean atEOF = false;
    private static String[] javaOrCExtensions = new String[] { 
            // list all file types which follow the '//' and '/*'
            // comment convention.
            ".java", ".h", ".cpp", ".cs", ".hpp", ".c", ".m", ".php"
        };
    
    
    public static void countLines( String fileName ) {
        try {
            theReader = new BufferedReader( new FileReader( fileName ));
        }
        catch (FileNotFoundException fnfe) {
            System.out.println( "File " + fileName + " not found." );
        }
        
        if (isAJavaOrCFile( fileName )) {
            doJavaLineCount();
        }
        else if (fileName.toLowerCase().endsWith( ".sql" )) {
            doSQLLineCount();
        }
        else {
            doSimpleLineCount();
        }

        try {
            theReader.close();
        }
        catch (IOException ioe) {
            System.out.println("Cannot close " + fileName + " : "
                    + ioe.toString());
        }
    }
    
    private static void readNextLine() {
        nextCharIndex = 0;
        try {
            currentLine = theReader.readLine();
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
    
    private static char getChar() {
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

    private static void doJavaLineCount() {
        char ch = ' ';
        char lastCh = ' ';
        boolean commentLine = false;
        boolean inSlashStarComment = false;
        boolean stillEmptyLine = true;
        
        initCountingVariables();
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
    }

    private static void doSQLLineCount() {
        char ch = ' ';
        char lastCh = ' ';
        boolean commentLine = false;
        boolean inSlashStarComment = false;
        boolean stillEmptyLine = true;
        
        initCountingVariables();
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
                        break;
                    case '-':
                        if ((lastCh == '-') && stillEmptyLine) {
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
    }
    
    private static void doSimpleLineCount() {
        char ch = ' ';
        boolean stillEmptyLine = true;
        
        initCountingVariables();
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
    }
    
    private static boolean checkIfLineIsStillEmpty( char ch ) {
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
        return  true;
    }
    
    private static boolean isAJavaOrCFile( String fileName ) {
        for (String ext: javaOrCExtensions) {
            if (fileName.toLowerCase().endsWith( ext.toLowerCase() )) {
                return true;
            }
        }
        return  false;
    }
    
    private static void initCountingVariables() {
        lineCount = 0;
        emptyLineCount = 0;
        commentLineCount = 0;
        atEOF = false;
    }
    
    public static int getNetLineCount() {
        return lineCount - emptyLineCount - commentLineCount;
    }
    
    public static int getTotalLineCount() {
        return lineCount;
    }

    public static int getEmptyLineCount() {
        return emptyLineCount;
    }
    
    public static int getCommentLineCount() {
        return commentLineCount;
    }
}
