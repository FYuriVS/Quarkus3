package br.com.yvital.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "Cruso Quarkus 3.0", description = "Projeto inicial da grade curricular do curso.")
        },
        info = @Info(
                title = "Quarkus 3.0",
                version = "1.0.0",
                contact = @Contact(
                        name = "CoffeeAndit.com.br",
                        url = "www.coffeeandit.com.br",
                        email = "CoffeAndit@coffeeandit.com.br"
                ),
                license = @License(
                        name = "CoffeeAndIT",
                        url = "www.coffeeandir.com.br"
                )
        )
)
public class OpenApiConfig extends Application {
}
