package controller;

import BO.BOFactory;
import BO.Custom.LoginBO;
import ValidationUtil.Validation;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ChangePasswordFormController implements Initializable {
    public JFXButton btnReset;
    public TextField txtUsername;
    public Label lblUsername;
    public PasswordField txtPassword;
    public Label lblPassword;
    public PasswordField txtConfirmPassword;
    public Label lblConfirmPassword;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern compile_Password = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");

    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReset.setDisable(true);
        setValidation();
    }

    private void setValidation() {
        map.put(txtPassword,compile_Password);
    }

    public void user_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(map, btnReset);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }
    }

    public void resetPasswordOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").show();
        }else {
            if (txtConfirmPassword.getText().equals(txtPassword.getText())){
                txtConfirmPassword.getParent().setStyle("-fx-border-color: limegreen");
                lblConfirmPassword.setStyle("-fx-border-color: limegreen");

                if (loginBO.checkUsername(txtUsername.getText())) {
                    loginBO.insertNewPassword(txtUsername.getText(), txtPassword.getText());
                    new Alert(Alert.AlertType.CONFIRMATION, "Successful Reset").show();
                } else {
                    lblUsername.setStyle("-fx-text-fill: crimson");
                    txtUsername.setStyle("-fx-text-fill: crimson");
                    new Alert(Alert.AlertType.WARNING, "Please Enter Correct Username.").show();
                }

            }else {
                txtConfirmPassword.getParent().setStyle("-fx-border-color: crimson");
                lblConfirmPassword.setStyle("-fx-border-color: crimson");
                new Alert(Alert.AlertType.WARNING, "Check Your Password.").show();
            }
        }
    }

}
