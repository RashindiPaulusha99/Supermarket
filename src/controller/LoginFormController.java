package controller;

import BO.BOFactory;
import BO.Custom.LoginBO;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import DTO.LoginDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUsername;
    public TextField txtPassword;
    public AnchorPane loginContext;
    public JFXButton btnLogin;
    public Label errorMassageContext;

    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void openSelectItemForm(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        LoginDTO login =  loginBO.returnLoginData(txtUsername.getText(), txtPassword.getText());

        if (loginBO.checkCorrectUsernameAndPassword(txtUsername.getText(), txtPassword.getText())){

            if (login.getRole().equalsIgnoreCase("Admin")){

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ManageItemForm.fxml"));
                Parent load = loader.load();
                ManageItemFormController controller = loader.<ManageItemFormController>getController();
                controller.setDetails(login.getRole(),login.getUsername());
                Stage window = (Stage) loginContext.getScene().getWindow();
                window.setScene(new Scene(load));

            }else if (login.getRole().equalsIgnoreCase("User")) {

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ManageCustomerForm.fxml"));
                Parent load = loader.load();
                ManageCustomerFormController controller = loader.<ManageCustomerFormController>getController();
                controller.setData(login.getRole(),login.getUsername());
                Stage window = (Stage) loginContext.getScene().getWindow();
                window.setScene(new Scene(load));
            }

        }else {
            errorMassageContext.setText("Please Enter Correct Username Or Password");
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void openChangePasswordFormOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChangePasswordForm.fxml"))));
        stage.show();
    }

    public void openRegisterFormOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"))));
        stage.show();
    }
}
