package main.system;


import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Kobzar on 07.06.2017.
 */
public final class User implements IUsers {
    private SimpleStringProperty fullName,
            position,
            department,
            company,
            event,
            birthday,
            eventDate,
            inAdFound,
            distinguishedName;

    private CompanyProperties companyProperties;

    private User(UserBuilder builder){
        this.fullName = new SimpleStringProperty(builder.fullName);
        this.position = new SimpleStringProperty(builder.position);
        this.department = new SimpleStringProperty(builder.department);
        this.company = new SimpleStringProperty(builder.company);
        this.event = new SimpleStringProperty(builder.event);
        this.birthday = new SimpleStringProperty(builder.birthday);
        this.eventDate = new SimpleStringProperty(builder.eventDate);
        this.inAdFound = new SimpleStringProperty("-");
        this.distinguishedName = new SimpleStringProperty("");

        for (CompanyProperties cp : CompanyProperties.values()){
            if (cp.getCompanyName().equals(getCompany()))
                companyProperties = cp;
        }
    }

    @Override
    public String toString() {
        return "===*" +
                fullName + " || " +
                position + " || " +
                department + " || " +
                company + " || " +
                event + " || " +
                birthday + " || " +
                eventDate + " || " +
                inAdFound +
                "*===";
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public String getEvent() {
        return event.get();
    }

    public SimpleStringProperty eventProperty() {
        return event;
    }

    public String getBirthday() {
        return birthday.get();
    }

    public SimpleStringProperty birthdayProperty() {
        return birthday;
    }

    public String getEventDate() {
        return eventDate.get();
    }

    public SimpleStringProperty eventDateProperty() {
        return eventDate;
    }

    public CompanyProperties getCompanyProperties() {
        return companyProperties;
    }

    public String getInAdFound() {
        return inAdFound.get();
    }

    public SimpleStringProperty inAdFoundProperty() {
        return inAdFound;
    }

    public void setInAdFound(String inAdFound) {
        this.inAdFound.set(inAdFound);
    }

    public CompanyProperties getCompanyProperty() {
        return companyProperties;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public void setEventDate(String eventDate) {
        this.eventDate.set(eventDate);
    }

    public void setCompanyProperties(CompanyProperties companyProperties) {
        this.companyProperties = companyProperties;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName.set(distinguishedName);
    }

    public String getDistinguishedName() {
        return distinguishedName.get();
    }

    public SimpleStringProperty distinguishedNameProperty() {
        return distinguishedName;
    }

    /**
     * Builder-класс для объектов User
     */
    public static class UserBuilder {
        private String fullName,
                position,
                department,
                company,
                event,
                birthday,
                eventDate;

        public UserBuilder(String fullName) {
            this.fullName = fullName;
        }

        public UserBuilder addPosition(String position){
            this.position = position;
            return this;
        }

        public UserBuilder addDepartment(String department){
            this.department = department;
            return this;
        }

        public UserBuilder addCompany(String company) {
            this.company = company;
            return this;
        }

        public UserBuilder addEvent(String event) {
            switch (event){
                case "0": this.event = EVENT_0;
                    break;
                case "1": this.event = EVENT_1;
                    break;
                case "2": this.event = EVENT_2;
                    break;
            }
            return this;
        }

        public UserBuilder addBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBuilder addEventDate(String eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
