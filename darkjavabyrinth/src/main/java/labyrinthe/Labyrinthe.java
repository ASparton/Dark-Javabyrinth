package labyrinthe;

import java.util.ArrayList;
import java.util.Collection;

import outils.Fichier;
import personnages.IPersonnage;

/**
 * Représente un labyrinthe sous forme d'une liste de salle.
 * @author INFO Professors team & Sparton Alexandre
 */
public class Labyrinthe extends ArrayList<ISalle> implements ILabyrinthe {

    protected ISalle entree;
    protected ISalle sortie;
    protected int largeur;
    protected int hauteur;
    
    public ArrayList<ISalle> murs;  // Contient tous les murs du labyrinthe

    /**
     * Créer le labyrinthe à partir du fichier donné (supposé valide).
     * @param file le fichier du labyrinthe
     */
    @Override
    public void creerLabyrinthe(String file) {
        Fichier f = new Fichier(file);
        
        // dimensions
        this.largeur = f.lireNombre(); 
        this.hauteur = f.lireNombre(); 
        // entrée et sortie
        this.entree = new Salle(f.lireNombre(), f.lireNombre());
        this.sortie = new Salle(f.lireNombre(), f.lireNombre());
        this.add(entree);
        this.add(sortie);
        
        // le reste des salles
        int x = 0, y = 0;
        while (x > -1 && y > -1)
        {
            x = f.lireNombre();
            y = f.lireNombre();
            this.add(new Salle(x, y));
        }
    }
    
    /**
     * Renvoie toutes les salles accessibles par le personnage donné.
     * @param bob le personnage dont on veut obtenir les salles accessibles
     * @return les salles accessibles par le personnage, null s'il n'y en a aucune
     */
    @Override
    public Collection<ISalle> sallesAccessibles(IPersonnage bob) {
        
        ArrayList<Salle> sallesCardinaux = new ArrayList<>();
        sallesCardinaux.add(new Salle(bob.getPosition().getX() - 1, 
                bob.getPosition().getY()));
        sallesCardinaux.add(new Salle(bob.getPosition().getX() + 1, 
                bob.getPosition().getY()));
        sallesCardinaux.add(new Salle(bob.getPosition().getX(), 
                bob.getPosition().getY() - 1));
        sallesCardinaux.add(new Salle(bob.getPosition().getX(), 
                bob.getPosition().getY() + 1));
        
        ArrayList<ISalle> sallesDisponibles = new ArrayList<>();
        for (Salle salle : sallesCardinaux)
        {
            if (this.contains(salle))
                sallesDisponibles.add(salle);
        }
        
        if (sallesDisponibles.isEmpty())
            return null;
        
        return sallesDisponibles;
    }
    
    /**
     * @return la liste des murs du labyrinthe
     */
    @Override
    public Collection<ISalle> getMurs()
    {
        return this.murs;
    }

    /**
     * @return la salle d'entrée du labyrinthe
     */
    @Override
    public ISalle getEntree() {
        return entree;
    }
    
    /**
     * @return la salle de sortie du labyrinthe
     */
    @Override
    public ISalle getSortie() {
        return sortie;
    }
    
    /**
     * Détermine le chemin le plus court entre la salle de départ et d'arrivé données,
     * utilisé dans LabyrintheGraphe.
     */
    @Override
    public Collection<ISalle> chemin(ISalle depart, ISalle arrive) {
        return null;
    }
    
    /**
     * @return le nombre de salle en largeur du labyrinthe.
     */
    @Override
    public int getLargeur() {
        return largeur;
    }
    
    /**
     * @return le nombre de salle en hauteur du labyrinthe.
     */
    @Override
    public int getHauteur() {
        return hauteur;
    }

}
