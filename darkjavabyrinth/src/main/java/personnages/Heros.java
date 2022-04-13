package personnages;

import java.util.Collection;
import labyrinthe.ISalle;

/**
 * Permet de déplacer et faire choisir une salle à un personnage.
 * @author Alexandre Sparton
 */
public class Heros extends APersonnage {
    
    public ISalle salleChoisie;
    
    /**
     * Construit un héro en lui attribuant une salle de départ.
     * @param salleDepart la salle de départ du héro
     */
    public Heros(ISalle salleDepart)
    {
        this.salleChoisie = salleDepart;
        this.setPosition(salleDepart);
    }
    
    /**
     * Renvoie une salle parmi sallesAccesibles.
     * @param sallesAccessibles les salles accessibles par le personnage.
     * @return La salle choisie parmis les salles accessibles
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles)
    {
        return this.salleChoisie;
    }
    
    /**
     * Définit la salle courante du héro & indique que la salle est visitée.
     * @param salle la nouvelle salle courante du personnage.
     */
    @Override
    public void setPosition(ISalle salle)
    {
        if (!salle.estVisite())
            salle.setEstVisite();
        this.salleCourante = salle;
    }
}
