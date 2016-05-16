package countlines;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.Vector;


public class FileListTableModel extends AbstractTableModel {
    
    private final int COLUMN_COUNT = 5;
    private final int FILENAME_COLUMN = 0;
    private final String[] columnTitles = new String [] {
            "file name", "net lines", "comment lines", "empty lines", "total lines"
        };
    private int rowCount = 0;
    private boolean totalsCalculated = false;
    
    private Vector fileExtensions = null;
    private Vector allFiles = new Vector();
    private Vector vecNetLineCount = new Vector();
    private Vector vecCommentLineCount = new Vector();
    private Vector vecEmptyLineCount = new Vector();
    private Vector vecTotalLineCount = new Vector();
    private int[] totals = new int[ COLUMN_COUNT ];
    
    
    public FileListTableModel() {
    }
    
    
    private void initVariables( ) {
        allFiles.clear();
        vecNetLineCount.clear();
        vecCommentLineCount.clear();
        vecEmptyLineCount.clear();
        vecTotalLineCount.clear();
        for (int i = 0; i < COLUMN_COUNT; i++) {
            totals[i] = 0;
        }
        totalsCalculated = false;
    }
    
    
    public void setDirectory( String startDirectory, Vector fileExtensions ) {
        this.fileExtensions = fileExtensions;
        if (fileExtensions != null && !fileExtensions.isEmpty()) {
            initVariables();
            collectFiles( startDirectory );
            System.out.println( "Starte dir scan: " + startDirectory );
            adaptGridSize( allFiles.size() );
        }
    }
    
    
    public void countAllFiles() {
        if (!totalsCalculated) {
            for (int i = 0; i < getRowCount(); i++) {
                countFile( i );
            }
            calculateTotal();
            // writeToConsole();
        }
    }
    
    
    private void countFile( int rowIndex ) {
        LineCounter.countLines( (String)getValueAt( rowIndex, FILENAME_COLUMN ));
        vecNetLineCount.add(     new Integer(LineCounter.getNetLineCount()));
        vecCommentLineCount.add( new Integer(LineCounter.getCommentLineCount()));
        vecEmptyLineCount.add(   new Integer(LineCounter.getEmptyLineCount()));
        vecTotalLineCount.add(   new Integer(LineCounter.getTotalLineCount()));
        fireTableDataChanged();
    }
    
    
    private void calculateTotal() {
        for (int i = 0; i < vecNetLineCount.size(); i++) {
            totals[1] += ((Integer)vecNetLineCount.get( i )).intValue();
            totals[2] += ((Integer)vecCommentLineCount.get( i )).intValue();
            totals[3] += ((Integer)vecEmptyLineCount.get( i )).intValue();
            totals[4] += ((Integer)vecTotalLineCount.get( i )).intValue();
        }
        totalsCalculated = true;
        adaptGridSize( rowCount + 2 );
    }
    
    
    private boolean hasRightExtension( String fileName ) {
        for (int i = 0; i < fileExtensions.size(); i++) {
            if (fileName.toLowerCase().endsWith( fileExtensions.elementAt( i ).toString())) {
                return true;
            }
        }
        return  false;
    }
    
    
    private void collectFiles( String directory ) {
        if (!directory.endsWith( "/" ) && !directory.endsWith( "\\" )) {
            directory += "/";
        }
        File currentdir = new File( directory );
        String[] theFiles = currentdir.list();
        if (theFiles != null) {
            for (String aFile: theFiles) {
                String fileName = directory + aFile;
                File f = new File( fileName );
                if (f.isDirectory()) {
                    collectFiles( fileName + '/' );
                }
                else if (f.isFile() && f.canRead()) {
                    if (hasRightExtension( fileName )) {
                        allFiles.add( fileName );
                    }
                }
                else {
                    System.out.println( "?????" + fileName );
                }
            }
        }
    }
    
    
    private void adaptGridSize( int newRowCount ) {
        if (newRowCount == rowCount) {
            fireTableDataChanged();
        }
        else if (newRowCount < rowCount) {
            fireTableRowsDeleted( newRowCount, rowCount-1 );
        }
        else {
            fireTableRowsInserted( rowCount, newRowCount );
        }
        rowCount = newRowCount;
    }
    
    
    @Override
    public int getColumnCount() {
        return columnTitles.length;
    }
    
    
    @Override
    public int getRowCount() {
        return rowCount;
    }
    
    
    @Override
    public String getColumnName(int col) {
        return columnTitles[ col ];
    }
    
    
    @Override
    public Object getValueAt(int row, int col) {
        if (col == FILENAME_COLUMN) {
            if (allFiles != null && row < allFiles.size()) {
                return (String)allFiles.get(row);
            }
            else if (totalsCalculated && row == rowCount - 1) {
                return "Total";
            }
        }
        else if (row < vecNetLineCount.size()) {
            if (col == 1) {
                return vecNetLineCount.get( row );
            }
            else if (col == 2) {
                return vecCommentLineCount.get( row );
            }
            else if (col == 3) {
                return vecEmptyLineCount.get( row );
            }
            else if (col == 4) {
                return vecTotalLineCount.get( row );
            }
        }
        else if (totalsCalculated) {
            if (row == rowCount - 2)
                return "-------";
            else if (row == rowCount - 1)
                return "" + totals[ col ];
        }
        return " ";
    }
    
    
    public void writeToConsole() {
        for (int i = 0; i < getRowCount()-2; i++) {
            System.out.print( (String)getValueAt( i, FILENAME_COLUMN ));
            System.out.println( "\t" + (Integer)getValueAt( i, 1 ) ); // net lines
        }
        System.out.println( "--------------------" );
        System.out.println( "Total  \t" + (String)getValueAt( getRowCount()-1, 1 ));
    }
    
    
    @Override
    public Class getColumnClass(int c) {
        if (getValueAt(0, c) != null)
            return getValueAt(0, c).getClass();
        else {
            String s = "";   // Falls leer gib einfach Typ String zurück
            return s.getClass();
        }
    }
}
