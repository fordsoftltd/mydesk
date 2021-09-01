package fordsoft.tech.mydesk.controller;

import fordsoft.tech.mydesk.config.Router;
import fordsoft.tech.mydesk.dto.Countries;
import fordsoft.tech.mydesk.model.User;
import fordsoft.tech.mydesk.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
@FxmlView("/ui/signup.fxml")
public class SignupController implements Initializable {
    @Autowired
    UserService userService;
    @Autowired
    Router router;


    // the name of the nodes are coming from fxml fx:id -> it is identity associated to component in fxml to build a controller
    @FXML
    private TextField txtFullName;
    @FXML
    private Label lblNameError;

    @FXML
    private TextField txtMail;
    @FXML
    private Label lblMailError;

    @FXML
    private TextField txtPass;
    @FXML
    private Label lblPassError;

    @FXML
    private ComboBox boxCountry;
    @FXML
    private Label lblCountryError;

    @FXML
    private TextField txtCity;
    @FXML
    private Label lblCityError;

    @FXML
    private Label lblError;     // General Error Label

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        String fullName;
        String mail;
        String pass;
        String country;
        String city;

        // Check if any of the text field is empty
        ArrayList<TextField> txtList = new ArrayList<>();
        txtList.add(txtFullName);
        txtList.add(txtMail);
        txtList.add(txtPass);
        txtList.add(txtCity);
        // iterate the textField nodes
        for (TextField nodes : txtList) {
            if (nodes.getText().isEmpty()) {
                lblError.setText("Please complete  all the form");
            }

        }
        if (boxCountry.getSelectionModel().isEmpty()) {     // check if a country is selected
            lblCountryError.setText("Select a country from the list");
        } else if (isValidEmailAddress(txtMail.getText()) == false) {     // check if the mail address is a valid address
            lblMailError.setText("Enter a valid mail");
            lblError.setText("");
        } else {
            lblCountryError.setText("");
            lblMailError.setText("");

            // store the user's inputs
            fullName = txtFullName.getText();
            mail = txtMail.getText();
            pass = txtPass.getText();
            country = boxCountry.getSelectionModel().getSelectedItem().toString();
            city = txtCity.getText();

            User user = new User();
            user.setCity(city);
            user.setCountry(country);
            user.setEmail(mail);
            user.setFullname(fullName);
            user.setPassword(pass);
            user.setDatecreated(LocalDateTime.now());
            userService.save(user);
            lblError.setText("Registaration is succesful");
        }

    }


    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            //  InternetAddress emailAddr = new InternetAddress(email);
            //emailAddr.validate();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }
    @FXML
    void onLogin(ActionEvent event) {
router.navigate(LoginController.class, event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // display the list of countries
        boxCountry.setItems(Countries.obsList());

    }
}
