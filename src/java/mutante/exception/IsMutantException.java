package mutante.exception;

/** Excepción que se arroja cuando se confirma que un sujeto es mutante
 *
 * @author Vir
 */

public class IsMutantException extends Exception {

    public IsMutantException() {
        super("El sujeto es  un mutante");
    }
}
