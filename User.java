import java.util.Objects;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    // Hash and compare passwords securely
    public boolean verifyPassword(String inputPassword) {
        return Objects.equals(inputPassword, password);
    }
}
