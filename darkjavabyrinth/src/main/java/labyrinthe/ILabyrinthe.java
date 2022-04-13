package labyrinthe;

import java.io.IOException;
import java.util.Collection;
import personnages.IPersonnage;

/**
 * Ce qu'un labyinthe doit offrir comme service.
 * @author INFO Professors team & Sparton Alexandre
 */
public interface ILabyrinthe extends Collection<ISalle> {

    /**
     * Crée le labyrinthe a partir d'un fichier .
     * @param file le fichier qui permet de créer la labyrinthe
     * @throws IOException si une erreur est levée lors de l'utilisation du fichier
     */
    public void creerLabyrinthe(String file) throws IOException;

    /**
     * Renvoie les salles accessibles par le personnage donné.
     * @param heros le personnage
     * @return les salles accessibles par le personnage donné
     */
    public Collection<ISalle> sallesAccessibles(IPersonnage heros);

    /**
     * @return la salle correspondant à l'entrée du labyrinthe
     */
    public ISalle getEntree(); 

    /**
     * @return la salle correspondant à la sortie du labyrinthe
     */
    public ISalle getSortie();
    
    /**
     * @return la liste des murs du labyrinthe
     */
    public Collection<ISalle> getMurs();

    /**
     * Détermine le chemin le plus court entre le départ et l'arrivé.
     * @param depart la salle de départ
     * @param arrive la salle d'arrivé
     * @return le chemin le plus court entre le départ et l'arrivé ou null si aucun chemin n'est existant
     */
    public Collection<ISalle> chemin(ISalle depart, ISalle arrive);

    /**
     * @return le nombre de salle en largeur du labyrinthe.
     */
    public int getLargeur();

    /**
     * @return le nombre de salle en hauteur du labyrinthe.
     */
    public int getHauteur();
}
