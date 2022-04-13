package vue2D.sprites;

import javafx.scene.image.Image;
import personnages.IPersonnage;

/**
 * Représentation graphique du dragon.
 * @author Sparton Alexandre
 */
public class DragonSprite extends ASprite {
    
    /**
     * Construit la représentation graphique d'un monstre en lui attribuant 
     * un sprite et une vitesse de déplacement.
     * @param dragon le monstre lié à la représentation graphique.
     */
    public DragonSprite(IPersonnage dragon)
    {
        super(dragon);
        this.sprite = new Image("file:icons/monstre1.gif");
        this.vitesse = 1;
    }
}
