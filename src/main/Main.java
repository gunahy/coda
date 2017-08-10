package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.system.ActiveDirectory;
import main.system.CSVReader;
import main.system.CompanyProperties;
import main.system.User;
import main.view.RootLayoutController;
import main.view.UserEditDialogController;
import main.view.UserOverviewController;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.logging.Logger;

import static main.system.IUsers.CN;

public class Main extends Application {
    private static final Logger LOG = Logger.getLogger(ActiveDirectory.class.getName());
    private static final int COMPANY_COUNT = 7;
    private Stage primaryStage;
    private CSVReader csvReader;
    private BorderPane rootLayout;
    private UserOverviewController userOverviewController;

    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<User> bufferedUserData = FXCollections.observableArrayList();
    //TODO создать массив объектов ActiveDirectory и все их подключить
    private ActiveDirectory adConnection;
    private ActiveDirectory[] adConnections;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Сотрудники");
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        initADConnections();
        initRootLayout();
        showUserOverview();

    }

    /**
     * Инициализация главной сцены
     */
    private void initRootLayout(){
        try {
            //Загрузка макета из fxml файла
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));

            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Даем контроллеру доступ к главному приложению
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initADConnections() throws IOException {
        adConnections = new ActiveDirectory[COMPANY_COUNT];
        adConnections[0] = new ActiveDirectory(CompanyProperties.MS11.getServerName());
        adConnections[1] = new ActiveDirectory(CompanyProperties.MO15.getServerName());
        adConnections[2] = new ActiveDirectory(CompanyProperties.MO29.getServerName());
        adConnections[3] = new ActiveDirectory(CompanyProperties.MO36.getServerName());
        adConnections[4] = new ActiveDirectory(CompanyProperties.MO87.getServerName());
        adConnections[5] = new ActiveDirectory(CompanyProperties.SU.getServerName());
        adConnections[6] = new ActiveDirectory(CompanyProperties.DSU.getServerName());

    }
    /**
     * Вывод пользовательских данных в табличной части и
     * информации "Подробно"
     */
    private void showUserOverview(){
        try {
            //Загружаем форму списка пользователей
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/UserOverview.fxml"));
            AnchorPane userOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(userOverview);

            //Даем контроллеру доступ к главному приложению
            userOverviewController = loader.getController();
            userOverviewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showUserEditDialog(User user){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/UserEditDialog.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Изменить данные сотрудника");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Загрузка сотрундиков из файла @param filename
     * @param fileName полное путь к файлу для загрузки данных о сотрудниках
     */
    public void loadUserDataFromFile(String fileName){

        userData.clear();
        csvReader = new CSVReader(fileName);
        userData.addAll(csvReader.getUsers());

        // Для каждого подключения создается поток и проивзодится поиск пользователя в базе AD
        for (ActiveDirectory adc : adConnections) {
         Runnable task = () -> compareToAD(adc, userData);
         Thread tr = new Thread(task);
         tr.start();
        }

//        // Проверяем текущее подключение к ActiveDirectory
//        if (adConnection != null) adConnection.closeLdapConnection();
//        //Для каждой организации созадать подлкючение и поискать там пользователей
//        for (CompanyProperties cp : CompanyProperties.values()) {
//            adConnection = new ActiveDirectory(cp.getServerName());
//
//            // Фильтруем список пользователей по организации и сравниваем с базой ActiveDirectory
//            bufferedUserData.addAll(csvReader.getUsersByCompany(cp));
//            compareToAD(adConnection, bufferedUserData);
//            userData.addAll(bufferedUserData);
//
//            // Очищаем список для новой итерации
//            bufferedUserData.clear();
//
//            // Закрываем текущее соедниение
//            adConnection.closeLdapConnection();
//        }

    }

    /**
     * Поиск сотрудников в базе ActiveDirectory. Если сотрудник найден, то его поле
     * inAdFound становится true
     * @param adConnection текущее соединение с ActiveDirectory
     * @param currentUserList список пользователей для сравнения
     */
    private void compareToAD (ActiveDirectory adConnection, ObservableList<User> currentUserList){
        if (adConnection != null){
            for (User user : currentUserList) {
                try {
                    NamingEnumeration<SearchResult> searchResult = adConnection.searchUser(user.getFullName(), CN, user.getCompanyProperty().getSearchBase());
                    if (searchResult.hasMoreElements()){
                        user.setInAdFound("+");
                        SearchResult sr = (SearchResult) searchResult.next();
                        user.setDistinguishedName(sr.getName());
                    }
                } catch (NamingException e) {
                    LOG.severe(e.getMessage());
                }
            }

        }
    }

    //TODO Создать метод который изменяет аттрибуты пользователей в AD добавить кнопку на форме

    /**
     * Возвращает список польователей для отображения в таблице
     * @return список объектов пользователи
     */
    public ObservableList<User> getUserData() {
        return userData;
    }

    public void updateUsersADInfo(User user){
        for (ActiveDirectory adc : adConnections) {
            adc.replaceAttributes(user);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
