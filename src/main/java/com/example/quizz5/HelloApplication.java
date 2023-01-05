package com.example.quizz5;

import connection.FlightUtil;
import connection.JDBCUtil;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, RuntimeException, ParseException, SQLException {

        int startSpace = 15;
        FlightUtil.createTable();
        FlightUtil.getMapData();

        Label label1 = new Label("direction");
        label1.setTranslateX(20);
        label1.setTranslateY(startSpace);

        TextField textField1 = new TextField();
        textField1.setTranslateX(80);
        textField1.setTranslateY(startSpace);


        Label label2 = new Label("Date");
        label2.setTranslateX(20);
        label2.setTranslateY(startSpace +30);

        TextField textField2 = new TextField();
        textField2.setTranslateX(80);
        textField2.setTranslateY(startSpace +30);

        Label label3 = new Label("number");
        label3.setTranslateX(20);
        label3.setTranslateY(startSpace +60);

        TextField textField3 = new TextField();
        textField3.setTranslateX(80);
        textField3.setTranslateY(startSpace +60);

        Label label4 = new Label("price");
        label4.setTranslateX(20);
        label4.setTranslateY(startSpace +90);

        TextField textField4 = new TextField();
        textField4.setTranslateX(80);
        textField4.setTranslateY(startSpace +90);

        PieChart pieChart = new PieChart();
        pieChart.setTranslateX(300);
        pieChart.setTranslateY(200);

        pieChart.setData(FlightUtil.getData());




        Button button = new Button("Add new Item");
        button.setTranslateX(100);
        button.setTranslateY(200);


        Group root = new Group(); 
        root.getChildren().add(textField1);
        root.getChildren().add(label1);
        root.getChildren().add(textField2);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(textField3);
        root.getChildren().add(label4);
        root.getChildren().add(textField4);
        root.getChildren().add(pieChart);
        root.getChildren().add(button);


         button.setOnAction(actionEvent -> {
             String direction = textField1.getText();

             int number = Integer.parseInt(textField3.getText());
             float price = Float.parseFloat(textField4.getText());
             LocalDate localDate = LocalDate.parse(textField2.getText());
             System.out.println(direction);
             System.out.println(number);
             System.out.println(localDate);
             System.out.println(price);
             try {
                 FlightUtil.insertItem(new Flight(direction, localDate, number, price));
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }

         });


        Scene scene = new Scene(root, 500, 700);
        stage.setScene(scene);
        stage.setTitle("Flight App!");
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}