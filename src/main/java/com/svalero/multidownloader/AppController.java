package com.svalero.multidownloader;

import com.svalero.multidownloader.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AppController {

    public TextField tfUrl;
    public Button btDownload;
    public TabPane tpDownloads;

    private Map<String, DownloadController> allDownloads;

    @FXML
    private ScrollPane sp;
    public File file;

    public AppController() {
        allDownloads = new HashMap<>();
    }

    @FXML
    private void changeDirectory(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        Stage stage = (Stage) sp.getScene().getWindow();
        file = dirChooser.showDialog(stage);
    }

    @FXML
    public void launchDownload(ActionEvent event) {
        String urlText = tfUrl.getText();
        tfUrl.clear();
        tfUrl.requestFocus();

        launchDownload(urlText, file);
    }

    private void launchDownload(String url, File file) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI("download.fxml"));

            DownloadController downloadController = new DownloadController(url, file);
            loader.setController(downloadController);
            VBox downloadBox = loader.load();

            String filename = url.substring(url.lastIndexOf("/") + 1);
            tpDownloads.getTabs().add(new Tab(filename, downloadBox));

            allDownloads.put(url, downloadController);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    public void stopAllDownloads() {
        for (DownloadController downloadController : allDownloads.values())
            downloadController.stop();
    }

    @FXML
    public void readDLC() {
        // Pedir el fichero al usuario (FileChooser)

        // Leo el fichero y cargo cada linea en un List (clase Files)

        // Para cada linea, llamar al método launchDownload
    }

    @FXML
    public void viewLog(ActionEvent event) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        File log = new File("C:/Users/David/IdeaProjects/multidescarga/multidescargas.log");
        desktop.open(log);
    }
}
