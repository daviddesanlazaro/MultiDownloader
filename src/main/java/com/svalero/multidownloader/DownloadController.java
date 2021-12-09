package com.svalero.multidownloader;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadController implements Initializable {

    public TextField tfUrl;
    public TextField delay;
    public Label lbStatus;
    public Label lbDelay;
    public ProgressBar pbProgress;
    private String urlText;
    private DownloadTask downloadTask;
    private File defaultFile;
    private File file;
    private AppController controller;

    private static final Logger logger = LogManager.getLogger(DownloadController.class);

    public DownloadController(String urlText, File defaultFile) {
        logger.info("Creado: " + urlText);
        this.urlText = urlText;
        this.defaultFile = defaultFile;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUrl.setText(urlText);
    }

    @FXML
    public void start(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(defaultFile);
            file = fileChooser.showSaveDialog(tfUrl.getScene().getWindow());
            if (file == null)
                return;

            try {
                int delayTime = delay();
                Thread.sleep(delayTime * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            downloadTask = new DownloadTask(urlText, file);

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(downloadTask.progressProperty());

            downloadTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                System.out.println(observableValue.toString());
                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha terminado");
                    alert.show();
                }
            });
            downloadTask.messageProperty().addListener((observableValue, oldValue, newValue) -> lbStatus.setText(newValue));
            new Thread(downloadTask).start();

        } catch (MalformedURLException murle) {
            murle.printStackTrace();
            logger.error("URL mal formada", murle.fillInStackTrace());
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        stop();
    }

    public void stop() {
        if (downloadTask != null)
            downloadTask.cancel();
    }

    public void delete() {
        if (file != null) {
            file.delete();
        }
    }

    public String getUrlText() {
        return urlText;
    }

    public int delay() {
        if (delay.getText().equals("")) {
            return 0;
        } else {
            int delayTime = Integer.parseInt(delay.getText());
            return delayTime;
        }
    }
}
