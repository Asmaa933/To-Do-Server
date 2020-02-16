/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.UserModel;


/**
 * FXML Controller class
 *
 * @author AhmedWagdy
 */
public class ChartController extends Application {
   
    public static int allUser;
    public static int offline=0;
    public static int online;
    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseHandler.startConnection();
        allUser = DatabaseHandler.allUserCount();
        online = DatabaseHandler.getUserCountOnStatus(UserModel.ONLINE_STATUS.ONLINE);
        DatabaseHandler.closeConnection();
        offline = allUser-online;
        //online++;
        
        primaryStage.setTitle("User Status");
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Users");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Numbers");

        StackedBarChart stackedBarChart = new StackedBarChart(xAxis, yAxis);
        stackedBarChart.setCategoryGap(50);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("All User");
        dataSeries1.setNode(yAxis);
        dataSeries1.getData().add(new XYChart.Data("All User", allUser));

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Offline User");
        dataSeries2.setNode(yAxis);
        dataSeries2.getData().add(new XYChart.Data("Offline User",offline ));

        XYChart.Series dataSeries3 = new XYChart.Series();
        dataSeries3.setName("Online User");
        dataSeries3.setNode(yAxis);
        dataSeries3.getData().add(new XYChart.Data("Online User",online));

        stackedBarChart.getData().addAll(dataSeries1, dataSeries2, dataSeries3);

        VBox vbox = new VBox(stackedBarChart);

        Scene scene = new Scene(vbox, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setHeight(400);
        primaryStage.setWidth(400);

        primaryStage.show();
        
       
    }

}
