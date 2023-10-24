package com.example.agiledelivery;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.xml.XMLOpener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        StackPane root = new StackPane();
//        Scene scene = new Scene(root, 640, 480);
//
//        // draw segment example on canvas
//        Canvas canvas = new Canvas(640, 480);
//        drawSegmentExample(canvas);
//        root.getChildren().add(canvas);
//
//
//        stage.setTitle("Hello world");
//        stage.setScene(scene);
//        stage.show();
//    }

//    public static void drawSegmentExample(Canvas canvas)
//    {
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        gc.setStroke(javafx.scene.paint.Color.RED);
//        gc.setLineWidth(2);
//
//        Intersection i1 = new Intersection(1L, 0, 0);
//        Intersection i2 = new Intersection(2L, 100, 100);
//        Segment segment = new Segment(i1, i2, 100, "rue de la paix");
//
//        gc.strokeLine(segment.getOrigin().getLatitude(), segment.getOrigin().getLongitude(), segment.getDestination().getLatitude(), segment.getDestination().getLongitude());
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();

        Carte carte = new Carte();  // Assuming you have a Carte class that can store intersections and segments.

        try{
            XMLOpener.getInstance().readFile(primaryStage, carte);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = Double.MIN_VALUE;

        // 1. Trouver les valeurs min et max
        for (Intersection intersection : carte.getListeIntersections().values()) {
            minLat = Math.min(minLat, intersection.getLatitude());
            maxLat = Math.max(maxLat, intersection.getLatitude());
            minLon = Math.min(minLon, intersection.getLongitude());
            maxLon = Math.max(maxLon, intersection.getLongitude());
        }

        // 2. Determiner le centre et le scale
        double midLat = (minLat + maxLat) / 2;
        double midLon = (minLon + maxLon) / 2;

        double rangeLat = maxLat - minLat;
        double rangeLon = maxLon - minLon;

        double paneWidth = 1200;
        double paneHeight = 900;

        double scaleX = paneWidth / rangeLon;
        double scaleY = paneHeight / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        // 3. Afficher les intersections
        for (Intersection intersection : carte.getListeIntersections().values()) {
            double adjustedX = (intersection.getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + paneHeight / 2;  // negate Y to adjust for screen coordinates

            Circle circle = new Circle(adjustedX, adjustedY, 3);
            pane.getChildren().add(circle);
        }

        // 4. Afficher les segments

        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + paneHeight / 2;  // negate Y to adjust for screen coordinates
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + paneHeight / 2;  // negate Y to adjust for screen coordinates

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            pane.getChildren().add(line);
        }

        pane.setPrefSize(paneWidth, paneHeight);

        // Centrer le pane
        Pane root = new Pane();
        root.getChildren().add(pane);

        pane.layoutXProperty().bind(root.widthProperty().subtract(paneWidth).divide(2));
        pane.layoutYProperty().bind(root.heightProperty().subtract(paneHeight).divide(2));

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


        public static void main(String[] args) {
        launch(args);

    }
}