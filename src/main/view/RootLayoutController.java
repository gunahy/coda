package main.view;


import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import main.Main;

import java.io.File;

/**
 * Created by Kobzar on 18.07.2017.
 */
public class RootLayoutController {

    private static final String OPEN_FILE_TITLE = "Открыть CSV файл";

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(OPEN_FILE_TITLE);

        // Задаем фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показать диалог загрузки файла
        File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        String filePath = selectedFile.getPath();

        if (filePath != null)
        mainApp.loadUserDataFromFile(filePath);

    }

    @FXML
    private void searchInAD() {
        mainApp.searchInAD();
    }
}
