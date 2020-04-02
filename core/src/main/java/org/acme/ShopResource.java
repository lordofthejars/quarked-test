package org.acme;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/checkout")
public class ShopResource {

    @Inject
    CheckoutProcess checkoutProcess;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(ShoppingBasket shoppingBasket) {
        
        Long id = checkoutProcess.checkout(shoppingBasket);

        UriBuilder uriBuilder = UriBuilder.fromResource(ShopResource.class);
        uriBuilder.path(Long.toString(id));

        return Response.created(uriBuilder.build()).build();
    }
}