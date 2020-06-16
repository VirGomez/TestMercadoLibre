package mutante.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase utilizada para devolver las estadísticas en forma tal que se reciba como JSON
 * @author Vir
 */
@XmlRootElement
public class Stats {
    
	private final int count_mutant_dna;
	private final int count_human_dna;
        private final float ratio;
        
        public Stats(int count_mutant_dna, int count_human_dna) {
            this.count_mutant_dna = count_mutant_dna;
            this.count_human_dna = count_human_dna;
            if (count_human_dna == 0){
                this.ratio = Float.NaN;
            }else{
                this.ratio = 1.0f * count_mutant_dna/count_human_dna;
            }
        }
	@XmlAttribute
        public int getCount_mutant_dna() {
            return count_mutant_dna;
        }
	@XmlAttribute
        public int getCount_human_dna() {
            return count_human_dna;
        }
	@XmlAttribute
        public float getRatio() {
            return ratio;
        }
        
        
    } 