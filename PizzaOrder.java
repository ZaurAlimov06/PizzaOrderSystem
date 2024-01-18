package hw5;

import javax.management.OperationsException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PizzaOrder {
    static Scanner scanner = new Scanner(System.in);
    static Repository repository = new Repository();
    static List<String> selectedIngredients = new ArrayList<>();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome Pizza Order System!");
            System.out.println("Press 1 to order pizza!");
            System.out.println("Press 2 to view all orders!");
            System.out.println("Press 3 to change order status!");
            System.out.println("Press 0 to exit!");

            int firstChoice = scanner.nextInt();
            scanner.nextLine();

            switch (firstChoice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    repository.fetchPizzaTypes();

                    System.out.println("Select pizza type: ");
                    int pizzaTypeChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (pizzaTypeChoice > 0) {
                        Order order = new Order();
                        order.setStatus(PizzaStatus.IN_PROGRESS);

                        String selectedPizzaType = repository.fetchSelectedPizzaTypes(pizzaTypeChoice);
                        order.setPizzaType(selectedPizzaType);

                        double price = repository.fetchPizzaTypePrice(pizzaTypeChoice);

                        System.out.println("Select ingredients: ");

                        while (true) {
                            repository.fetchIngredients();

                            System.out.print("Enter number of ingredient you want to add (0 to finish): ");
                            int ingredientChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (ingredientChoice == 0) {
                                break;
                            }

                            if (ingredientChoice > 0) {
                                selectedIngredients.add(repository.fetchSelectedIngredient(ingredientChoice));
                                price = price + repository.fetchIngredientPrice(ingredientChoice);
                            } else {
                                System.out.println("Invalid ingredient choice!");
                            }
                        }

                        order.setIngredientsList(selectedIngredients);
                        order.setPrice(price);

                        System.out.println(order);

                        repository.insertOrder(order);
                    } else {
                        System.out.println("Invalid input!");
                    }
                    break;
                case 2:
                    repository.fetchAllOrders();
                    break;
                case 3:
                    System.out.println("Here is the all orders: ");
                    repository.fetchAllOrders();

                    System.out.print("Enter ID of order thar you want to change status: ");
                    int orderID = scanner.nextInt();
                    scanner.nextLine();

                    repository.changeOrderStatus(orderID);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }


    }
}

