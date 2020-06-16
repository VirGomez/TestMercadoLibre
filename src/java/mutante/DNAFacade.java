package mutante;

import mutante.entity.Dna;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vir
 */
public class DNAFacade {

    private EntityManager em;

    /**
     * Método constructor donde se inicializa el EntityManager
     */
    public DNAFacade() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MutantPU");
        this.em = factory.createEntityManager();

    }

    /**
     * Método para persistir un objeto DNA
     */
    public void create(Dna dna) {
        em.persist(dna);

    }

    /**
     * Método para contar cuántos registros de mutantes hay en la BD
     * @return 
     */
    public int countMutant() {
        int count = ((Number) em.createNamedQuery("Dna.findMutants").getSingleResult()).intValue();
        return count;
    }

    /**
     * Método para contar cuántos registros de humanos hay en la BD
     * @return 
     */
    public int countHuman() {
        int count = ((Number) em.createNamedQuery("Dna.findHumans").getSingleResult()).intValue();
        return count;
    }

}
