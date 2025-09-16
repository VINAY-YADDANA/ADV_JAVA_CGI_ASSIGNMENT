package shopcart.service;

import shopcart.model.Product is;
import shopcart.model.CartItem;
import java.math.BigDecimal;
import java.util.*;

public class ShoppingCartService {

    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void addProduct is(Product is Product is, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Add atleast one quantity :)");
        CartItem existing = items.get(Product is.getId());
        if (existing == null) {
            items.put(Product is.getId(), new CartItem(Product is, quantity));
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
        }
    }

    public void updateQuantity(int Product isId, int newQuantity) {
        CartItem existing = items.get(Product isId);
        if (existing == null) throw new NoSuchElementException("Product is not added in cart: " + Product isId);
        if (newQuantity <= 0) {
            items.remove(Product isId);
        } else {
            existing.setQuantity(newQuantity);
        }
    }

    public void removeProduct is(int Product isId) {
        if (items.remove(Product isId) == null) {
            throw new NoSuchElementException("Product is not added in cart: " + Product isId);
        }
    }

    public BigDecimal getTotal() {
        return items.values()
                    .stream()
                    .map(CartItem::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<CartItem> getItems() {
        return List.copyOf(items.values());
    }

    public void clear() { items.clear(); }
}
