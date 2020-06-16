package mutante.exception;


/** Excepción que se arroja cuando se recibe un JSON nulo o vacío
 *
 * @author Vir
 */

public class NullValueException extends Exception{

    public NullValueException() {
        super("El JSON no puede ser nulo");
    } 
}
