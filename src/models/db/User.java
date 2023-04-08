package models.db;

public class User {
    private String username = "";
    private String password = "";
    private int maxScore = 0, level = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int level) {
        this(username, password);
        this.level = level;
    }

    public User(String username, String password, int level, int maxScore) {
        this(username, password, level);
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

    public User setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getLevel() {
        return this.level;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
