package mutante.ws;

import mutante.exception.NullValueException;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mutante.DNAFacade;
import mutante.entity.Dna;
import mutante.exception.DNAException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Vir
 */
@Stateless
@Path("/mutant")
public class MutantWS {

    DNAFacade dNAFacade;
    
    public MutantWS() {
        this.dNAFacade = new DNAFacade();
    }

    public MutantWS(DNAFacade dNAFacade) {
        this.dNAFacade = dNAFacade;
    }

    /**
     * Método para saber que se levantó bien la API y que explica los métodos disponibles
     * @return 
     */
    @GET
    @Produces("text/html")
    public String getGreeting() {
        return "<html><body><h1>Exemen Mercado Libre - Mutantes</h1>\n"
                + "<p>Esta API REST consta de dos servicios:&nbsp;</p>\n"
                + "<h2 style=\"color: #2e6c80;\">/mutant</h2>\n"
                + "<p>El metodo <b>POST</b> recibe un JSON con el formato: {\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}. Permite determinar si el DNA de un sujeto corresponde a un mutante o a un humano, y lo guarda en la Base de datos.&nbsp;</p>\n"
                + "<h2 style=\"color: #2e6c80;\">/stats</h2>\n"
                + "<p>El metodo <b>GET</b> devuelve las estadisticas de las verificaciones de DNA en un JSON con el formato:{\"count_mutant_dna\":40, \"count_human_dna\":100: \"ratio\":0.4} &nbsp;</p></body></html>";
    }

    /**
     * POST método para testear a un sujeto
     *
     * @param input JSON con el dna del sujero
     * @return an HTTP response.
     */
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces("text/html")
    public Response isMutant(String input) {
        try {

            JSONArray jsonArray = validateInput(input);
            Dna dna = getDNA(jsonArray);
            dna.test();
            dNAFacade.create(dna);
            if (dna.isMutant()) {
                return Response.status(200).entity("Es un mutante").build();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.status(403).entity("Hubo un error en la ejecución:" + e.getMessage()).build();
        }
        return Response.status(403).entity("No es un mutante").build();
    }

    /**
     * Valida que la entrada no sea nula y sea un JSON válido
     *
     * @param input
     * @return
     * @throws Exception
     */
    JSONArray validateInput(String input) throws Exception {
        if ((input == null) || (input.trim().isEmpty())) {
            throw new NullValueException();
        }
        JSONObject json = new JSONObject(input);
        JSONArray jsonArray = json.getJSONArray("dna");
        return jsonArray;
    }

    /**
     * Obtiene un objeto DNA a partir del JsonArray y el valor de n para no
     * tener que calcularlo nuevamente
     *
     * @param jsonArray
     * @return
     * @throws NullValueException
     */
    private Dna getDNA(JSONArray jsonArray) throws DNAException {
        int n = jsonArray.length();
        String[] dna = new String[n];
        for (int i = 0; i < n; i++) {
            dna[i] = jsonArray.optString(i);
        }
        return new Dna(n, dna);
    }
}
