package mutante.exception;

/** Posibles excepciones dentro del objeto DNA
 *
 * @author Vir
 */

public class DNAException extends Exception {

    public final static String INVALID_FORMAT = "El DNA tiene un formato inválido";
    public final static String INVALID_CHARACTER = "El DNA tiene un caracter inválido";
    public final static String NULL_VALUE = "El valor no puede ser nulo";

    public DNAException(String cause) {
        super(cause);
    }
}
