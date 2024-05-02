package com.redhat.developers;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Path("/")
public class MainResource {

    @Inject
    Template index;

    @Inject
    IncrementService incrementService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance mainPage() {
        int count = 0;

        if(incrementService.keys().await().indefinitely().contains("count")) {
            count = incrementService.get("count");
        }

        incrementService.set("count", ++count);



        return index.data("count", count);
    }

}
