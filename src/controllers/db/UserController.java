package controllers.db;

import models.db.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class UserController {
    private final User user;

    public UserController(User user) {
        this.user = user;
    }

    public static User findOne(String username, String password) {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        File db = new File(Path.of(path.toString(), "src", "config", "db", "accounts.txt").toString());
        try {
            Scanner scanner = new Scanner(db);
            int n = 0;
            if (scanner.hasNext())
                n = Integer.parseInt(scanner.nextLine().trim());

            for (int j = 0; j < n && scanner.hasNextLine(); j++) {

                String[] data = (scanner.nextLine()).split(";");
                String currentUsername = data[0].trim();
                String currentPassword = data[1].trim();
                int level = Integer.parseInt(data[2].trim());

                if (currentUsername.equals(username) && currentPassword.equals(password))
                    return new User(currentUsername, currentPassword, level);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean store() {

        if (user.getUsername().equals("") || user.getPassword().equals("")) return false;
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

        StringBuilder txt = new StringBuilder();

        File db = new File(Path.of(path.toString(), "src", "config", "db", "accounts.txt").toString());
        System.out.println("PATH: " + db.getPath());

        FileWriter fileWriter = null;
        int number = 0;

        try {
            fileWriter = new FileWriter(db, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        txt.append('\n').append(user.getUsername()).append(' ').append(user.getPassword());
        System.out.println(txt);

        try {
            fileWriter.write(txt.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;

    }

    public static boolean deleteOne(User user) {
        return false;
    }
}
