package com.svalero.multidownloader;

import com.svalero.multidownloader.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("multidownload.fxml"));
        loader.setController(new AppController());
        ScrollPane vbox = loader.load();

        Scene scene2 = new Scene(vbox);
        stage.setScene(scene2);
        stage.setTitle("Downloader");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}

