package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CheckoutProcess {

    @Inject
    PriceService priceService;

    public Long checkout(ShoppingBasket shoppingBasket) {
        Double total = this.priceService.calculate(shoppingBasket);
        
        shoppingBasket.persist();
        
        Invoice invoice = new Invoice();
        invoice.shoppingBasket = shoppingBasket;
        invoice.total = total;

        invoice.persist();

        return invoice.id;

    }
    
}