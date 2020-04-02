package c.su.vishwam;

import android.app.Application;

import c.su.vishwam.models.User;

import c.su.vishwam.models.User;


public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
