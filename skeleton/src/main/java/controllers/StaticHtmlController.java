package controllers;

import com.google.common.io.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class StaticHtmlController {

    @GET
    public String getIndexPage() throws IOException {
        Resources.getResource("index.html");
        return Resources.toString(Resources.getResource("index.html"), UTF_8);
    }
}