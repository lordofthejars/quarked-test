package org.acme;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ShopResourceTest {

    @Test
    public void should_do_a_checkout() {
        ShoppingBasket shoppingBasket = ShoppingBasketObjectMother.theHobbiBasket();
        given().contentType(ContentType.JSON).body(shoppingBasket).when().post("/checkout").then().statusCode(201);
    }

}