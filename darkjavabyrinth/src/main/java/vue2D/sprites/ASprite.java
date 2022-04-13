package vue2D.sprites;

import java.util.Collection;

import personnages.IPersonnage;
import labyrinthe.ISalle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Permet de dessiner un personnage 
 *      (le sprite sera initialisé dans les classes filles)
 * @author Sparton Alexandre
 */
public abstract class ASprite implements ISprite {
    
    // Contient les coordonnées de salle où afficher le sprite
    protected IPersonnage personnage;
    protected Image sprite;
    protected int coordXPix;
    protected int coordYPix;
    
    // Vitesse de déplacement pixel par tour de boucle du sprite
    protected int vitesse;
    protected boolean enDeplacement;
    public ISalle destination;
    
    /**
     * Initialise la représentation graphique avec un personnage.
     * @param personnage le personnage lié à cette représentation graphique
     */
    protected ASprite(IPersonnage personnage)
    {
        this.personnage = personnage;
        this.enDeplacement = false;
        this.coordXPix = this.personnage.getPosition().getX() * ISprite.UNITE;
        this.coordYPix = this.personnage.getPosition().getY() * ISprite.UNITE;
    }
    
    /**
     * Dessine le sprite du personnage au niveau de sa salle courante
     * @param g la fenêtre sur laquelle dessiner le sprite
     */
    @Override
    public void dessiner(GraphicsContext g)
    {
        g.drawImage(this.sprite, this.coordXPix, this.coordYPix, 
                ISprite.UNITE, ISprite.UNITE);
    }
    
    
    
    
    /// Déplacement du personnage pixel par pixel \\\
    
    /**
     * Définit les coordonnées de dessin
     * @param xpix coordonnée de l'axe de l'abscisse en pixel
     * @param ypix coordonnée de l'axe des ordonnées en pixel
     */
    @Override
    public void setCoordonnees(int xpix, int ypix)
    {
        this.coordXPix = xpix;
        this.coordYPix = ypix;
    }
    
    /**
     * @return les coordonnées sur l'axe des abscisses en pixel du sprite
     */
    @Override
    public int getCoordX()
    {
        return this.coordXPix;
    }

    /**
     * @return les coordonnées sur l'axe des ordonnées en pixel du sprite
     */
    @Override
    public int getCoordY()
    {
        return this.coordYPix;
    }
    
    /**
     * @return la vitesse de déplacement du personnage
     */
    @Override
    public int getVitesse()
    {
        return this.vitesse;
    }
    
    /**
     * 
     * @param salleDestination 
     */
    @Override
    public void setSalleDestination(ISalle salleDestination)
    {
        this.destination = salleDestination;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public ISalle getSalleDestination()
    {
        return this.destination;
    }
    
    
    
    
    /// Choix de la salle et son changement côté personnage \\\
    
    /**
     * Fait faire le choix d'une salle au personnage 
     *      parmis les salles accessibles.
     * @param sallesAccessibles les salles où le personnage peut se rendre
     * @return la salle où le personnage décide d'aller
     */
    @Override
    public ISalle faitSonChoix(Collection<ISalle> sallesAccessibles)
    {
        return this.personnage.faitSonChoix(sallesAccessibles);
    }

    /**
     * @return la position courante du personnage.
     */
    @Override
    public ISalle getPosition()
    {
        return this.personnage.getPosition();
    }
    
    /**
     * Définit la salle courante du personnage
     * @param s la salle courante
     */
    @Override
    public void setPosition( ISalle s)
    {
        this.personnage.setPosition(s);
    }
}
