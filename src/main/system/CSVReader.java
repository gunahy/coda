package main.system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Kobzar on 07.06.2017.
 */
public class CSVReader{

    private static final String DELIMITER = ";";
    private static final String PATH = "E:/shared/AD update/";
    private static final int COLUMN_COUNT = 7;
    private static final int COLUMN_FULL_NAME = 0;
    private static final int COLUMN_POSITION = 1;
    private static final int COLUMN_DEPARTMENT = 2;
    private static final int COLUMN_COMPANY = 3;
    private static final int COLUMN_EVENT = 4;
    private static final int COLUMN_EVENT_DATE = 5;
    private static final int COLUMN_BIRTHDAY = 6;

    private ObservableList<User> users, usersByCompany;
    private BufferedReader br;

    public ObservableList<User> getUsers() {
        return users;
    }

    public ObservableList<User> getUsersByCompany(CompanyProperties companyProperties) {
        usersByCompany = FXCollections.observableArrayList();
        for (User user : getUsers()) {
            if (user.getCompanyProperty() == companyProperties)
                usersByCompany.add(user);
        }
        return usersByCompany;
    }
    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public CSVReader(String fileName) {
        String s;
        try {
            br = new BufferedReader(new FileReader(fileName));
            users = FXCollections.observableArrayList();

            while ((s = br.readLine()) != null) {
                String[] eemployer = s.split(DELIMITER);
                if (eemployer[COLUMN_POSITION].equals("Title")) continue;
                users.add(splitLineInUser(eemployer));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User splitLineInUser(String[] line) {

        String fullName, position, department, company, event, birthday, eventdate;

        if (line.length == COLUMN_COUNT) {
            fullName = line[COLUMN_FULL_NAME];
            position = line[COLUMN_POSITION];
            department = line[COLUMN_DEPARTMENT];
            company = line[COLUMN_COMPANY];
            event = line[COLUMN_EVENT];
            birthday = line[COLUMN_BIRTHDAY];
            eventdate = line[COLUMN_EVENT_DATE];

            User user = new User.UserBuilder(fullName)
                    .addCompany(company)
                    .addDepartment(department)
                    .addPosition(position)
                    .addBirthday(birthday)
                    .addEvent(event)
                    .addEventDate(eventdate)
                    .build();
            return user;

        }
        else {
            System.out.println("Размер массива не соответствует параметрам функции");
            return null;
        }
    }

    @Override
    public String toString() {
        String viewUser = "";

        return viewUser;
    }

}


