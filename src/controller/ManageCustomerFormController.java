package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageCustomerFormController implements Initializable {

    public AnchorPane manageCustomerContext;
    public Label lblUsername;
    public Label lblRole;
    public JFXButton btnSave;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnView;
    public JFXButton btnPlaceOrder;
    public JFXButton btnSearchOrder;
    public Label lblDate;
    public Label lblTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
    }

    public void setData(String role , String username){
        lblRole.setText(role);
        lblUsername.setText(username);
    }

    private void loadDateAndTime() {
        /*load date*/
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        /*load time*/
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void openSaveCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        btnSave.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/SaveCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openSearchCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        btnSearch.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSave.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/SearchCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdateCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        btnUpdate.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnSave.setStyle("");
        btnView.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/UpdateCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openDeleteCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        btnDelete.setStyle("-fx-background-color: lightcoral");
        btnSave.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/DeleteCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openDisplayAllCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        btnView.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnSave.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/DisplayAllCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openPlaceOrderForm(ActionEvent actionEvent) throws IOException {
        btnPlaceOrder.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnSave.setStyle("");
        btnSearchOrder.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void openSearchOrderForm(ActionEvent actionEvent) throws IOException {
        btnSearchOrder.setStyle("-fx-background-color: lightcoral");
        btnDelete.setStyle("");
        btnSearch.setStyle("");
        btnUpdate.setStyle("");
        btnView.setStyle("");
        btnPlaceOrder.setStyle("");
        btnSave.setStyle("");
        Parent load = FXMLLoader.load(getClass().getResource("../view/SearchOrderForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void homePageOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) manageCustomerContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

}
