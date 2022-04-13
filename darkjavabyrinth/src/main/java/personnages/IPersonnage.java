package personnages;

import java.util.Collection;
import labyrinthe.ISalle;

/**
 * Représente 
 * @author INFO Professors team
 */
public interface IPersonnage {
    
    /**
     * Renvoie une salle parmi sallesAccesibles.
     * @param sallesAccessibles les salles accessibles par le personnage
     * @return une salle parmi sallesAccesibles
     */
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles);

    /**
     * @return la position courante du personnage
     */
    public ISalle getPosition();
    
    /**
     * Définit la position courante du personnage.
     * @param salle la nouvelle salle
     */
    public void setPosition(ISalle salle);
}
