package com.svalero.multidownloader;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

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
    private ExecutorService exec;
    private Timeline timeline = new Timeline();

    private static final Logger logger = LogManager.getLogger(DownloadController.class);

    public DownloadController(String urlText, File defaultFile, ExecutorService exec) {
        logger.info("Creado: " + urlText);
        this.urlText = urlText;
        this.defaultFile = defaultFile;
        this.exec = exec;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUrl.setText(urlText);
    }

    public void start() {
        try {
            downloadTask = new DownloadTask(urlText, file);

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
            exec.execute(downloadTask);
        } catch (MalformedURLException murle) {
            murle.printStackTrace();
        }
    }

    @FXML
    public void start(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(defaultFile);
        file = fileChooser.showSaveDialog(tfUrl.getScene().getWindow());
        if (file == null)
            return;

        int delayTime = delay();
        if (delayTime == 0) {
            start();
        } else {
            timeline = new Timeline (
                    new KeyFrame(
                            Duration.seconds(delayTime), actionEvent -> start()
                    )
            );
            timeline.play();
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        stop();
    }

    public void stop() {
        if (downloadTask != null) {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
            }
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0);
            logger.info("Cancelado: " + urlText);
            downloadTask.cancel();
        }
    }

    public void delete() {
        if (file != null) {
            logger.info("Eliminado: " + urlText);
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
            try {
                if (Integer.parseInt(delay.getText()) > 0) {
                    return Integer.parseInt(delay.getText());
                } else {
                    return 0;
                }
            } catch (NumberFormatException nfe) {
                return 0;
            }
        }
    }
}
