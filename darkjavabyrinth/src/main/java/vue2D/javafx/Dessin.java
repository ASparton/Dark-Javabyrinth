package vue2D.javafx;

import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import labyrinthe.LabyrintheGraphe;
import labyrinthe.Salle;
import vue2D.sprites.ISprite;

/**
*
* @author INFO Professors team
*/
public class Dessin extends Canvas {
    
    private final Collection<ISprite> sprites;
    private final ILabyrinthe labyrinthe;
    private final GraphicsContext tampon;
    private Image solImage;
    private Image entreeImage;
    private Image sortieImage;
    private Image murImage;
    private ISalle positionPerso;
    private ArrayList<Object> cheminPlusCourt;
   
    /**
     * Initialise le Dessin, la classe qui nous permet de dessiner sur la vue.
     * @param labyrinthe la labyrinthe à dessiner
     * @param sprites les sprites à dessiner
     */
    public Dessin(ILabyrinthe labyrinthe, Collection<ISprite> sprites)
    {
        this.sprites = sprites;
        this.labyrinthe = labyrinthe;
        setWidth(labyrinthe.getLargeur() * ISprite.UNITE);
        setHeight(labyrinthe.getHauteur() * ISprite.UNITE);
        this.tampon = this.getGraphicsContext2D();
        chargementImages();
        
        this.positionPerso = null;
        this.cheminPlusCourt = new ArrayList<>();
    }
    
    /**
     * Charge toutes les images dont on aura besoin 
     *      pour dessiner les éléments du jeu.
     */
    public final void chargementImages()
    {
        this.solImage = new Image("file:icons/ground.gif");
        this.entreeImage = new Image("file:icons/groundP.gif");
        this.sortieImage = new Image("file:icons/sortie.gif");
        this.murImage = new Image("file:icons/mur0.gif");
    }
    
    /**
     * Dessine l'image de fond.
     */
    public void dessinFond()
    {
        this.tampon.setFill(Color.BLACK);
        this.tampon.fillRect(0, 0, 
                ISprite.UNITE * labyrinthe.getLargeur(),
                ISprite.UNITE * labyrinthe.getHauteur());
    }
    
    /**
     * Dessine les salles du labyrinthe.
     */
    public void dessinLabyrinthe()
    {
        int counter = 0;
        Color ombreSalle = Color.BLACK;
        Image imageSalle;
        for (ISalle salle : this.labyrinthe)
        {
            // Choix de l'image à afficher
            switch (counter) {
                case 0:
                    imageSalle = this.entreeImage;
                    break;
                case 1:
                    imageSalle = this.sortieImage;
                    break;
                default:
                    imageSalle = this.solImage; 
            }
            
            // Affichage des images
            this.tampon.drawImage(imageSalle, 
                                 salle.getX() * ISprite.UNITE,
                                 salle.getY() * ISprite.UNITE,
                                 ISprite.UNITE, ISprite.UNITE);
            
            // Opacité de l'ombre (effet de lumière)
            if (!salle.estVisite())
            {
                double opacite = getOpaciteOmbreSalle(salle);
                this.tampon.setFill(ombreSalle.deriveColor(1.0, 1.0, 1.0, opacite));
                this.tampon.fillRect(salle.getX() * ISprite.UNITE,
                                     salle.getY() * ISprite.UNITE,
                                     ISprite.UNITE, ISprite.UNITE);
            }
            counter++;
        }
    }
    
    /**
     * Détermine l'opacité de l'ombre d'une salle 
     * en fonction de sa distance avec celle du personnage le plus proche.
     * @param salle la salle dont on doit déterminer l'opacité de son ombre
     * @return la l'opacité de l'ombre de la salle
     */
    private double getOpaciteOmbreSalle(ISalle salle)
    {
        double opaciteMin = 1.0;
        ArrayList<ISprite> persos = new ArrayList<>(this.sprites);
        for (ISprite perso : persos)
        {
            double opacite = getOpaciteOmbreSallePerso(salle, perso);
            if (opacite == 0.0)
                return opacite;
            if (opacite < opaciteMin)
                opaciteMin = opacite;
        }
        return opaciteMin;
    }
    
    /**
     * Détermine l'opacité de l'ombre d'une salle 
     * en fonction de sa distance avec celle du personnage donné.
     * @param salle la salle dont on doit déterminer l'opacité de son ombre
     * @return la l'opacité de l'ombre de la salle
     */
    private double getOpaciteOmbreSallePerso(ISalle salle, ISprite perso)
    {
        if (salle.equals(perso.getPosition()))
            return 0.0;
        
        int distance = Salle.getDistance(perso.getPosition(), salle);
        LabyrintheGraphe labyGraphe = (LabyrintheGraphe)this.labyrinthe;
        int distanceCheminPlusCourt = labyGraphe.distanceGraphe(perso.getPosition(), salle);

        if (distanceCheminPlusCourt - 1 > distance)
            return 1.0;
        
        if (distance >= 6)
            return 1.0;
        if (distance <= 5 && distance > 3)
            return 0.6;
        if (distance > 1 && distance <= 3)
            return 0.3;
        return 0.0;
    }
    
    /**
     * Dessine tous les murs du labyrinthe en prenant en compte 
     * l'éclairage et les salles visitées.
     */
    public void dessinMurs()
    {
        for (ISalle mur : this.labyrinthe.getMurs())
        {
            // Affichage des images
            this.tampon.drawImage(this.murImage, 
                                  mur.getX() * ISprite.UNITE,
                                  mur.getY() * ISprite.UNITE,
                                  ISprite.UNITE, ISprite.UNITE);
            
            // Mur non visible tant qu'une salle adjacente n'a pas été visitée
            if (!mur.estVisite())
            {
                this.tampon.setFill(Color.BLACK);
                this.tampon.fillRect(mur.getX() * ISprite.UNITE,
                                     mur.getY() * ISprite.UNITE,
                                     ISprite.UNITE, ISprite.UNITE);
            }
        }
    }
    
    /**
     * Dessine tous les personnages (héro + monstres).
     */
    public void dessinPersonnages()
    {
        this.sprites.forEach(sprite -> {
             sprite.dessiner(this.tampon);
        });
    }
    
    /**
     * Dessine le chemin le plus court entre le héro et la sortie.
     */
    public void dessinCheminPlusCourt()
    {
        ISprite perso = (ISprite)this.sprites.toArray()[0];
        
        if (perso.getPosition() != this.positionPerso) // Si le héro a bougé
        {
            // Calcule du chemin le plus court vers la sortie
            this.positionPerso = perso.getPosition();
            ISalle sortie = this.labyrinthe.getSortie();
            this.cheminPlusCourt = 
                    new ArrayList(this.labyrinthe.chemin(perso.getPosition(), sortie));
        }
        
        // Affichage de ce chemin
        this.cheminPlusCourt.forEach(object -> {
            ISalle salle = (ISalle)object;
            
            double opacite = getOpaciteOmbreSalle(salle);
            if (opacite == 1.0)
                opacite = 0.0;
            else
                opacite = 0.5;
            this.tampon.setFill(Color.YELLOW.deriveColor(1.0, 1.0, 1.0, opacite));
            this.tampon.fillRect(salle.getX() * ISprite.UNITE + ISprite.UNITE / 2,
                    salle.getY() * ISprite.UNITE + ISprite.UNITE / 2,
                    3, 3);
        });
    }
}
