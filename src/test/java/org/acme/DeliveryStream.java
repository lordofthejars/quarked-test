package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class DeliveryStream {

    private List<ShoppingBasket> deliveredShoppingBaskets = new ArrayList<>();

    @Incoming("delivery")
    public void delivery(String shoppingBasket) {
        this.deliveredShoppingBaskets.add(JsonbBuilder.create().fromJson(shoppingBasket, ShoppingBasket.class));
    }
    
    public boolean isAnyDelivery() {
        return !this.deliveredShoppingBaskets.isEmpty();
    }

}