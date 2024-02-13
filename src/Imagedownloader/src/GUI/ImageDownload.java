/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


/**
 *
 * @author Bibhakta lamsal
 */
public class ImageDownload extends javax.swing.JFrame {
    private static final String DESTINATION_FOLDER = System.getProperty("user.home") + "/Downloads/";
    private final ExecutorService executorService;
    private final Semaphore downloadSemaphore;
    private volatile boolean paused;
    private DownloadTask currentTask;


    /**
     * Creates new form ImageDownload
     */
  public ImageDownload() {
        initComponents();

        // Add ActionListener to the "Download" button
        executorService = Executors.newFixedThreadPool(10);
        downloadSemaphore = new Semaphore(10); // Limit concurrent downloads
        paused = false;

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executorService.submit(() -> downloadImage());
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseDownload();
            }
        });

        resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeDownload();
            }
        });

         cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelDownload();
            }
        });
    }

    

  private class DownloadTask implements Runnable {
        private final String urlText;
        private final JProgressBar progressBar;
        

        public DownloadTask(String urlText, JProgressBar progressBar) {
            this.urlText = urlText;
            this.progressBar = progressBar;
        }

        private volatile boolean paused;
         private volatile boolean canceled;

        public void pause() {
            paused = true;
        }

        public void resume() {
            synchronized (this) {
                paused = false;
                notifyAll(); // Notify waiting threads (if any) that it's safe to resume
            }
        }
        
          public void cancel() {
            canceled = true;
        }

        @Override
        public void run() {
            try {
                URI uri = new URI(urlText);

                downloadSemaphore.acquire();

                synchronized (this) {
                    while (paused) {
                        wait();
                    }
                }

                HttpClient httpClient = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

                HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

                String fileName = uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1);
                String timestamp = String.valueOf(System.currentTimeMillis());
                fileName = timestamp + "_" + fileName;

                Path destination = Path.of(DESTINATION_FOLDER, fileName);

                long contentLength = response.headers().firstValueAsLong("Content-Length").orElse(-1);
                long totalBytesRead = 0;
                byte[] buffer = new byte[1024];
                int bytesRead;

                try (InputStream inputStream = response.body()) {
                    try (OutputStream outputStream = Files.newOutputStream(destination)) {
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            synchronized (this) {
                                while (paused) {
                                    wait();
                                }
                            }

                            if (canceled) {
                                break;
                            }

                            totalBytesRead += bytesRead;

                            int progress = (int) ((double) totalBytesRead / contentLength * 100);
                            SwingUtilities.invokeLater(() -> {
                                progressBar.setValue(progress);
                                percentage.setText("Percentage: " + progress + "%");
                            });

                            outputStream.write(buffer, 0, bytesRead);
                            Thread.sleep(1000);
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ImageDownload.this, "Error downloading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(ImageDownload.this, "Image downloaded successfully!\nSaved to: " + destination.toString());
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                JOptionPane.showMessageDialog(ImageDownload.this, "Error downloading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                progressBar.setValue(0);
                textField1.setText("");
                percentage.setText("");
                downloadSemaphore.release();
                currentTask = null;
            }
        }
    }

    private void downloadImage() {
        String urlText = textField1.getText().trim();
        JProgressBar progressBar = jProgressBar1;
        currentTask = new DownloadTask(urlText, progressBar);
        executorService.submit(currentTask);
    }

    private void pauseDownload() {
        if (currentTask != null) {
            currentTask.pause();
            JOptionPane.showMessageDialog(this, "Downloads Paused");
        }
    }

    private void resumeDownload() {
        if (currentTask != null) {
            currentTask.resume();
            JOptionPane.showMessageDialog(this, "Downloads Resumed");
        }
    }

    private void cancelDownload() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
            JOptionPane.showMessageDialog(this, "Download Canceled");
        }
        textField1.setText("");
        jProgressBar1.setValue(0);
        percentage.setText("");
    }



    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label2 = new java.awt.Label();
        label1 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        button1 = new java.awt.Button();
        pause = new java.awt.Button();
        cancel = new java.awt.Button();
        resume = new java.awt.Button();
        jProgressBar1 = new javax.swing.JProgressBar();
        percentage = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        label2.setText("label2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        setMinimumSize(new java.awt.Dimension(600, 350));
        setUndecorated(true);

        label1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label1.setText("Enter URL:");

        button1.setBackground(new java.awt.Color(51, 153, 255));
        button1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setLabel("Download");

        pause.setBackground(new java.awt.Color(51, 153, 255));
        pause.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        pause.setForeground(new java.awt.Color(255, 255, 255));
        pause.setLabel("Pause");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });

        cancel.setBackground(new java.awt.Color(51, 153, 255));
        cancel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancel.setForeground(new java.awt.Color(255, 255, 255));
        cancel.setLabel("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        resume.setBackground(new java.awt.Color(51, 153, 255));
        resume.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        resume.setForeground(new java.awt.Color(255, 255, 255));
        resume.setLabel("Resume");
        resume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeActionPerformed(evt);
            }
        });

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 12)); // NOI18N

        percentage.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        percentage.setText("Percentage");

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Welcome To Image Downloader (1).png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(pause, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(percentage)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(resume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(percentage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pause, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
       
    
    }//GEN-LAST:event_pauseActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
         if (currentTask != null) {
        currentTask.cancel(); // Cancel the current download task
        currentTask = null; // Reset the current task
        JOptionPane.showMessageDialog(this, "Download Canceled");
    }
    textField1.setText(""); // Clear the input field
    jProgressBar1.setValue(0); // Reset progress bar
        
    }//GEN-LAST:event_cancelActionPerformed

    private void resumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resumeActionPerformed
        // TODO add your handling code 
    }//GEN-LAST:event_resumeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
      public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImageDownload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new ImageDownload().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button cancel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Button pause;
    private javax.swing.JLabel percentage;
    private java.awt.Button resume;
    private java.awt.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
