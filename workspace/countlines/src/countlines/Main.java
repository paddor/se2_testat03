package countlines;

public class Main {
    
    public Main() {
    }
    
    public static void main(String[] args) {
        final String fileExtensions;
        if (args.length > 0) {
            fileExtensions= args[0];
        }
        else {
            fileExtensions = ".java, .c,.h,.cpp,  .cs, .hpp, .pas,.sql, .php"; 
                             // default string (contains spaces and tabs for testing)
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow aWindow = new MainWindow();
                aWindow.setFileExtensions( fileExtensions );
                aWindow.setVisible(true);
            }
        });
    }
    
}
