package hw5;

import java.sql.*;

public class Repository {
    Connection connection;

    public Repository() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DbConfig.url, DbConfig.username, DbConfig.password);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void fetchAllOrders() {
        String selectAllOrders = "SELECT * FROM pizzaorder";
        try (PreparedStatement statement = connection.prepareStatement(selectAllOrders)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("Order_ID");
                String type = resultSet.getString("Type");
                String ingredients = resultSet.getString("Ingredients");
                String status = resultSet.getString("Status");
                double price = resultSet.getDouble("Price");

                System.out.println("Order ID: " + orderId +
                        ", Type: " + type +
                        ", Ingredients: " + ingredients +
                        ", Status: " + status +
                        ", Price: " + price);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void fetchIngredients() {
        String selectAllIngredients = "SELECT * FROM ingredients";
        try (PreparedStatement statement = connection.prepareStatement(selectAllIngredients)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int Id = resultSet.getInt("ID");
                String ingredient = resultSet.getString("Ingredient");
                double price = resultSet.getDouble("Price");

                System.out.println("Ingredient ID: " + Id +
                        ", Ingredient: " + ingredient +
                        ", Price: " + price);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void fetchPizzaTypes() {
        String selectAllPizzaTypes = "SELECT * FROM pizzatype";
        try (PreparedStatement statement = connection.prepareStatement(selectAllPizzaTypes)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int Id = resultSet.getInt("ID");
                String pizzaType = resultSet.getString("PizzaType");
                double price = resultSet.getDouble("Price");

                System.out.println("Pizza Type ID: " + Id +
                        ", Ingredient: " + pizzaType +
                        ", Price: " + price);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String fetchSelectedPizzaTypes(int pizzaID) {
        String pizzaTypeQuery = "SELECT PizzaType FROM pizzatype WHERE ID=?";
        String pizzaType = "";
        try (PreparedStatement statement = connection.prepareStatement(pizzaTypeQuery)) {
            statement.setInt(1, pizzaID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pizzaType = resultSet.getString("PizzaType");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return pizzaType;
    }

    public double fetchPizzaTypePrice(int pizzaID) {
        String pizzaPriceQuery = "SELECT Price FROM pizzatype WHERE ID=?";
        double pizzaTypePrice = 0;
        try (PreparedStatement statement = connection.prepareStatement(pizzaPriceQuery)) {
            statement.setInt(1, pizzaID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pizzaTypePrice = resultSet.getDouble("Price");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return pizzaTypePrice;
    }

    public String fetchSelectedIngredient(int ingredientID) {
        String ingredientQuery = "SELECT Ingredient FROM ingredients WHERE ID=?";
        String ingredient = "";
        try (PreparedStatement statement = connection.prepareStatement(ingredientQuery)) {
            statement.setInt(1, ingredientID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredient = resultSet.getString("Ingredient");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ingredient;
    }

    public double fetchIngredientPrice(int ingredientID) {
        String ingredientPriceQuery = "SELECT Price FROM ingredients WHERE ID=?";
        double ingredientPrice = 0;
        try (PreparedStatement statement = connection.prepareStatement(ingredientPriceQuery)) {
            statement.setInt(1, ingredientID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredientPrice = resultSet.getDouble("Price");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ingredientPrice;
    }

    public void insertOrder(Order order) {
        String insertQuery = "INSERT INTO PizzaOrder (Type, Ingredients, Status, Price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, order.getPizzaType());

            String ingredientsString = String.join(", ", order.getIngredientsList());
            preparedStatement.setString(2, ingredientsString);

            preparedStatement.setString(3, String.valueOf(order.getStatus()));
            preparedStatement.setDouble(4, order.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pizza order successfully placed!");
            } else {
                System.out.println("Failed to place the pizza order.");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void changeOrderStatus(int orderID) {
        String changeOrderStatusQuery = "UPDATE pizzaorder SET Status = ? WHERE Order_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(changeOrderStatusQuery)) {
            statement.setString(1, PizzaStatus.READY.toString());
            statement.setInt(2, orderID);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Status updated!");
            } else {
                System.out.println("Failed to update the status for the order with ID: " + orderID);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
