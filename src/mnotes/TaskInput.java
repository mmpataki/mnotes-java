package mnotes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public class TaskInput extends javax.swing.JFrame {

    ToDoItem todoItem = null;
    ArrayList<String> imageList = new ArrayList<>();
    int numImages = 0;
    String folder;
    ArrayList<ToDoItem> todoList;
    DefaultListModel<String> taskListModel;

    TaskInput(ToDoItem item, ArrayList<ToDoItem> todoList, DefaultListModel<String> taskListModel) {
        initComponents();

        if (item != null) {
            TitleBox.setEditable(false);
        }
        
        todoItem = item;
        
        if (todoItem != null) {
            TitleBox.setEditable(false);
            TitleBox.setText(todoItem.title);
            PrioritySelector.setSelectedIndex(todoItem.priority);
            ContentBox.setText(Util.readFile(todoItem.contentFile));
            try {
                PreviewPane.setPage("file://" + todoItem.getHTMLPath());
            } catch (IOException ex) {
                Logger.getLogger(NoteEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.todoList = todoList;
        this.taskListModel = taskListModel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        TitleBox = new javax.swing.JTextField();
        Attachment = new javax.swing.JButton();
        SaveTaskButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ContentBox = new javax.swing.JTextArea();
        StartDate = new javax.swing.JSpinner();
        EndDate = new javax.swing.JSpinner();
        PrioritySelector = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Preview = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        PreviewPane = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Task Editor");

        jSplitPane1.setDividerLocation(520);

        TitleBox.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N

        Attachment.setText("Attachment");
        Attachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttachmentActionPerformed(evt);
            }
        });

        SaveTaskButton.setText("Save");
        SaveTaskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveTaskButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        ContentBox.setColumns(20);
        ContentBox.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        ContentBox.setLineWrap(true);
        ContentBox.setRows(5);
        ContentBox.setWrapStyleWord(true);
        jScrollPane1.setViewportView(ContentBox);

        StartDate.setModel(new javax.swing.SpinnerDateModel());

        EndDate.setModel(new javax.swing.SpinnerDateModel());

        PrioritySelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High", "Critical" }));

        jLabel2.setText("Start Date");

        jLabel3.setText("End Date");

        jLabel4.setText("Priority");

        jLabel5.setText("Content");

        jLabel6.setText("Title");

        Preview.setText("Preview");
        Preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Attachment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Preview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SaveTaskButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TitleBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(80, 80, 80)
                                            .addComponent(jLabel3)))
                                    .addGap(93, 93, 93)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(StartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(PrioritySelector, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addGap(0, 6, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Attachment)
                    .addComponent(Preview)
                    .addComponent(SaveTaskButton)
                    .addComponent(CancelButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TitleBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PrioritySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jScrollPane2.setViewportView(jEditorPane1);

        jSplitPane1.setRightComponent(jScrollPane2);

        PreviewPane.setEditable(false);
        jScrollPane3.setViewportView(PreviewPane);

        jSplitPane1.setRightComponent(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void SaveTaskButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveTaskButtonActionPerformed

        if (todoItem == null) {
            todoItem = new ToDoItem();
        }

        todoItem.title = TitleBox.getText();
        todoItem.startDate = (Date) StartDate.getModel().getValue();
        todoItem.endDate = (Date) EndDate.getModel().getValue();
        todoItem.priority = PrioritySelector.getSelectedIndex();

        String nRoot = Util.createDirectory(mNConsts.ToDoPath, todoItem.title) + "/";
        String toWrite = ContentBox.getText();

        for (String string : imageList) {
            try {
                String extension = string.substring(string.lastIndexOf("."));
                Files.copy(Paths.get(string), Paths.get(nRoot + (++numImages) + extension), StandardCopyOption.REPLACE_EXISTING);
                toWrite = toWrite.replace(string, (nRoot + numImages + extension));
            } catch (IOException ex) {
                Logger.getLogger(TaskInput.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            
            Files.deleteIfExists(Paths.get(nRoot+"/Todo.mml"));
            Path p = Files.createFile(Paths.get(nRoot + "/Todo.mml"));
            todoItem.contentFile = p.toAbsolutePath().toString();

            BufferedWriter outputStream = new BufferedWriter(new FileWriter(todoItem.contentFile));
            outputStream.write(toWrite);
            outputStream.flush();

        } catch (IOException ex) {
            Logger.getLogger(TaskInput.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (todoItem.Id == -1) {
            taskListModel.addElement(todoItem.title);
            todoList.add(todoItem);
        }
        todoItem.insertDB();
        setVisible(false);
    }//GEN-LAST:event_SaveTaskButtonActionPerformed

    private void AttachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttachmentActionPerformed

        ArrayList<String> atlist = Util.selectFiles(TaskInput.this);

        atlist.stream().forEach((string) -> {
            ContentBox.insert("@img(src=\"" + string + "\") @\n", ContentBox.getCaretPosition());
            imageList.add(string);
        });
    }//GEN-LAST:event_AttachmentActionPerformed

    private void PreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviewActionPerformed
        try {
            
            Path p = Files.createTempFile("mNotes", ".mml");
            
            String unfHTML = Util.parseNote(ContentBox.getText());
            String HTML = String.format(unfHTML, TitleBox.getText(), "21-Aug-2015", "23-Jan-2010", ContentBox.getText());
            String url = Util.createHTMLfile(HTML, p.toAbsolutePath().toString());

            PreviewPane.setPage("file://" + url);
            
        } catch (IOException ex) {
            Logger.getLogger(NoteEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PreviewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Attachment;
    private javax.swing.JButton CancelButton;
    private javax.swing.JTextArea ContentBox;
    private javax.swing.JSpinner EndDate;
    private javax.swing.JButton Preview;
    private javax.swing.JEditorPane PreviewPane;
    private javax.swing.JComboBox<String> PrioritySelector;
    private javax.swing.JButton SaveTaskButton;
    private javax.swing.JSpinner StartDate;
    private javax.swing.JTextField TitleBox;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}