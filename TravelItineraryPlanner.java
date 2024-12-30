import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TravelItineraryPlanner {

    public static Scanner scanner = new Scanner(System.in);
    public static double totalBudget;

    static class Destination {
        String name;
        String activity;
        String date;

        Destination(String name, String activity, String date) {
            this.name = name;
            this.activity = activity;
            this.date = date;
        }

        @Override
        public String toString() {
            System.out.println("Your destination is: " + name);
            try {
                System.out.print("Geological info: ");
                getMapInfo(name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getWeather(name);
            return "Activity: " + activity + "\nDate: " + date + "\nTotal budget: Rs" + totalBudget + "\n";
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Destination> itinerary = new ArrayList<>();

        System.out.print("Welcome to the Travel Itinerary Planner!");
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a destination");
            System.out.println("2. View itinerary");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter the destination name: ");
                String DestinationName = scanner.nextLine();

                System.out.print("Would you like to see the geological information of " + DestinationName + "? ");
                String bool = scanner.nextLine();
                if(bool.equalsIgnoreCase("YES")) getMapInfo(DestinationName);

                System.out.print("Would you like to see the weather information of " + DestinationName + "? ");
                String verify = scanner.nextLine();
                if(verify.equalsIgnoreCase("YES")) getWeather(DestinationName);

                System.out.print("Enter the activity at this destination (e.g., adventure,culture etc.): ");
                String activity = scanner.nextLine();

                System.out.print("Enter the date for this destination (e.g., YYYY-MM-DD): ");
                String date = scanner.nextLine();

                calculateBudget();

                itinerary.add(new Destination(DestinationName, activity, date));

            } else if (choice == 2) {
                if (itinerary.isEmpty()) {
                    System.out.println("Your itinerary is empty.");
                } else {
                    System.out.println("\nYour Itinerary:");
                    for(Destination destination : itinerary) {
                        System.out.println(destination);
                    }
                }

            } else if (choice == 3) {
                System.out.println("Thank you for using the Travel Itinerary Planner!");
                break;

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public static void getMapInfo(String destination) throws IOException {
        String apiKey = "K5FGSK3TRhcK1x4PSctcXlSGTjJPB-0cchQci2QSVn8";
        String query = "https://geocode.search.hereapi.com/v1/geocode?q=" + destination + "&apiKey=" + apiKey;

        URL url = new URL(query);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        System.out.println(response);
    }
    public static void getWeather(String city) {
        String apiKey = "0bd55f6a04b5ef6623504147b61e87ea";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Weather Info: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void calculateBudget() {
        System.out.print("Enter transportation cost: ");
        double transportation = scanner.nextDouble();

        System.out.print("Enter accommodation cost per night: ");
        double accommodation = scanner.nextDouble();

        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();

        System.out.print("Enter daily meal cost: ");
        double meals = scanner.nextDouble();

        System.out.print("Enter number of days: ");
        int days = scanner.nextInt();

        System.out.print("Enter other expenses: ");
        double other = scanner.nextDouble();

        totalBudget = transportation + (accommodation * nights) + (meals * days) + other;
        System.out.println("Total Estimated Budget: Rs" + totalBudget);
    }
}