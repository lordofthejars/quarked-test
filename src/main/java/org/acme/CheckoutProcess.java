package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Channel;

@ApplicationScoped
public class CheckoutProcess {

    @Inject
    PriceService priceService;

    @Inject
    @Channel("delivery")
    Emitter<String> delivery;

    public Long checkout(ShoppingBasket shoppingBasket) {
        Double total = this.priceService.calculate(shoppingBasket);
        
        shoppingBasket.persist();
        
        Invoice invoice = new Invoice();
        invoice.shoppingBasket = shoppingBasket;
        invoice.total = total;

        invoice.persist();

        delivery.send(JsonbBuilder.create().toJson(shoppingBasket));

        return invoice.id;

    }
    
}