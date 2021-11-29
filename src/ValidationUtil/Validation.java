package ValidationUtil;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validation {

    public static Object Validations(LinkedHashMap<TextField, Pattern> map, JFXButton btn){
        btn.setDisable(true);

        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                if (!key.getText().isEmpty()){
                    key.getParent().setStyle("-fx-border-color: crimson");
                }
                return key;
            }
            key.getParent().setStyle("-fx-border-color: limegreen");
        }
        btn.setDisable(false);
        return true;
    }

    /*public static Object ValidationsForEdit(LinkedHashMap<TextField, Pattern> map, JFXButton btn){

        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                if (!key.getText().isEmpty()){
                    key.setStyle("-fx-border-color: crimson");
                }
                return key;
            }
            key.setStyle("-fx-border-color: limegreen");
        }
        return true;
    }*/
}
