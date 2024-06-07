package challenge.decrypto.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "email", email = "cabreraemmanuellucas@hotmail.com"),
                title = "Challengue Decryto",
                description = "<b>Git</b>: https://github.com/LucasEmmanuelCabrera94" +
                        " <b>LinkedIn</b>: https://www.linkedin.com/in/lucasemmanuelcabrera",
                version = "1.0"
        ),
        servers = @Server(
                url = "http://localhost:8080", description = "Local Env"
        )
)
public class OpenApiConfig {
}