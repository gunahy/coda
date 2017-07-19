package main.system;

/**
 * Created by Kobzar on 13.07.2017.
 */
public class ActiveDirectoryConnection implements Runnable {

    private User user;

    public ActiveDirectoryConnection(User user) {
        this.user = user;

    }

    @Override
    public void run() {

    }
}
