package application;

import java.io.IOException;
import java.util.Collection;
import labyrinthe.ILabyrinthe;
import labyrinthe.ISalle;
import labyrinthe.Salle;
import personnages.IPersonnage;
import vue2D.IVue;
import vue2D.javafx.Vue;
import vue2D.sprites.ISprite;
import vue2D.sprites.HerosSprite;
import vue2D.sprites.MonstresSprite;
import outils.Fichier;
import outils.ExceptionInvalidFile;
import vue2D.sprites.DragonSprite;

/**
 * Gestion du jeu.
 * @author INFO Professors team & Sparton Alexandre
 */
public class Core {
    ISprite heros;
    ILabyrinthe labyrinthe;
    
    /**
     * Initialise le labyrinthe du jeu à partir d'un fichier,
     * si aucun fichier n'est valide, arrête le programme.
     */
    protected void initLabyrinthe() {
        // creation du labyrinthe
        this.labyrinthe = new labyrinthe.LabyrintheGraphe();
        try {
            chargementLaby("labys/level3.txt");
        }
        catch (ExceptionInvalidFile e) {
            try {
                chargementLaby("labys/level7.txt");
            }
            catch (ExceptionInvalidFile e2) {
                System.err.println("Aucun fichier de labyrinthe valide, "
                        + "arrêt du programme.");
                System.exit(1);
            }
        }
    }

    /**
     * Ajoute les sprites et donc personnages nécessaires au jeu à la vue donnée.
     * @param vue la vue qui contiendra les personnages
     */
    protected void initSprites(IVue vue) {
        // Création du héro
        IPersonnage h = new personnages.Heros(this.labyrinthe.getEntree());
        setMursVisites(h.getPosition());
        this.heros = new HerosSprite(h, this.labyrinthe);
        vue.add(this.heros);
        
        // Création des monstres
        for (int i = 0; i < 10; i++)
        {
            IPersonnage monstre = new personnages.Monstres(this.labyrinthe.getSortie());
            vue.add(new MonstresSprite(monstre));
        }
        
        // Création du dragon
        IPersonnage dragon = new personnages.TerribleDragon(labyrinthe.getSortie(), h, this.labyrinthe);
        vue.add(new DragonSprite(dragon));
    }

    /**
     * Gère le déroulement du jeu (déplacement des personnages, collisions, victoire et défaite.
     * @param vue la vue contenant les personnages du jeu
     */
    protected void jeu(IVue vue) {
        // boucle principale
        while (!this.labyrinthe.getSortie().equals(this.heros.getPosition())) {
            
            gestionDeplacement(vue);
            boolean collision = gestionCollisions(vue);
            if (collision)
                break;
            
            temporisation(40);
        }
        System.out.println("Gagné!");
    }
    
    /**
     * Gère le déplacement d'un personnage dans le labyrinthe.
     * @param vue la vue contenant les personnages à déplacer
     */
    private void gestionDeplacement(IVue vue)
    {
        ISalle choix;
        for (ISprite persoSprite : vue)
            {
                // Si le personnage n'est pas en déplacement, il choisi la prochaine salle où il désire aller
                if (persoSprite.getSalleDestination() == null)
                {
                    IPersonnage perso = (IPersonnage)persoSprite;
                    Collection<ISalle> sallesAccessibles = this.labyrinthe.
                            sallesAccessibles(perso);
                    // on demande au personnage de faire son choix de salle
                    choix = perso.faitSonChoix(sallesAccessibles);
                    
                    if (!choix.equals(perso.getPosition()))
                        persoSprite.setSalleDestination(choix);
                }
                else
                {
                    // si le personnage est arrivé à la salle de destination
                    if (Vue.coordonneesEgales(persoSprite.getSalleDestination(), persoSprite))
                    {
                        IPersonnage perso = (IPersonnage)persoSprite;
                        // on attribue maintenant la nouvelle position
                        perso.setPosition(persoSprite.getSalleDestination());
                        setMursVisites(perso.getPosition());
                        // il n'a plus de nouvelle destination pour le moment
                        persoSprite.setSalleDestination(null);
                    }
                    else
                    {
                        deplacerSpriteVersSalleDestination(persoSprite);
                    }
                }
            }
    }
    
    /**
     * Déplace le sprite d'un personnage vers sa salle de destination
     */
    private void deplacerSpriteVersSalleDestination(ISprite persoSprite) {
        int prochaineColonne
                = Salle.getDirectionX(persoSprite.getPosition(),
                        persoSprite.getSalleDestination())
                * persoSprite.getVitesse()
                + persoSprite.getCoordX();

        int prochaineLigne
                = Salle.getDirectionY(persoSprite.getPosition(),
                        persoSprite.getSalleDestination())
                * persoSprite.getVitesse()
                + persoSprite.getCoordY();

        persoSprite.setCoordonnees(prochaineColonne, prochaineLigne);
    }
    
    /**
     * Les murs adjacents à la salle donnée deviennent visités.
     */
    private void setMursVisites(ISalle positionHero) {
        for (ISalle salle : this.labyrinthe.getMurs())
        {
            if (salle.estAdjacente(positionHero) && !salle.estVisite())
                salle.setEstVisite();
        }
    }
    
    /**
     * Gère les collisions entre les personnages dans la labyrinthe.
     * @param vue la vue contenant les personnages
     * @return true si une collision a eu lieu, false sinon
     */
    private boolean gestionCollisions(IVue vue) {
        // detection des collisions
            boolean collision = false;
            ISprite monstre = null;
            for (ISprite p : vue) {
                if (p != heros) {
                    if (p.getPosition().equals(heros.getPosition())) {
                        System.out.println("Collision !!");
                        collision = true;
                        monstre = p;
                    }
                }
            }
            if (collision) {
                vue.remove(monstre);
                vue.remove(heros);
                System.out.println("Perdu !");
                System.out.println("Plus que " + vue.size() + " personnages ...");
                return true;
            }
            else
                return false;
    }
    
    /**
     * Charge un labyrinthe à partir d'un fichier donné (sous forme de chemin)
     * @param fic le fichier
     * @throws ExceptionInvalidFile si le fichier n'est pas valide
     */
    private void chargementLaby(String fic) throws ExceptionInvalidFile {
        try {
            if (Fichier.testValide(fic))
                this.labyrinthe.creerLabyrinthe(fic);
            else
                throw new ExceptionInvalidFile("Le fichier de labyrinthe est "
                        + "invalide.");
        } catch (IOException ex) {
        }
    }
    
    /**
     * Met en pause le thread courant pendant un nombre de millisecondes donné.
     * @param nb le nombre de millisecondes de mise en pause
     */
    protected void temporisation(int nb) {
        try {
            Thread.sleep(nb); // pause de nb millisecondes
        } catch (InterruptedException ie) {
        }
    }
}
