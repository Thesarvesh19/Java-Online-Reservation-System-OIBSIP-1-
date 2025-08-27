import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random; //seat available or not to check randomly
import java.util.Scanner;


class Ticket {
    private static int pnrCounter = 1000;
    private String pnr;
    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String from;
    private String to;

    public Ticket(String passengerName, String trainNumber, String trainName, String classType, String journeyDate, String from, String to) {
        this.pnr = "PNR" + pnrCounter++;
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.from = from;
        this.to = to;
    }

    public String getPnr() {
        return pnr;
    }

    @Override
    public String toString() {
        // Using multiple colors for the ticket
        return OnlineReservationSystem.ANSI_YELLOW + "PNR: " + OnlineReservationSystem.ANSI_WHITE + pnr +
               OnlineReservationSystem.ANSI_YELLOW + ", Name: " + OnlineReservationSystem.ANSI_WHITE + passengerName +
               OnlineReservationSystem.ANSI_YELLOW + ", Train: " + OnlineReservationSystem.ANSI_WHITE + trainNumber + " (" + trainName + ")" +
               OnlineReservationSystem.ANSI_YELLOW + ", Class: " + OnlineReservationSystem.ANSI_WHITE + classType +
               OnlineReservationSystem.ANSI_YELLOW + ", Date: " + OnlineReservationSystem.ANSI_WHITE + journeyDate +
               OnlineReservationSystem.ANSI_YELLOW + ", From: " + OnlineReservationSystem.ANSI_WHITE + from +
               OnlineReservationSystem.ANSI_YELLOW + ", To: " + OnlineReservationSystem.ANSI_WHITE + to;
    }
}

public class OnlineReservationSystem {

    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";


    // In-memory storage 
    private static Map<String, String> users = new HashMap<>(); 
    private static ArrayList<Ticket> tickets = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        initializeData();

        System.out.println(ANSI_PURPLE + "==========================================");
        System.out.println("   WELCOME TO THE ONLINE RESERVATION SYSTEM");
        System.out.println("==========================================" + ANSI_RESET);

        if (!login()) {
            System.out.println(ANSI_RED + "Login failed. Exiting system." + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_GREEN + "\nLogin Successful!" + ANSI_RESET);
        showMenu();
    }

    private static void initializeData() {
        // Add some pre loaded users
        users.put("admin", "pass123");
        users.put("user", "password");
        users.put("sarvesh", "ss1915");
    }

    private static boolean login() {
        System.out.println(ANSI_CYAN + "\n--- LOGIN ---" + ANSI_RESET);
        for (int i = 0; i < 3; i++) { // Allow 3 login attempts only
            System.out.print(ANSI_YELLOW + "Enter Username: " + ANSI_RESET);
            String username = scanner.nextLine();
            System.out.print(ANSI_YELLOW + "Enter Password: " + ANSI_RESET);
            String password = scanner.nextLine();

            if (users.containsKey(username) && users.get(username).equals(password)) {
                return true;
            } else {
                System.out.println(ANSI_RED + "Invalid credentials. Please try again." + ANSI_RESET);
            }
        }
        return false;
    }

