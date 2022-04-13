package vue2D.sprites;

import personnages.Heros;
import personnages.IPersonnage;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;

import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Permet de dessiner le héro que le joueur va contrôler.
 * @author Sparton Alexandre
 */
public class HerosSprite extends ASprite implements EventHandler<KeyEvent> {
    
    private final ILabyrinthe labyrinthe;
    
    /**
     * Construit la représentation graphique du héro en lui attribuant 
     * un sprite et une vitesse de déplacement.
     * @param persoHero le hero lié
     * @param labyrinthe le labyrinthe courant
     */
    public HerosSprite(IPersonnage persoHero, ILabyrinthe labyrinthe)
    {
        super(persoHero);
        this.labyrinthe = labyrinthe;
        this.sprite = new Image("file:icons/link/LinkRunShieldD1.gif");
        this.vitesse = 3;
    }
    
    /**
     * Détermine la salle où le héro va se déplacer 
     *      en fonction de la touche pressée
     * Reste dans la même salle si l'endroit désiré est inaccessible.
     * @param event la touche pressée.
     */
    @Override
    public void handle(KeyEvent event)
    {
        if (this.destination != null)
            return;
        
        int xSalleIndiquee = this.personnage.getPosition().getX();
        int ySalleIndiquee = this.personnage.getPosition().getY();
        switch (event.getCode())
        {
            case LEFT:
                xSalleIndiquee--;
                break;
            case RIGHT:
                xSalleIndiquee++;
                break;
            case UP:
                ySalleIndiquee--;
                break;
            case DOWN: 
                ySalleIndiquee++;
                break;
            default:
                System.out.println("Touche non valide.");
        }
        
        // On récupère la salle indiquée si elle fait partie du labyrinthe
        ISalle salleIndiquee = null;
        for (ISalle salle : this.labyrinthe)
        {
            if (salle.getX() == xSalleIndiquee && salle.getY() == ySalleIndiquee)
                salleIndiquee = salle;
        }
        Heros hero = (Heros)this.personnage;
        
        /* On l'attribue à salle choisie si elle fait partie 
           des salles du labyrinthe (qu'elle n'est pas un mur)*/
        if (this.labyrinthe.sallesAccessibles(hero).contains(salleIndiquee))
            hero.salleChoisie = salleIndiquee;
        else
            hero.salleChoisie = hero.getPosition();
    }
}
