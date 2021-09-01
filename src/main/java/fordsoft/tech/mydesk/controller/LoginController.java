package fordsoft.tech.mydesk.controller;


import fordsoft.tech.mydesk.config.Router;
import fordsoft.tech.mydesk.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Odofin Timothy
 */

@Component
@FxmlView("/ui/Login.fxml")
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;

    @Autowired
    private UserService userService;


    @Autowired
    private Router router;

    @FXML
    void onSignup(ActionEvent event) {

        router.navigate(SignupController.class, event);
    }

    @FXML
    private void login(ActionEvent event) throws IOException {
        if (userService.authenticate(getUsername(), getPassword())) {
            router.navigate(DashboardController.class, event);

        } else {
            lblLogin.setText("Login Failed.");
        }
    }

    public String getPassword() {
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    private Stage stage;

    public void initialize(URL location, ResourceBundle resources) {
        this.stage = new Stage();
    }


}
