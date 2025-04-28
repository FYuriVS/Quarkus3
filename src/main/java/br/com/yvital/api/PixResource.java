package br.com.yvital.api;

import br.com.yvital.model.LinhaDigitavel;
import br.com.yvital.model.Pix;
import br.com.yvital.model.Transaction;
import br.com.yvital.service.DictService;
import br.com.yvital.service.PixService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Path("/v1/pix")
public class PixResource {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Inject
    DictService dictService;

    @Inject
    PixService pixService;

    @Operation(description = "API oara buscar uma linha digitável.")
    @APIResponseSchema(LinhaDigitavel.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a linha criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),
    })


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/linha")
    public Response gerarLinhaDigitavel(final Pix pix) {
        var chave = dictService.buscarChave(pix.chave());

        if (Objects.nonNull(chave)) {
            return Response.ok(pixService.gerarLinhaDigitavel(chave, pix.valor(), pix.cidadeRemetente())).build();
        }
        return null;
    }

    @Operation(description = "API oara buscar um QRCode a partir de um UUID específico.")
    @APIResponseSchema(Response.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),
    })

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/{uuid}/qrcode")
    public Response qrCode(@PathParam("uuid") String uuid) throws IOException {
        return Response.ok(pixService.gerarQrCode(uuid)).build();
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}/aprovar")
    @PATCH
    @Operation(description = "API responsável por aprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),    }
    )
    public Response aprovarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.aprovarTransacao(uuid).get()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}/reprovar")
    @DELETE
    @Operation(description = "API responsável por reprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),    }
    )
    public Response reprovarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.reprovarTransacao(uuid).get()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    @GET
    @Operation(description = "API responsável por buscar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),    }
    )
    public Response buscarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.findById(uuid).get()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/transacoes")
    @GET
    @Operation(description = "API responsável por buscar pagamentos PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno Ok com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API."),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado."),
            @APIResponse(responseCode = "200", description = "OK"),    }
    )
    @Parameter(name = "dataInicio", in = ParameterIn.QUERY,
    description = "Data de início no formatao yyyy-MM-dd")

    @Parameter(name = "dataFim", in = ParameterIn.QUERY,
            description = "Data de fim no formatao yyyy-MM-dd")

    public Response buscarTransacoes(@QueryParam(value = "dataInicio") String dataInicio, @QueryParam(value = "dataFim") String dataFim) throws ParseException {
        return Response.ok(pixService.buscarTransacoes(DATE_FORMAT.parse(dataInicio), DATE_FORMAT.parse(dataFim))).build();
    }
}