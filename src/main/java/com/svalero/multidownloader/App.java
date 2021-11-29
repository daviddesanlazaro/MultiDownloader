package com.svalero.multidownloader;

import com.svalero.multidownloader.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

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

//        stage.setTitle("JavaFX App");
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setInitialDirectory(new File("src"));
//
//        Button button = new Button("Select Directory");
//        button.setOnAction(e -> {
//            File selectedDirectory = directoryChooser.showDialog(stage);
//
//            System.out.println(selectedDirectory.getAbsolutePath());
//        });
//
//
//        VBox vBox = new VBox(button);
//        //HBox hBox = new HBox(button1, button2);
//        Scene scene = new Scene(vBox, 960, 600);
//        stage.setScene(scene);
//        stage.show();

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

