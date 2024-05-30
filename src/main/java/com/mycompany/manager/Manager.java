/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.manager;

/**
 *Codigo proporcionado por Ing. Melvin Cali
 * 
 * Se realizaron las modificaciones para poder realizar la copia de archivos de una computadora a otra conectadas a una misma red
 * 
 * @author Jean, Bryan, Jonathan, Roberto
 */
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;

public class Manager extends javax.swing.JFrame {

    public Manager() {
        initComponents();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            jLabelIP.setText("IP Local: " + ip);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jButtonOpen = new javax.swing.JButton();
        jButtonSelectDestination = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFiles = new javax.swing.JTable();
        jLabelIP = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonOpen.setText("Agregar Archivo");
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });

        jButtonSelectDestination.setText("Seleccionar Destino");
        jButtonSelectDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectDestinationActionPerformed(evt);
            }
        });

        jButtonClear.setText("Limpiar");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jTableFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Origen", "Destino" }
        ) {
            Class[] types = new Class [] { java.lang.String.class, java.lang.String.class };
            boolean[] canEdit = new boolean [] { false, false };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableFiles);

        jLabelIP.setText("IP Local: 192.168.1.1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButtonOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSelectDestination)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jLabelIP)
                .addGap(80, 80, 80)
                .addComponent(jButtonClear)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOpen)
                    .addComponent(jButtonSelectDestination)
                    .addComponent(jLabelIP)
                    .addComponent(jButtonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            sourceFile = jFileChooser.getSelectedFile();
        }
    }

    private void jButtonSelectDestinationActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            destinationFile = jFileChooser.getSelectedFile();
            try {
                File target = new File(destinationFile.getAbsolutePath() + "/" + sourceFile.getName());
                FileUtils.copyFile(sourceFile, target);
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTableFiles.getModel();
                defaultTableModel.addRow(new Object[]{sourceFile.getAbsolutePath(), target.getAbsolutePath()});
            } catch (IOException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTableFiles.getModel();
        if (defaultTableModel.getRowCount() > 0) {
            for (int i = defaultTableModel.getRowCount() - 1; i > -1; i--) {
                defaultTableModel.removeRow(i);
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Manager().setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonOpen;
    private javax.swing.JButton jButtonSelectDestination;
    private javax.swing.JLabel jLabelIP;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFiles;
    private File sourceFile;
    private File destinationFile;
}
