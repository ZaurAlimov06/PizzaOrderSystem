package hw5;

import java.util.Arrays;
import java.util.List;

public class Order {
    private String pizzaType;
    private String[] ingredientsList;
    private PizzaStatus status;
    private double price;

    public String getPizzaType() { return pizzaType; }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public String[] getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList.toArray(new String[0]);
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return
                "pizzaType= " + pizzaType + "\n" +
                "ingredientsList= " + Arrays.toString(ingredientsList) + "\n"+
                "status=" + status + "\n"+
                "price=" + price + "\n";
    }
//    public Order(String pizzaType, String[] ingredientsList, PizzaStatus status, double price) {
//        this.pizzaType = pizzaType;
//        this.ingredientsList = ingredientsList;
//        this.status = status;
//        this.price = price;
//    }
}
