package vue2D.sprites;

import javafx.scene.canvas.GraphicsContext;
import personnages.IPersonnage;
import labyrinthe.ISalle;

/**
 * Tout ce qu'un sprite doit fournri comme services.
 * @author INFO Professors team & Sparton Alexandre
 */
public interface ISprite extends IPersonnage {
        
        /**
         * Dessine le sprite sur le graphics context donné.
         * @param g le graphics context
         */
	public void dessiner(GraphicsContext g);
        
        /**
         * @return les coordonnées sur l'axe des abscisses en pixel du sprite
         */
        public int getCoordX();
        
        /**
         * @return les coordonnées sur l'axe des abscisses en pixel du sprite
         */
        public int getCoordY();
        
        /**
         * Attribue des coordonnées en pixel au sprite.
         * @param xpix la position en pixel sur l'axe des abscisses
         * @param ypix la position en pixel sur l'axe des ordonnées
         */
	public void setCoordonnees(int xpix, int ypix);
        
        /**
         * @return la vitesse de déplacement du personnage
         */
        public int getVitesse();
        
        /**
         * @return la salle où le sprite est en train de se diriger
         */
        public ISalle getSalleDestination();
        
        /**
         * Attribue une salle vers laquelle le sprite va se diriger.
         * @param salleDestination la salle vers laquelle le sprite va se diriger
         */
        public void setSalleDestination(ISalle salleDestination);
        
        // Une case = 15 pixels
        public static int UNITE = 15;
}
