package outils;

/**
 * Levée lorsqu'un fichier sensé représenter un labyrinthe s'avère invalide.
 * @author Alexandre Sparton
 */
public class ExceptionInvalidFile extends Exception {

    public ExceptionInvalidFile(String message) {
        super(message);
    }
    
}
