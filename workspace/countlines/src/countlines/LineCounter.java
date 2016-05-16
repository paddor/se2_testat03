package countlines;
import java.io.*;

public class LineCounter {
    

    private static LineCounts lineCounts = new LineCounts(0, 0, 0);
    private static BufferedReader theReader;
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
            lineCounts = new JavaCounter(theReader).count();
        }
        else if (fileName.toLowerCase().endsWith( ".sql" )) {
            lineCounts = new SqlCounter(theReader).count();
        }
        else {
            lineCounts = new SimpleCounter(theReader).count();
        }

        try {
            theReader.close();
        }
        catch (IOException ioe) {
            System.out.println("Cannot close " + fileName + " : "
                    + ioe.toString());
        }
    }

    private static boolean isAJavaOrCFile( String fileName ) {
        for (String ext: javaOrCExtensions) {
            if (fileName.toLowerCase().endsWith( ext.toLowerCase() )) {
                return true;
            }
        }
        return  false;
    }
    
    public static int getNetLineCount() {
        return lineCounts.getNetLineCount();
    }
    
    public static int getTotalLineCount() {
        return lineCounts.getTotalLineCount();
    }

    public static int getEmptyLineCount() {
        return lineCounts.getEmptyLineCount();
    }
    
    public static int getCommentLineCount() {
        return lineCounts.getCommentLineCount();
    }
}