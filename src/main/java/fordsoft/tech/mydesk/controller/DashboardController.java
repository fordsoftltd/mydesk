package fordsoft.tech.mydesk.controller;

import fordsoft.tech.mydesk.config.Router;
import fordsoft.tech.mydesk.dto.Countries;
import fordsoft.tech.mydesk.model.User;
import fordsoft.tech.mydesk.service.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Odofin Timothy
 */

@Component
@FxmlView("/ui/dashboard.fxml")
public class DashboardController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label userId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker datecreated;

    @FXML
    private TextField city;


    @FXML
    private ComboBox<String> country;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button reset;

    @FXML
    private Button saveUser;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> colUserId;

    @FXML
    private TableColumn<User, String> colFullName;

    @FXML
    private TableColumn<User, LocalDateTime> colDatecreated;

    @FXML
    private TableColumn<User, String> colCity;

    @FXML
    private TableColumn<User, String> colCountry;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, Boolean> colEdit;

    @FXML
    private MenuItem deleteUsers;

    @Autowired
    private Router entrance;

    @Autowired
    private UserService userService;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    private Stage stage;

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        entrance.navigate(LoginController.class, event);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("Full Name", getFullName(), "[a-zA-Z]+") &&
                emptyValidation("City", city.getText() == null) &&
                emptyValidation("Country", country.getSelectionModel().getSelectedItem() == null &&
                        emptyValidation("Email", email.getText() == null))) {

            if (userId.getText() == null || userId.getText() == "") {

                User user = new User();
                user.setFullname(getFullName());
                user.setCountry(country.getSelectionModel().getSelectedItem());
                user.setCity(city.getText());
                user.setDatecreated(LocalDateTime.now());
                user.setEmail(email.getText());
                user.setPassword(password.getText());
                User newUser = userService.save(user);

                saveAlert(newUser);


            } else {
                Optional<User> users = userService.find(Long.parseLong(userId.getText()));
                if (users.isPresent()) {
                    User user = users.get();
                    user.setFullname(getFullName());
                    user.setCountry(country.getSelectionModel().getSelectedItem());
                    user.setCity(city.getText());
                    user.setDatecreated(LocalDateTime.now());
                    user.setEmail(email.getText());
                    user.setPassword(password.getText());
                    User newUser = userService.save(user);
                    updateAlert(user);
                }

            }

            clearFields();
            loadUserDetails();
        }


    }

    @FXML
    private void deleteUsers(ActionEvent event) {
        List<User> users = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) userService.deleteInBatch(users);

        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        city.clear();
        country.getSelectionModel().clearSelection();
        email.clear();
        password.clear();
    }

    private void saveAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFullname() + " has been created ");
        alert.showAndWait();
    }

    private void updateAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFullname() + " has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
        return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFullName() {
        return firstName.getText() + " " + lastName.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = new Stage();
        country.setItems(Countries.obsList());

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadUserDetails();
    }


    /*
     *  Set All userTable column properties
     */
    private void setColumnProperties() {
		/* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        colDatecreated.setCellValueFactory(new PropertyValueFactory<>("datecreated"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory =
            new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                @Override
                public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
                    final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    User user = getTableView().getItems().get(getIndex());
                                    updateUser(user);
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

				private void updateUser(User user) {
                            String fulln[] = user.getFullname().split(" ");
					userId.setText(Long.toString(user.getId()));
					firstName.setText(fulln[0]);
					lastName.setText(fulln[1]);
					city.setText(user.getCity());
                    country.getSelectionModel().select(user.getCountry());
               	email.setText(user.getEmail());
				}
                    };
                    return cell;
                }
            };


    /*
     *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        userList.addAll(userService.findAll());
        userTable.setItems(userList);
    }

    /*
     * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) alert.setContentText("Please Select " + field);
        else {
            if (empty) alert.setContentText("Please Enter " + field);
            else alert.setContentText("Please Enter Valid " + field);
        }
        alert.showAndWait();
    }
}
