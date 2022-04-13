package vue2D.sprites;

import personnages.IPersonnage;
import javafx.scene.image.Image;

/**
 * Représentation graphique d'un monstre.
 * @author Sparton Alexandre
 */
public class MonstresSprite extends ASprite {
    
    /**
     * Construit la représentation graphique d'un monstre en lui attribuant 
     * un sprite et une vitesse de déplacement.
     * @param monstre le monstre lié à la représentation graphique.
     */
    public MonstresSprite(IPersonnage monstre)
    {
        super(monstre);
        this.sprite = new Image("file:icons/monstre0.gif");
        this.vitesse = 1;
    }
    
}
