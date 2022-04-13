package personnages;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Random;
import labyrinthe.ISalle;

/**
 * Représente un monstre qui se déplace aléatoirement dans le labyrinthe.
 * @author Sparton Alexandre
 */
public class Monstres extends APersonnage {
    
    /**
     * Construit un monstre en lui donnant une salle de départ.
     * @param salleDepart la salle où le monstre démarrera
     */
    public Monstres(ISalle salleDepart)
    {
        this.salleCourante = salleDepart;
    }
    
    /**
     * Renvoie une salle parmi sallesAccesibles.
     * @param sallesAccessibles les salles accessibles par le personnage.
     * @return La salle choisie parmis les salles accessibles
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles)
    {
        Random randomizer = new Random();
        ArrayList<ISalle> salles = new ArrayList<>(sallesAccessibles);
        return salles.get(randomizer.nextInt(salles.size()));
    }
}
