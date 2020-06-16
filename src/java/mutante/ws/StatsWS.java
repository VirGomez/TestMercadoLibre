package mutante.ws;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mutante.DNAFacade;
import mutante.entity.Stats;
import org.json.JSONException;


/**
 *
 * @author Vir
 */

@Stateless
@Path("/stats")
public class StatsWS {

    DNAFacade dNAFacade;
    public StatsWS() {
        this.dNAFacade = new DNAFacade();
    }
    
    public StatsWS(DNAFacade dNAFacade) {
        this.dNAFacade = dNAFacade;
    }

    //Servicio que devuelva un Json con las estadísticas de las
    //verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Stats getStats() throws JSONException {

        int mutants = dNAFacade.countMutant();
        int human = dNAFacade.countHuman();
            return new Stats(mutants, human);
    }
}
