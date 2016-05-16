package countlines;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;


public class MainWindow extends javax.swing.JFrame {

    private final JPanel topPanel;
    private final JButton countFilesButton;
    private final JButton selectDirButton;
    private final JTextField dirTextField;
    private final JLabel noOfFilesLabel;
    private final JTable tableGrid;

    private final int FILENAME_WIDTH = 500;
    private final int WINDOW_WIDTH = 900;
    private final int WINDOW_HEIGHT = 400;
    private final int DIR_FIELD_WIDTH = 30;
    private final String NO_OF_FILES_TEXT = "         Number of files: ";

    private final FileListTableModel tableModel = new FileListTableModel();
    private final Vector fileExtensions = new Vector();

    public MainWindow() {
        topPanel = new JPanel();
        selectDirButton = new JButton();
        dirTextField = new JTextField();
        countFilesButton = new JButton();
        tableGrid = new JTable();
        noOfFilesLabel = new JLabel(NO_OF_FILES_TEXT + "0           ");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        selectDirButton.setText("Select Directory");
        selectDirButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDirButtonActionPerformed(evt);
            }
        });
        topPanel.add(selectDirButton);

        dirTextField.setColumns(DIR_FIELD_WIDTH);
        dirTextField.setText("C:\\");
        topPanel.add(dirTextField);

        topPanel.add(noOfFilesLabel);

        countFilesButton.setText("Count Files");
        countFilesButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countFilesButtonActionPerformed(evt);
            }
        });
        topPanel.add(countFilesButton);

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        tableGrid.setModel(tableModel);
        tableGrid.setGridColor( Color.LIGHT_GRAY );
        tableGrid.getColumnModel().getColumn(0).setPreferredWidth(FILENAME_WIDTH);

        JScrollPane scrollPane = new JScrollPane(tableGrid);
        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
        pack();
    }

    public void setFileExtensions(final String extensions) {
        fileExtensions.clear();
        if (extensions != null) {
            String title = "Count files with extensions: ";
            for (String ext : extensions.split(",")) {
                fileExtensions.add(ext.trim());
                title = title + " " + ext.trim();
            }
            this.setTitle(title);
        }
    }

    private void countFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {
        tableModel.countAllFiles();
    }

    private void selectDirButtonActionPerformed(java.awt.event.ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File(dirTextField.getText()));
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                dirTextField.setText(file.getCanonicalPath());
                tableModel.setDirectory(dirTextField.getText(), fileExtensions);
                noOfFilesLabel.setText(NO_OF_FILES_TEXT + tableModel.getRowCount() + "  ");
                tableModel.fireTableDataChanged();
            } catch (IOException ioe) {
                System.out.println("Error converting path" + ioe.getMessage());
            }
        }
    }
}
