
package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 6543;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Gson gson = new Gson();

    public void start() {
        try (Socket socket = new Socket("localhost", PORT)) {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter username: ");
            String username = scanner.nextLine();

            String loginRequest = ClientUtils.createLoginRequest(username);
            out.writeObject(loginRequest);

            String response = (String) in.readObject();
            System.out.println(response);

            boolean isAdmin = username.equalsIgnoreCase("ADMIN");

            while (true) {
                if (isAdmin) {
                    if (!adminMenu(scanner, username)) break;
                } else {
                    if (!userMenu(scanner, username)) break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private boolean adminMenu(Scanner scanner, String username) {
        try {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Location");
            System.out.println("2. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();

                System.out.print("Enter latitude: ");
                double latitude = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter longitude: ");
                double longitude = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter today's temperature: ");
                double day1 = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter tomorrow's temperature: ");
                double day2 = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter day after tomorrow's temperature: ");
                double day3 = Double.parseDouble(scanner.nextLine());

                String requestJson = ClientUtils.createAddLocationRequest(username, name, latitude, longitude, day1, day2, day3);
                out.writeObject(requestJson);

                String response = (String) in.readObject();
                System.out.println("\nServer response: " + response);

            } else if (choice == 2) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers!");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    private boolean userMenu(Scanner scanner, String username) {
        try {
            System.out.println("\n=== USER MENU ===");
            System.out.println("1. View Weather");
            System.out.println("2. Change Location");
            System.out.println("3. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                String requestJson = ClientUtils.createGetWeatherRequest(username);
                out.writeObject(requestJson);

                String response = (String) in.readObject();
                try {
                    Location location = gson.fromJson(response, Location.class);
                    System.out.println("\nCurrent Location Weather:");
                    System.out.println("Name: " + location.getName());
                    System.out.println("Coordinates: " + location.getLatitude() + ", " + location.getLongitude());
                    System.out.println("Today: " + location.getTodayTemp() + "°C");
                    System.out.println("Tomorrow: " + location.getTomorrowTemp() + "°C");
                    System.out.println("Day after tomorrow: " + location.getDayAfterTomorrowTemp() + "°C");
                } catch (JsonSyntaxException e) {
                    System.out.println("\n" + response);
                }
            } else if (choice == 2) {
                System.out.print("Enter latitude: ");
                double latitude = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter longitude: ");
                double longitude = Double.parseDouble(scanner.nextLine());

                String requestJson = ClientUtils.createChangeLocationRequest(username, latitude, longitude);
                out.writeObject(requestJson);

                String response = (String) in.readObject();
                System.out.println("\nServer response: " + response);

            } else if (choice == 3) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers!");
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }
}

