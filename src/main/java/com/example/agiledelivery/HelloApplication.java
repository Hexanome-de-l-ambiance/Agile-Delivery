package com.example.agiledelivery;

import com.example.model.Segment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);

        // draw segment example on canvas
        Canvas canvas = new Canvas(640, 480);
        drawSegmentExample(canvas);
        root.getChildren().add(canvas);


        stage.setTitle("Hello world");
        stage.setScene(scene);
        stage.show();
    }

    public static void drawSegmentExample(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(javafx.scene.paint.Color.RED);
        gc.setLineWidth(2);

        Segment segment = new Segment(100, 100, 200, 200);

        gc.strokeLine(segment.getOrigin().getLatitude(), segment.getOrigin().getLongitude(), segment.getDestination().getLatitude(), segment.getDestination().getLongitude());
    }

    public static void main(String[] args) {
        launch();
    }
}