package shopcart.model;

import java.math.BigDecimal;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Add atleast one qunatity");
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Add atleast one qunatity");
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "CartItem{" +
            "product=" + product +
            ", quantity=" + quantity +
            ", subtotal=" + getSubtotal() +
            '}';
    }
}
