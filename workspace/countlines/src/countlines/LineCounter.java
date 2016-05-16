package countlines;
import java.io.*;

public class LineCounter {
    

    private static String[] javaOrCExtensions = new String[] { 
            // list all file types which follow the '//' and '/*'
            // comment convention.
            ".java", ".h", ".cpp", ".cs", ".hpp", ".c", ".m", ".php"
        };

    public static LineCounts countLines( String fileName ) {
        BufferedReader theReader = null;
        try {
            theReader = new BufferedReader( new FileReader( fileName ));
            return count(fileName, theReader);
        } catch (FileNotFoundException fnfe) {
            System.out.println( "File " + fileName + " not found." );
            // NOTE: I'd prefer killing the process here. But whatever.
            return new LineCounts(0, 0, 0);
        } finally {
            try {
                theReader.close();
            }
            catch (IOException ioe) {
                System.out.println("Cannot close " + fileName + " : "
                        + ioe.toString());
            }
        }
    }

    private static LineCounts count(String fileName, BufferedReader theReader) {
        if (isAJavaOrCFile( fileName )) {
            return new JavaCounter(theReader).count();
        }
        else if (fileName.toLowerCase().endsWith( ".sql" )) {
            return new SqlCounter(theReader).count();
        }
        else {
            return new SimpleCounter(theReader).count();
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
}