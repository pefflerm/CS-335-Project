package cs335_package;

public class User {
    private String username;
    private String password; // In a real application, this should be hashed and salted sooo sorry

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // For simple plain text comparison
    public boolean checkPassword(String passwordAttempt) {
        return this.password.equals(passwordAttempt);
    }
}



//https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
//https://gist.github.com/vsoued/1059265
//used resrouces to see what i could do but did not copy