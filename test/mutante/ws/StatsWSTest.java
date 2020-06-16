package mutante.ws;

import mutante.DNAFacade;
import org.json.JSONException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.easymock.*;

public class StatsWSTest {

    DNAFacade dNAFacadeMock = EasyMock.createMock(DNAFacade.class);
            
    StatsWS statsWS = new StatsWS(dNAFacadeMock);

    @Test
    public void getStatsOk() throws JSONException {
        EasyMock.expect(dNAFacadeMock.countMutant()).andReturn(10).anyTimes();
        EasyMock.expect(dNAFacadeMock.countHuman()).andReturn(5).anyTimes();
        EasyMock.replay(dNAFacadeMock);

        assertEquals(statsWS.getStats().getCount_mutant_dna(), 10);
        assertEquals(statsWS.getStats().getCount_human_dna(), 5);
        assertEquals(statsWS.getStats().getRatio(), 2, 0);

    }

}
