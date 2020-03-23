package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class DiscountPriceService implements PriceService {

	@RestClient
	DiscountGateway discountGateway;

	@Override
	public double calculate(ShoppingBasket shoppingBasket) {
		double total = shoppingBasket.calculateTotal();
		double discount = Double.parseDouble(discountGateway.getDiscount(Double.toString(total)));

		return total * (1 - discount);
	}
    
}