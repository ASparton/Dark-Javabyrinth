package vue2D.javafx;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import vue2D.sprites.ISprite;
import vue2D.IVue;
import vue2D.AVue;

/**
*
* @author INFO Professors team
*/
public class Vue extends AVue implements IVue {
    Dessin dessin;
    ILabyrinthe labyrinthe;
    public Scene scene;
    
    /**
     * Initialise la vue.
     * @param labyrinthe la labyrinthe à afficher
     */
    public Vue(ILabyrinthe labyrinthe){
        this.labyrinthe=labyrinthe;
        this.dessin = new Dessin(labyrinthe, this);
        Group root = new Group();
        this.scene = new Scene(root);
        root.getChildren().add(this.dessin);
    }
    
    /**
     * Dessine tout ce qu'il y a à dessiner sur l'écran.
     */
    @Override
    public void dessiner() {
        this.dessin.dessinFond();
        this.dessin.dessinLabyrinthe();
        this.dessin.dessinMurs();
        this.dessin.dessinPersonnages();
        this.dessin.dessinCheminPlusCourt();
    }
    
    /**
     * Ajoute un ISprite à la Vue et démarre l'écoute 
     *      de ses évènements s'il est nécessaire.
     * @param sprite le sprite à ajouter
     * @return true si l'écoute s'est mise en place, false sinon
     */
    @Override
    public boolean add(ISprite sprite) {
        super.add(sprite);
        // si le sprite est controle par le clavier
        if (sprite instanceof EventHandler) {
            System.out.println("registering keylistener");
            // association de l'ecouteur sur le clavier avec le composant graphique principal
            this.scene.setOnKeyPressed((EventHandler) sprite);
        }
        return true;
    }
    
    /**
     * Détermine si les coordonnées en pixel d'un sprite et d'une salle 
     *      sont égales.
     * @param salle la salle
     * @param persoSprite le sprite
     * @return true si les coordonnées sont égales, false sinon
     */
    public static boolean coordonneesEgales(ISalle salle, ISprite persoSprite)
    {
       return Vue.getPositionSallePixel(salle.getX()) == persoSprite.getCoordX()
          && Vue.getPositionSallePixel(salle.getY()) == persoSprite.getCoordY();
    }
    
    /**
     * Retourne la position (X ou Y) de salle donnée en pixel.
     * @param positionSalle la position (X ou Y) de la salle
     * @return la position (X ou Y) de salle donnée en pixel
     */
    private static int getPositionSallePixel(int positionSalle)
    {
        return positionSalle * ISprite.UNITE;
    }
}
