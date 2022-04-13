package labyrinthe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.util.Pair;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import outils.Fichier;

/**
 * Représente un labyrinthe avec une représentation sous forme de graphe.
 * @author Sparton Alexandre
 */
public class LabyrintheGraphe extends Labyrinthe implements ILabyrinthe {
    
    private DefaultDirectedGraph<ISalle, DefaultEdge> labyGraphe;
    
    /* Contient les distances pour aller d'une salle à une autre
       (évite de recalculer la même distance plusieurs fois) */
        private HashMap<Pair<ISalle, ISalle>, Integer> distances;
    
    /**
     * Créer le labyrinthe à partir du fichier donné (supposé valide).
     * @param file le fichier du labyrinthe
     */
    @Override
    public void creerLabyrinthe(String file) {
        // le graphe
        this.labyGraphe = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.distances = new HashMap<>();
        
        Fichier f = new Fichier(file);
        
        // dimensions
        this.largeur = f.lireNombre(); 
        this.hauteur = f.lireNombre(); 
        // entrée et sortie
        this.entree = new Salle(f.lireNombre(), f.lireNombre());
        this.sortie = new Salle(f.lireNombre(), f.lireNombre());
        this.add(entree);
        this.labyGraphe.addVertex(entree);
        this.add(sortie);
        this.labyGraphe.addVertex(sortie);
        
        // le reste des salles
        int x = 0, y = 0;
        while (x > -1 && y > -1)
        {
            x = f.lireNombre();
            y = f.lireNombre();
            Salle salle = new Salle(x, y);
            this.add(salle);
            this.labyGraphe.addVertex(salle);
        }
        ajoutLiaisons();
        ajoutMurs();
    }
    
    /**
     * Ajoute les liaisons entre les sommets du graphe.
     */
    private void ajoutLiaisons() {
        this.forEach(salle1 -> {
            for (ISalle salle2 : this)
            {
                // Il y a liaison lorsque les salles sont adjacentes l'une à l'autre
                if (salle1 != salle2 && salle1.estAdjacente(salle2))
                    this.labyGraphe.addEdge(salle1, salle2);
            }
        });
    }
    
    /**
     * Ajoute les murs au labyrinthe.
     */
    private void ajoutMurs() {
        this.murs = new ArrayList<>();
        // Partout où il n'y a pas de salles, on créer un mur
        for (int x = 0; x < this.largeur; x++)
        {
            for (int y = 0; y < this.hauteur; y++)
            {
                Salle salle = new Salle(x, y);
                if (!this.contains(salle))
                    this.murs.add(salle);
            }
        }
    }
    
    /**
     * Calcule le chemin le plus court depuis la salle de départ jusqu'à la salle d'arrivé.
     * @param depart la salle de départ
     * @param arrive la salle de d'arrivé
     * @return le chemin le plus court entre le départ et l'arrivé ou null si aucun chemin n'est existant
     */
    @Override
    public Collection<ISalle> chemin(ISalle depart, ISalle arrive) {
        DijkstraShortestPath<ISalle, DefaultEdge> dsp = 
                new DijkstraShortestPath(this.labyGraphe);
        GraphPath chemin = dsp.getPath(depart, arrive);
        if (chemin != null)
            return chemin.getVertexList();
        return null;
    }
    
    /**
     * Calcule la distance par le chemin le plus court entre deux salles.
     *
     * @param depart salle de départ
     * @param arrive salle de arrivé
     * @return la distance du chemin le plus court entre la salle de départ et
     * d'arrivé, -1 si le chemin est inexistant
     */
    public int distanceGraphe(ISalle depart, ISalle arrive) {
        // On vérifie si la distance entre ces deux salles a déjà été calculée
        Pair<ISalle, ISalle> sallesChemin = new Pair<>(depart, arrive);
        if (this.distances.containsKey(sallesChemin))
            return this.distances.get(sallesChemin);
        else
        {
            // Sinon on l'ajoute au dictionnaire
            Collection<ISalle> chemin = this.chemin(depart, arrive);
            if (chemin != null)
            {
                this.distances.put(sallesChemin, chemin.size());
                return chemin.size();
            } else
                return -1;
        }
    }
}