    private static void showMenu() {
        while (true) {
            System.out.println(ANSI_PURPLE + "\n--- MAIN MENU ---" + ANSI_RESET);
            System.out.println(ANSI_WHITE + "1. Book a Ticket");
            System.out.println(ANSI_WHITE + "2. Cancel a Ticket");
            System.out.println(ANSI_WHITE + "3. View All Tickets");
            System.out.println(ANSI_WHITE + "4. Exit" + ANSI_RESET);
            System.out.print(ANSI_YELLOW + "Choose an option: " + ANSI_RESET);

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    bookTicket();
                    break;
                case "2":
                    cancelTicket();
                    break;
                case "3":
                    viewAllTickets();
                    break;
                case "4":
                    System.out.println(ANSI_CYAN + "Thank you for using the system. Goodbye!" + ANSI_RESET);
                    return;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Please select a valid option." + ANSI_RESET);
            }
        }
    }

    private static void bookTicket() {
        System.out.println(ANSI_PURPLE + "\n--- TICKET BOOKING ---" + ANSI_RESET);
        
        System.out.print(ANSI_YELLOW + "Enter Train Number: " + ANSI_RESET);
        String trainNumber = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Enter Train Name: " + ANSI_RESET);
        String trainName = scanner.nextLine();

        // --- NEW FEATURE: SEAT AVAILABILITY CHECK ---
        String classType;
        Random random = new Random();
        while (true) {
            System.out.print(ANSI_YELLOW + "Enter Class Type (e.g., Sleeper, AC): " + ANSI_RESET);
            classType = scanner.nextLine();
            
            int seatsAvailable = random.nextInt(150) + 1; // Generates a random number between 1 and 150
            System.out.println(ANSI_WHITE + "Checking availability... " + seatsAvailable + " seats are available in " + classType + " class." + ANSI_RESET);
            
            System.out.print(ANSI_YELLOW + "Do you want to proceed with this class? (yes/no): " + ANSI_RESET);
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                break; // Exit the loop and continue with booking
            } else {
                System.out.println(ANSI_CYAN + "Let's try another class." + ANSI_RESET);
            }
        }
        // --- END OF NEW FEATURE ---

        System.out.print(ANSI_YELLOW + "Enter Passenger Name: " + ANSI_RESET);
        String passengerName = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Enter Date of Journey (YYYY-MM-DD): " + ANSI_RESET);
        String journeyDate = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Enter From (Place): " + ANSI_RESET);
        String from = scanner.nextLine();
        System.out.print(ANSI_YELLOW + "Enter To (Destination): " + ANSI_RESET);
        String to = scanner.nextLine();

        Ticket newTicket = new Ticket(passengerName, trainNumber, trainName, classType, journeyDate, from, to);
        tickets.add(newTicket);

        System.out.println(ANSI_GREEN + "\nSuccess! Your ticket has been booked." + ANSI_RESET);
        System.out.println(newTicket.toString() + ANSI_RESET);
    }

    private static void cancelTicket() {
        System.out.println(ANSI_PURPLE + "\n--- TICKET CANCELLATION ---" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "Enter your PNR number to cancel: " + ANSI_RESET);
        String pnrToCancel = scanner.nextLine();

        Ticket ticketToCancel = null;
        for (Ticket t : tickets) {
            if (t.getPnr().equalsIgnoreCase(pnrToCancel)) {
                ticketToCancel = t;
                break;
            }
        }

        if (ticketToCancel == null) {
            System.out.println(ANSI_RED + "PNR not found. Please enter a valid PNR." + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "\nTicket Details Found:" + ANSI_RESET);
            System.out.println(ticketToCancel.toString() + ANSI_RESET);
            System.out.print(ANSI_YELLOW + "Are you sure you want to cancel this ticket? (yes/no): " + ANSI_RESET);
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                tickets.remove(ticketToCancel);
                System.out.println(ANSI_GREEN + "Your ticket has been successfully cancelled." + ANSI_RESET);
            } else {
                System.out.println("Cancellation aborted.");
            }
        }
    }

    private static void viewAllTickets() {
        System.out.println(ANSI_PURPLE + "\n--- ALL BOOKED TICKETS ---" + ANSI_RESET);
        if (tickets.isEmpty()) {
            System.out.println(ANSI_YELLOW + "No tickets have been booked yet." + ANSI_RESET);
        } else {
            for (Ticket ticket : tickets) {
                System.out.println(ANSI_BLUE + "----------------------------------" + ANSI_RESET);
                System.out.println(ticket.toString() + ANSI_RESET);
            }
            System.out.println(ANSI_BLUE + "----------------------------------" + ANSI_RESET);
        }
    }
}
