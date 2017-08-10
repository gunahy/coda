package main.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Main;
import main.system.*;


public class UserOverviewController {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> eventColumn;
    @FXML
    private TableColumn<User, String> inAdFoundColumn;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label companyLabel;
    @FXML
    private Label eventLabel;
    @FXML
    private Label eventDateLabel;
    @FXML
    private Label birthdayLabel;



    private Main mainApp;

    @FXML
    private void initialize() {

        // Заполнение полей таблицы
        fullNameColumn.setCellValueFactory(celldata -> celldata.getValue().fullNameProperty());
        eventColumn.setCellValueFactory(celldata -> celldata.getValue().eventProperty());
        inAdFoundColumn.setCellValueFactory(celldata-> celldata.getValue().inAdFoundProperty());

        // Очистка области "Подробно"
        showUserDetails(null);

        // Отслеживает изменения данных пользователей и обновляет вывод в таблицу
        userTableView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> showUserDetails(newValue))
        );

    }

    /**
     * Заплняет все текстовые поля формы "Подробно"
     * @param selectedUser объект User или null
     */
    private void showUserDetails(User selectedUser) {
        if (selectedUser != null){
            fullNameLabel.setText(selectedUser.getFullName());
            positionLabel.setText(selectedUser.getPosition());
            departmentLabel.setText(selectedUser.getDepartment());
            companyLabel.setText(selectedUser.getCompany());
            eventLabel.setText(selectedUser.getEvent());
            eventDateLabel.setText(selectedUser.getEventDate());
            birthdayLabel.setText(selectedUser.getBirthday());
        } else {
            fullNameLabel.setText("");
            positionLabel.setText("");
            departmentLabel.setText("");
            companyLabel.setText("");
            eventLabel.setText("");
            eventDateLabel.setText("");
            birthdayLabel.setText("");
        }

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        userTableView.setItems(mainApp.getUserData());
    }

    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            userTableView.getItems().remove(selectedIndex);
        }
        else {
            //Ничего не выбрано
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Предупреждение!");
            alert.setHeaderText("Не выбран ни один сотрудник");
            alert.setContentText("Пожалуйста, выбирете сотрудника в табличной части");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditUser(){
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            boolean okClicked = mainApp.showUserEditDialog(selectedUser);
            if (okClicked){
                showUserDetails(selectedUser);
            }
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Предупреждение!");
            alert.setHeaderText("Не выбран ни один сотрудник");
            alert.setContentText("Пожалуйста, выбирете сотрудника в табличной части");

            alert.showAndWait();
        }
    }


    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            mainApp.updateUsersADInfo(selectedUser);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Предупреждение!");
            alert.setHeaderText("Не выбран ни один сотрудник");
            alert.setContentText("Пожалуйста, выбирете сотрудника в табличной части");

            alert.showAndWait();
        }
    }
}
