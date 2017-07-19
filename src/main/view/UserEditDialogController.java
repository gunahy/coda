package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.system.User;

/**
 * Created by Kobzar on 19.07.2017.
 */
public class UserEditDialogController {


    @FXML
    private TextField fullNameField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField companyField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField eventField;
    @FXML
    private TextField eventDateField;

    private User user;
    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User user){
        this.user = user;

        fullNameField.setText(user.getFullName());
        positionField.setText(user.getPosition());
        departmentField.setText(user.getDepartment());
        companyField.setText(user.getCompany());
        birthdayField.setText(user.getBirthday());
        eventField.setText(user.getEvent());
        eventDateField.setText(user.getEventDate());


    }

    /**
     * Проверка правильности заплнения полей ввода
     * @return true если все поля заполнены правильно, иначе false
     * и вывод сообщения
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (fullNameField.getText() == null || fullNameField.getText().length() == 0){
            errorMessage += "Некорректно введены ФИО";
        }

        if (positionField.getText() == null || positionField.getText().length() == 0){
            errorMessage += "Некорректно введена должность";
        }

        if (departmentField.getText() == null || departmentField.getText().length() == 0){
            errorMessage += "Некорректно введен отдел";
        }

        if (companyField.getText() == null || companyField.getText().length() == 0){
            errorMessage += "Некорректно введен филиал";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0){
            errorMessage += "Некорректно введена дата рождения";
        }

        if (eventField.getText() == null || eventField.getText().length() == 0){
            errorMessage += "Некорректно введено событие";
        }

        if (eventDateField.getText() == null || eventDateField.getText().length() == 0){
            errorMessage += "Некорректно введена дата события";
        }

        if (errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Пожалуйста, проверте введенные данные");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;

        }
    }

    /**
     * Обработчик события нажатия кнопки "Cancel"
     */
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    /**
     * Обработчик события нажатия кнопки "ОК"
     */
    @FXML
    private void handleOk(){
        if(isInputValid()){
            user.setFullName(fullNameField.getText());
            user.setPosition(positionField.getText());
            user.setDepartment(departmentField.getText());
            user.setCompany(companyField.getText());
            user.setBirthday(birthdayField.getText());
            user.setEvent(eventField.getText());
            user.setEventDate(eventDateField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }


    public boolean isOkClicked() {
        return okClicked;
    }
}
