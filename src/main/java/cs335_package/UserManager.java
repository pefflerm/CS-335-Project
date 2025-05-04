package cs335_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//https://learn.microsoft.com/en-us/dotnet/api/microsoft.aspnetcore.identity.usermanager-1?view=aspnetcore-9.0
public class UserManager {
    private static final String USERS_FILE = "places/users.csv";
    private Map<String, User> users = new HashMap<>();

    public UserManager() {
        System.out.println("UserManager initialized. Loading users from: " + new File(USERS_FILE).getAbsolutePath());
        loadUsers();
    }

    private void loadUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            System.out.println("Users file not found: " + file.getAbsolutePath());
            return; // No users file yet
        }

        System.out.println("Loading users from: " + file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], new User(parts[0], parts[1]));
                    System.out.println("Loaded user: " + parts[0]);
                }
            }
            System.out.println("Finished loading users. Total users loaded: " + users.size());
        } catch (IOException e) {
            System.err.println("Error loading users from " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        File file = new File(USERS_FILE);
        // Ensure the 'places' directory exists before saving
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create the directory if it doesn't exist
            System.out.println("Created directory: " + parentDir.getAbsolutePath());
        }

        System.out.println("Saving users to: " + file.getAbsolutePath());
        try (FileWriter writer = new FileWriter(file)) { // Overwrites the file
            for (User user : users.values()) {
                writer.append(user.getUsername())
                      .append(",")
                      .append(user.getPassword()) // Storing plain text password
                      .append("\n"); // Use \n for newline
            }
            System.out.println("Finished saving users. Total users saved: " + users.size());
        } catch (IOException e) {
            System.err.println("Error saving users to " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        System.out.println("Attempting to register user: " + username);
        if (users.containsKey(username)) {
            System.out.println("Registration failed: User '" + username + "' already exists.");
            return false; // User already exists
        }
        User newUser = new User(username, password);
        users.put(username, newUser);
        saveUsers();
        System.out.println("User '" + username + "' registered successfully.");
        return true;
    }

    public User loginUser(String username, String password) {
        System.out.println("Attempting to login user: " + username);
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful for user: " + username);
            return user; // Login successful
        }
        System.out.println("Login failed for user: " + username);
        return null; // Login failed
    }
}
