package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if(cartItems == null){
            cartItems = new HashSet<>();
            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(product.getCostPrice() * quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        }else{
            
        }
        return null;
    }

    //tim cai item theo id cua product
    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if(cartItems == null)
            return null;

        CartItem cartItem = null;
        for(CartItem item : cartItems){
            if(item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }
}

