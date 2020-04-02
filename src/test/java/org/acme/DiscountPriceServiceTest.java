package org.acme;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class DiscountPriceServiceTest {

    DiscountPriceService discountPriceService = new DiscountPriceService();

    @Test
    public void should_not_apply_any_discount() {

        // given

        DiscountGateway discountGateway = mock(DiscountGateway.class);
        when(discountGateway.getDiscount(anyString())).thenReturn("0");

        discountPriceService.discountGateway = discountGateway;
        ShoppingBasket shoppingBasket = ShoppingBasketObjectMother.theHobbiBasket();

        // when

        double total = discountPriceService.calculate(shoppingBasket);

        // then

        assertThat(total).isEqualTo(15);

    }

}