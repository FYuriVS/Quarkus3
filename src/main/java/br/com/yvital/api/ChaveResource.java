package br.com.yvital.api;

import br.com.yvital.service.DictService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/chaves")
public class ChaveResource {
    @Inject
    DictService dictService;

    @Operation(description = "API oara buscar uma chave pix.")
    @APIResponseSchema(Response.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a chave."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),
    })

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{chave}")
    public Response buscar(@PathParam("chave") String chave){
        var chaveCached = dictService.buscarDetalhesChave(chave);
        return  Response.ok(chaveCached).build();
    }
}
