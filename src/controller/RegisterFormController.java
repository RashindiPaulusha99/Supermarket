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
import DTO.LoginDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterFormController implements Initializable {

    public JFXButton btnRegister;
    public TextField txtUserId;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public Label lblUserId;
    public Label lblUsername;
    public Label lblPassword;
    public Label lblConfirmPassword;
    public Label lblError;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern compile_UserId = Pattern.compile("^[U-u]{1}(0){2}[-][0-9]{4}$");
    Pattern compile_UserName = Pattern.compile("^[A-z ]{3,20}$");
    Pattern compile_Password = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");
    Pattern compile_ConfirmPassword = Pattern.compile("^[A-z|!|@|#|$|%|&|0-9]{6,10}$");

    private LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnRegister.setDisable(true);
        setValidation();
    }

    private void setValidation() {
        map.put(txtUserId,compile_UserId);
        map.put(txtUsername,compile_UserName);
        map.put(txtPassword,compile_Password);
        map.put(txtConfirmPassword,compile_ConfirmPassword);
    }

    public void user_KeyReleased(KeyEvent keyEvent) {
        Object response = Validation.Validations(map, btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER){
            if (response instanceof TextField){
                TextField error = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
            }
        }

    }

    public void registerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String role = null;

        if (txtUserId.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"All Fields Are Required.").show();
        }else {
            if (txtConfirmPassword.getText().equals(txtPassword.getText())){
                txtConfirmPassword.getParent().setStyle("-fx-border-color: limegreen");
                lblConfirmPassword.setStyle("-fx-border-color: limegreen");

                if (loginBO.searchId(txtUserId.getText())){
                    new Alert(Alert.AlertType.WARNING,"UserId Already Exists.").show();
                }else {
                    if (txtUsername.getText().equals("Manager")){
                        role = "Admin";
                    }else if (txtUsername.getText().equals("Cashier")){
                        role = "User";
                    }

                    LoginDTO login = new LoginDTO(
                            txtUserId.getText(),
                            role,
                            txtUsername.getText(),
                            txtPassword.getText()
                    );

                    if (txtUsername.getText().equals("Manager") || txtUsername.getText().equals("Cashier")){
                        if (loginBO.saveLoginData(login)) {
                            new Alert(Alert.AlertType.CONFIRMATION,"Registration Successful.").show();
                        }else {
                            new Alert(Alert.AlertType.WARNING,"Try Again.").show();
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Invalid Username.").show();
                    }
                }

            }else {
                txtConfirmPassword.getParent().setStyle("-fx-border-color: crimson");
                lblConfirmPassword.setStyle("-fx-border-color: crimson");
                lblError.setText("Invalid Password.");
            }
        }
    }

}
