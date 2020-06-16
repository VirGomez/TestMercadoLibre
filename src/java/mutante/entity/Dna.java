package mutante.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import mutante.exception.DNAException;
import mutante.exception.IsMutantException;

/**
 *
 * @author Vir
 */
@Entity
@Table(name = "Dna")
@NamedQueries({
    @NamedQuery(name = "Dna.findAll", query = "SELECT d FROM Dna d"),
    @NamedQuery(name = "Dna.findById", query = "SELECT d FROM Dna d WHERE d.id = :id"),
    @NamedQuery(name = "Dna.findByIsMutant", query = "SELECT d FROM Dna d WHERE d.isMutant = :isMutant"),
    @NamedQuery(name = "Dna.findMutants", query = "SELECT COUNT(d) FROM Dna d WHERE d.isMutant = true"),
    @NamedQuery(name = "Dna.findHumans", query = "SELECT COUNT(d) FROM Dna d WHERE d.isMutant = false"),
    @NamedQuery(name = "Dna.findByN", query = "SELECT d FROM Dna d WHERE d.n = :n"),
    @NamedQuery(name = "Dna.findByDna", query = "SELECT d FROM Dna d WHERE d.dna = :dna")})
public class Dna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "is_mutant")
    private Boolean isMutant;
    @Column(name = "n")
    private Integer n;
    @Column(name = "dna")
    private String[] dna;

    @Transient
    private boolean[] horizontal;

    @Transient
    private boolean[] vertical;

    @Transient
    private boolean[] diagonalD;

    @Transient
    private boolean[] diagonalA;

    @Transient
    private int total = 0;

    public Dna() {
    }

    public Dna(Integer n, String[] dna) throws DNAException {
        this.n = n;
        setDna(dna);
        this.isMutant = false;
        this.horizontal = new boolean[n * n];
        this.vertical = new boolean[n * n];
        this.diagonalD = new boolean[n * n];
        this.diagonalA = new boolean[n * n];
    }

    /**
     * Solo para Test
     *
     * @param id
     * @deprecated
     */
    @Deprecated
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getN() {
        return n;
    }

    public String[] getDna() {
        return (String[]) dna;
    }

    protected void setDna(String[] dna) throws DNAException {
        if (dna == null) {
            throw new DNAException(DNAException.NULL_VALUE);
        }
        this.dna = validarCaracteresLongitud(dna);
    }

    /**
     * Método que valida que los caracteres recibidos son válidos y los pasa a mayúscula
     * @param dna
     * @return
     * @throws DNAException 
     */
    private String[] validarCaracteresLongitud(String[] dna) throws DNAException {
        for (int i = 0; i < n; i++) {
            if (dna[i].length() != n) {
                throw new DNAException(DNAException.INVALID_FORMAT);
            }
            dna[i] = dna[i].toUpperCase();
            for (int j = 0; j < n; j++) {
                char character = dna[i].charAt(j);
                if (!((character == 'A')
                        || (character == 'C')
                        || (character == 'G')
                        || (character == 'T'))) {
                    throw new DNAException(DNAException.INVALID_CHARACTER);
                }
            }
        }
        return dna;
    }

    public void setIsMutant(Boolean isMutant) {
        this.isMutant = isMutant;
    }

    public Boolean isMutant() {
        return isMutant;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dna other = (Dna) obj;
        if ((this.id!= null) && (other.id != null)) {
            return this.id.equals(other.id);
        }
        return (this.dna.equals(other.dna));
    }

    /**
     * Método que evalua si un sujeto es mutante o no
     *
     * @return
     */
    public Boolean test() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    validateHorizonal(i, j);
                    validateVertical(i, j);
                    validateDiagonalA(i, j);
                    validateDiagonalD(i, j);
                } catch (IsMutantException e) {
                    setIsMutant(true);
                    System.out.println("Mutante");
                    return true;
                }
            }
        }
        System.out.println("No es mutante");
        return false;
    }

    /**
     * Método que evalúa la igualdad de caracteres en sentido horizonal
     *
     * @param i // Fila
     * @param j // Columna
     * @throws IsMutantException
     */
    protected void validateHorizonal(int i, int j) throws IsMutantException {
        int pos = i * n + j;
        //Si ya fue evaluado en ese sentido no lo vuelve a hacer
        if (!horizontal[pos]) {
            horizontal[pos] = true;
            if (j < n - 3) {
                int partial = 1;
                while ((partial < 4) && (dna[i].charAt(j) == (dna[i].charAt(j + partial)))) {
                    // Si el siguiente es igual, suma a la cuenta parcial, y contin
                    horizontal[pos + partial] = true;
                    partial++;
                }
                validateEnd(partial);
            } else {
                //Anulo todos los que no llegan a 4
                for (int k = 1; k < n - j; k++) {
                    horizontal[pos + k] = true;
                }
            }
        }

    }

    /**
     * Método que evalúa la igualdad de caracteres en sentido vertical
     *
     * @param i // Fila
     * @param j // Columna
     * @throws IsMutantException
     */
    protected void validateVertical(int i, int j) throws IsMutantException {
        int pos = i * n + j;
        //Si ya fue evaluado en ese sentido no lo vuelve a hacer
        if (!vertical[pos]) {
            vertical[pos] = true;
            if (i < n - 3) {
                int partial = 1;
                // Si el siguiente es igual, suma a la cuenta parcial, y continua
                while ((partial < 4) && (dna[i].charAt(j) == (dna[i + partial].charAt(j)))) {
                    // Si el siguiente es igual, suma a la cuenta parcial, y continúa
                    vertical[pos + (partial * n)] = true;
                    partial++;
                }
                validateEnd(partial);
            } else {
                //Anulo todos los que no llegan a 4   
                for (int k = 1; k < n - i; k++) {
                    vertical[pos + (k * n)] = true;
                }
            }
        }
    }

    /**
     * Método que evalúa la igualdad de caracteres en sentido Diagonal
     * Descendente
     *
     * @param i // Fila
     * @param j // Columna
     * @throws IsMutantException
     */
    protected void validateDiagonalD(int i, int j) throws IsMutantException {
        int pos = i * n + j;
        //Si ya fue evaluado en ese sentido no lo vuelve a hacer
        if (!diagonalD[pos]) {
            diagonalD[pos] = true;
            if ((i < n - 3) && (j < n - 3)) {
                int partial = 1;
                // Si el siguiente es igual, suma a la cuenta parcial, y continúa
                while ((partial < 4) && (dna[i].charAt(j) == (dna[i + partial].charAt(j + partial)))) {
                    diagonalD[(i + partial) * n + (j + partial)] = true;
                    partial++;
                }
                validateEnd(partial);
            } else {
                //Si no llega al mínimo de 4 caracteres anula todos los que quedan
                int remaining = (i < j) ? n - j : n - i;
                for (int k = 1; k < remaining; k++) {
                    diagonalD[pos + k + (k * n)] = true;
                }
            }
        }
    }

    /**
     * Método que evalúa la igualdad de caracteres en sentido Diagonal
     * ascendente
     *
     * @param i // Fila
     * @param j // Columna
     * @throws IsMutantException
     */
    protected void validateDiagonalA(int i, int j) throws IsMutantException {
        int pos = i * n + j;
        //Si ya fue evaluado en ese sentido no lo vuelve a hacer
        if (!diagonalA[pos]) {
            diagonalA[pos] = true;
            if ((i < n - 3) && (j > 2)) {
                int partial = 1;
                // Si el siguiente es igual, suma a la cuenta parcial, y continúa
                while ((partial < 4) && (dna[i].charAt(j) == (dna[i + partial].charAt(j - partial)))) {
                    diagonalA[(i + partial) * n + (j - partial)] = true;
                    partial++;
                }
                validateEnd(partial);
            } else {
                int remaining = ((n - i) < (j + 1)) ? n - i : j + 1;
                //Si no llega al mínimo de 4 caracteres anula todos los que quedan
                for (int k = 1; k < remaining; k++) {
                    diagonalA[(pos - k) + (k * n)] = true;
                }
            }
        }
    }

    /**
     * Método que evalúa si se alcanzaron los 4 caracteres consecutivos y suma
     * al total. En caso de que el total sea 2 lanza una excepción para cortar
     * la ejecución
     *
     * @param partial
     * @throws IsMutantException
     */
    private void validateEnd(int partial) throws IsMutantException {
        if (partial == 4) {
            total++;
            if (total == 2) {
                throw new IsMutantException();
            }
        }
    }

    /**
     * Retorna el valor de la variable total. Método utilizado solo para
     * testing.
     *
     * @return
     */
    @Deprecated
    public int getTotal() {
        return total;
    }

}
