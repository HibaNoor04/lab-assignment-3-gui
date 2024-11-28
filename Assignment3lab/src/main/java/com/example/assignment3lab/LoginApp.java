package com.example.assignment3lab;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginApp extends Application {

    private final ArrayList<String[]> credentials = new ArrayList<>();
    private final File credentialsFile = new File("credentials.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        loadCredentialsFromFile();


        BorderPane borderPane = new BorderPane();


        Image image = new Image("file:C:/Users/xehib/Desktop/18e798d03eaaf791f28db61aea9dbf41.jpg"); // Update path if necessary
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(false);

        borderPane.setTop(imageView);

        // Center GridPane for login form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        Label usernameLabel = new Label("User Name:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();


        Button loginButton = new Button("Login");
        Button saveButton = new Button("Save Credentials");
        Button exitButton = new Button("Exit");


        Label notificationLabel = new Label();


        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);


        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(loginButton, 0, 0);
        buttonPane.add(saveButton, 1, 0);
        buttonPane.add(exitButton, 2, 0);

        // Add button pane to the main GridPane
        gridPane.add(buttonPane, 0, 2, 2, 1);


        gridPane.add(notificationLabel, 0, 3, 2, 1);
        notificationLabel.setAlignment(Pos.CENTER);


        borderPane.setCenter(gridPane);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateCredentials(username, password)) {
                notificationLabel.setText("Login successful!");
                openNewWindow(username);
                primaryStage.close();
            } else {
                notificationLabel.setText("Invalid username or password!");
            }
        });


        saveButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                saveCredentialsToFile(username, password);
                notificationLabel.setText("Credentials saved successfully!");
            } else {
                notificationLabel.setText("Username and password cannot be empty!");
            }
        });


        exitButton.setOnAction(e -> primaryStage.close());

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Login Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadCredentialsFromFile() {
        try {
            if (!credentialsFile.exists()) {
                // If the file doesn't exist, create it
                credentialsFile.createNewFile();
            }


            Scanner scanner = new Scanner(credentialsFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentials.add(parts);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading credentials: " + e.getMessage());
        }
    }

    private boolean validateCredentials(String username, String password) {

        for (String[] pair : credentials) {
            if (pair[0].equals(username) && pair[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void saveCredentialsToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFile, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            credentials.add(new String[]{username, password}); // Update in-memory list as well
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }

    private void openNewWindow(String username) {

        Stage newStage = new Stage();
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setAlignment(Pos.CENTER);

        Scene scene = new Scene(welcomeLabel, 400, 200);
        newStage.setTitle("Welcome");
        newStage.setScene(scene);
        newStage.show();
    }
}
