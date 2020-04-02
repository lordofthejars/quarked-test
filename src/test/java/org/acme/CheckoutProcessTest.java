package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
public class CheckoutProcessTest {

    @Inject
    CheckoutProcess checkoutProcess;

    @Inject
    DeliveryStream deliveryStream;

    @Test
    public void should_insert_invoice() {

        // given
        
        ShoppingBasket shoppingBasket = ShoppingBasketObjectMother.theHobbiBasket();

        // when

        checkoutProcess.checkout(shoppingBasket);

        // then

        Invoice invoice = Invoice.findInvoiceByTransaction(ShoppingBasketObjectMother.TRANSACTION_ID);

        assertThat(invoice.id).isNotNull();
    }
    
    @Test
    public void should_deliver_invoice() {
    
        // given
        
        ShoppingBasket shoppingBasket = ShoppingBasketObjectMother.theHobbiBasket();

        // when

        checkoutProcess.checkout(shoppingBasket);

        // then

        assertThat(deliveryStream.isAnyDelivery()).isTrue();

    }

}