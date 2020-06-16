package mutante.ws;

import mutante.DNAFacade;
import mutante.exception.NullValueException;
import org.json.JSONException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.easymock.*;

public class MutantWSTest {

    static final int FORBIDDEN = 403;
    static final int OK = 200;

    DNAFacade dNAFacadeMock = EasyMock.createMock(DNAFacade.class);
            
    MutantWS mutantWS = new MutantWS(dNAFacadeMock);

    @Test
    public void inputNull() {
        assertEquals(FORBIDDEN, mutantWS.isMutant(null).getStatus());
    }

    @Test
    public void inputVacio() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("     ").getStatus());
    }

    @Test
    public void inputInvalido() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("{     ").getStatus());
    }
    
    
    @Test
    public void inputInvalido2() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("{}").getStatus());
    }
    
    @Test
    public void inputInvalido3() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("{\"algo\":\"\"}").getStatus());
    }
    
    @Test
    public void inputInvalido4() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("{\"dna\":\"1111111\"}").getStatus());
    }
    @Test
    public void inputOkMutant() {
        assertEquals(OK, mutantWS.isMutant("{"
                + "\"dna\":["
                + "\"ATGCGA\","
                + "\"CAGTGC\","
                + "\"TTATGT\","
                + "\"AGAAGG\","
                + "\"CCCCTA\","
                + "\"TCACTG\"]"
                + "}").getStatus());
    }
    
    @Test
    public void inputNotMutant() {
        assertEquals(FORBIDDEN, mutantWS.isMutant("{"
                + "\"dna\":[\"CTGCTA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CACCTA\",\"TCACTG\"]"
                + "}").getStatus());
    }
    
    @Test(expected = NullValueException.class)
    public void validateInputNull() throws Exception {
        mutantWS.validateInput(null);
    }
    
    @Test(expected = NullValueException.class)
    public void validateInputVacia() throws Exception {
        mutantWS.validateInput("      ");
    }
    
    @Test(expected = JSONException.class)
    public void validateInputInvalida() throws Exception {
          mutantWS.validateInput("{}");
    }
    
    @Test(expected = JSONException.class)
    public void validateInputInvalida2() throws Exception {
          mutantWS.validateInput("{\"algo\":\"\"}");
    }
    
}
