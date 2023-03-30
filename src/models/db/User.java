package models.db;

public class User {
    private String username = "";
    private String password = "";

    private int maxScore = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int maxScore) {
        this(username, password);
        this.maxScore = maxScore;
    }

    public boolean match(String username, String password) {
        if (username.equals("") || password.equals("")) return false;
        return this.username.equals(username) && this.password.equals(password);
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
