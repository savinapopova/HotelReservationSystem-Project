package com.example.hotelreservationsystem;

import com.example.hotelreservationsystem.controller.AdminController;
import com.example.hotelreservationsystem.controller.BookingController;
import com.example.hotelreservationsystem.controller.SeedController;
import com.example.hotelreservationsystem.controller.UserController;
import com.example.hotelreservationsystem.model.dto.LoginDTO;
import com.example.hotelreservationsystem.service.AdminService;
import com.example.hotelreservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);


    private final UserService userService;


    private final AdminService adminService;


    private final SeedController seedController;

    private final UserController userController;

    private final AdminController adminController;

    private final BookingController bookingController;


    @Autowired
    public ConsoleRunner(UserService userService, AdminService adminService,
                          SeedController seedController, UserController userController,
                         AdminController adminController, BookingController bookingController) {

        this.userService = userService;
        this.adminService = adminService;
        this.seedController = seedController;
        this.userController = userController;
        this.adminController = adminController;
        this.bookingController = bookingController;
    }



    @Override
    public void run(String... args) throws Exception {


        seedController.seedAdmins();

        seedController.seedHotels();

        seedController.seedRooms();


        startMenu();


    }


    private void startMenu() throws IOException {
        System.out.println("Start Menu:");
        System.out.println("Register : 1");
        System.out.println("Login : 2");
        System.out.println("AdminMode : 3");
        System.out.println("Exit : 4");
        String command = scanner.nextLine();

        switch (command) {
            case "1":
                userController.register();
                startMenu();
                break;
            case "2":
                LoginDTO loginDTO = userController.loginUser();
                checkUser(loginDTO);
                break;
            case "3":
                LoginDTO adminDTO = adminController.loginAdmin();
                checkAdmin(adminDTO);
                break;
            case "4":
                return;
            default:
                System.out.println("Wrong input!");
                startMenu();
                break;
        }
    }



    private void displayAdminMenu() throws IOException {
        System.out.println("View Hotels : 1");
        System.out.println("View Rooms : 2");
        System.out.println("View Bookings : 3");
        System.out.println("Add Hotel : 4");
        System.out.println("Add Room: 5");
        System.out.println("Check Account Deposits: 6");
        System.out.println("Logout: 7");


        String command = scanner.nextLine();

        switch (command) {
            case "1":
                adminController.viewHotels();
                displayAdminMenu();
                break;
            case "2":
                adminController.viewRooms();
                displayAdminMenu();
                break;
            case "3":
                adminController.viewBookings();
                displayAdminMenu();
                break;
            case "4":
                     adminController.addHotel();
                     displayAdminMenu();
                break;
            case "5":
                     adminController.addRoom();
                     displayAdminMenu();
                break;
            case "6":
                   adminController.checkDeposits();
                   displayAdminMenu();
                break;
            case "7":
                  adminController.adminLogout();
                  startMenu();
                break;
            default:
                System.out.println("Invalid input!");
                displayAdminMenu();
        }
    }



    private void displayUserMenu() throws IOException {

        System.out.println("Book Room : 1");
        System.out.println("Cancel Reservation : 2");
        System.out.println("Logout: 3");

        String command = scanner.nextLine();

        switch (command) {
            case "1":
                bookingController.bookRoom();
                displayUserMenu();
                break;
            case "2":
                bookingController.cancelReservation();
                displayUserMenu();
                break;
            case "3":
                userController.logout();
                startMenu();
                break;
            default:
                System.out.println("Invalid input!");
                displayUserMenu();
        }

    }


    private void checkUser(LoginDTO loginDTO) throws IOException {
        if (loginDTO == null) {
            startMenu();
            return;
        }
        if (this.userService.isLogged(loginDTO.getUsername())) {
            displayUserMenu();
        } else {
            startMenu();
        }
    }


    private void checkAdmin(LoginDTO adminDTO) throws IOException {
        if (adminDTO == null) {
            startMenu();
            return;
        }
        if (this.adminService.isLogged(adminDTO.getUsername())) {
            displayAdminMenu();
        } else {
            startMenu();
        }
    }


}
