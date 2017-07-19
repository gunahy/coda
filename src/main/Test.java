package main;

import main.system.User;

/**
 * Created by Kobzar on 18.07.2017.
 */
public class Test {
    public static void main(String[] args) {
        User user = new User.UserBuilder("Nhzv").build();
        System.out.println(user.getFullName());
    }
}
