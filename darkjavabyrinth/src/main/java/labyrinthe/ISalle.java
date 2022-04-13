package labyrinthe;

/**
 * Ce qu'une salle doit être capable d'offrir comme service :
 *  - récupérer sa position
 *  - savoir si elle adjacente à une autre
 *  - gérer le fait qu'elle soit visitée.
 * @author INFO Professors team & Sparton Alexandre
 */
public interface ISalle {
    
    /**
     * @return la coordonnée sur l'axe des abscisses de la salle
     */
    public int getX(); // abcisse
    
    /**
     * @return la coordonnée sur l'axe des ordonnées de la salle
     */
    public int getY();
    
    /**
     * Détermine si cette salle est adjacente à celle donnée.
     * @param autre l'autre salle
     * @return true si cette salle est adjacente à celle donnée, false sinon
     */
    public boolean estAdjacente(ISalle autre);
    
    /**
     * @return true si la salle a déjà été visitée par un personnage, false sinon
     */
    public boolean estVisite();
    
    /**
     * la salle est maintenant considérée comme visitée.
     */
    public void setEstVisite();
}
