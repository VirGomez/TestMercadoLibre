package mutante.entity;

import mutante.exception.DNAException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Vir
 */
public class StatsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    

    
      
    @Test
    public void constructorOk() throws DNAException{

        Stats stats = new Stats(10, 5);
  
        Assert.assertEquals(10, stats.getCount_mutant_dna());
        Assert.assertEquals(5, stats.getCount_human_dna());
        Assert.assertEquals(2, stats.getRatio(), 0);
    }
    
    @Test
    public void constructorOkMutantZero() throws DNAException{

        Stats stats = new Stats(0, 5);
  
        Assert.assertEquals(0, stats.getCount_mutant_dna());
        Assert.assertEquals(5, stats.getCount_human_dna());
        Assert.assertEquals(0, stats.getRatio(), 0);
    }
    
    @Test
    public void constructorOkHumanZero() throws DNAException{

        Stats stats = new Stats(10, 0);
  
        Assert.assertEquals(10, stats.getCount_mutant_dna());
        Assert.assertEquals(0, stats.getCount_human_dna());
        Assert.assertEquals(Float.NaN, stats.getRatio(), 0);
    }
    
}
