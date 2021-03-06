package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Group;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ManageGroupsController{

    private DBAccess dbAccess;

    @FXML
    private ListView<Group> groupList;

    // setup, lijst van groepen tonen
    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        System.out.println(groupDAO.getAll());
        try {
            ArrayList<Group> allGroups = groupDAO.getAll();
            Collections.sort(allGroups);
            Iterator i = allGroups.iterator();
            while (i.hasNext()) {
                Group g = (Group) i.next();
                groupList.getItems().add(g);
            }
        }
        catch (Exception fout){
            fout.getMessage();
        }
    }


// Menuknop: terug naar WelcomeScene (inlog blijft behouden)
    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


    @FXML
    public void doCreateGroup() {
        Main.getSceneManager().showCreateUpdateGroupScene(null);
    }

    @FXML
    public void doUpdateGroup() {
        Group group = groupList.getSelectionModel().getSelectedItem();
        if (group == null) {
            alertSetupManage("Selecteer een groep.");
            return;
        }
        Main.getSceneManager().showCreateUpdateGroupScene(group);
    }

    @FXML
    public void doDeleteGroup() {
        Group group = groupList.getSelectionModel().getSelectedItem();
        System.out.println(group);
        if (group == null) {
            alertSetupManage("Selecteer een groep.");
        } else {
            GroupDAO groupDAO = new GroupDAO(Main.getDBaccess());
            groupDAO.deleteGroupByName(group.getGroupName());
            alertSetupManage("Groep is verwijderd.");
            Main.getSceneManager().showManageGroupsScene();
        }
    }

    public void alertSetupManage(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(string);
        alert.show();
    }
}
