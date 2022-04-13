package personnages;

import java.util.Collection;
import labyrinthe.ISalle;
import labyrinthe.ILabyrinthe;

/**
 * Représente un monstre qui trouve le chemin plus court pour poursuivre le héro.
 * @author Sparton Alexandre
 */
public class TerribleDragon extends APersonnage {
    
    IPersonnage hero;
    ILabyrinthe labyrinthe;
    
    /**
     * Construit un dragon, soit un monstre qui trouvera 
     * le chemin le plus rapide pour aller vers le héro.
     * @param salleDepart la salle de départ du dragon
     * @param hero le héro (pour obtenir la position)
     * @param labyrinthe le labyrinthe (pour obtenir le chemin le plus court)
     */
    public TerribleDragon(ISalle salleDepart, IPersonnage hero, ILabyrinthe labyrinthe)
    {
        this.salleCourante = salleDepart;
        this.hero = hero;
        this.labyrinthe = labyrinthe;
    }
    
    /**
     * Renvoie une salle parmi sallesAccesibles.
     * @param sallesAccessibles les salles accessibles par le personnage.
     * @return La salle choisie parmis les salles accessibles
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles)
    {
        Collection<ISalle> cheminVersHero = 
                this.labyrinthe.chemin(this.salleCourante, hero.getPosition());
        for (ISalle salle : sallesAccessibles)
        {
            if (cheminVersHero.contains(salle))
                return salle;
        }
        return null;
    }
}
