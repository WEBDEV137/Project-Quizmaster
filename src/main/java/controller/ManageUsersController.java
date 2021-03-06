package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Group;
import model.User;
import view.Main;

import java.util.Collections;
import java.util.List;

public class ManageUsersController {
    private UserDAO udao;
    private DBAccess db;

    @FXML
    ListView<User> userList;

    public ManageUsersController() {
        super();
        this.db = Main.getDBaccess();
        this.udao = new UserDAO(db);
    }

    public void setup() {
        this.udao = new UserDAO(Main.getDBaccess());
        List<User> allUsers = udao.getAll();
        Collections.sort(allUsers);
        for (User u : allUsers) {
            userList.getItems().add(u);
        }
    }

    @FXML
    public void doMenu(ActionEvent e) {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    @FXML
    public void doCreateUser(ActionEvent e) {
        Main.getSceneManager().showCreateUpdateUserScene(null);
    }

    @FXML
    public void doUpdateUser(ActionEvent e) {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
            verkeerdeInlogGegevens.setContentText("Je moet een gebruiker kiezen");
            verkeerdeInlogGegevens.show();
            return;
        }
        Main.getSceneManager().showExistingUserScene(user);
    }

    @FXML
    public void doDeleteUser(ActionEvent e) {
        User user = userList.getSelectionModel().getSelectedItem();
        System.out.println(user);
        if (user == null) {
            Alert niksGeselecteerdFout = new Alert(Alert.AlertType.ERROR);
            niksGeselecteerdFout.setContentText("Je moet een gebruiker kiezen");
            niksGeselecteerdFout.show();
        } else {
            UserDAO userDAO = new UserDAO(Main.getDBaccess());
            userDAO.deleteUser(user);
            Main.getSceneManager().showManageUserScene();
            Alert verwijder = new Alert(Alert.AlertType.INFORMATION);
            verwijder.setContentText("gebruiker is verwijderd.");
            verwijder.show();
        }
    }
}
