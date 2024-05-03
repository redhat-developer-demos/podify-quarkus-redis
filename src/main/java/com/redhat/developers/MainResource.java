package com.redhat.developers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class MainResource {

    @Inject
    Template index;

    @Inject
    IncrementService incrementService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance mainPage() {
        int count;

        if(incrementService.keys().await().indefinitely().contains("count")) {
            count = incrementService.get("count");
        } else {
            count = 1;
        }

        incrementService.increment("count", 1);

        return index.data("count", count);
    }

}
