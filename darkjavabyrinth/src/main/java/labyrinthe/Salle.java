package labyrinthe;

/**
 * Représente une salle ayant des coordonnées et pouvant se comparer.
 * @author Alexandre Sparton
 */
public class Salle implements ISalle {
    
    // Coordonnées de la salle
    private final int x;
    private final int y;
    private boolean estVisite;
    
    /**
     * Construit une salle en lui attribuant des coordonnées.
     * @param x numéro de la colonne
     * @param y numéro de la ligne
     */
    public Salle(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.estVisite = false;
    }
    
    /**
     * @return le numéro de la colonne où se situe la salle
     */
    @Override
    public int getX()
    {
        return x;
    }
    
    /**
     * @return le numéro de la ligne où se situe la salle
     */
    @Override
    public int getY()
    {
        return y;
    }
    
    /**
     * Détermine si une salle est adjacente à une autre.
     * @param autre la salle potentiellement adjacente.
     * @return true si les deux salles sont adjacentes, false sinon.
     */
    @Override
    public boolean estAdjacente(ISalle autre)
    {
        if (autre.getX() == this.x)
        {
            if (autre.getY() == this.y - 1 || autre.getY() == this.y + 1)
                return true;
        }
        else if (autre.getY() == this.y)
        {
            if (autre.getX() == this.x - 1 || autre.getX() == this.x + 1)
                return true;
        }
        
        return false;
    }
    
    /**
     * @return true si la salle a été visité par le héro, false sinon
     */
    @Override
    public boolean estVisite()
    {
        return this.estVisite;
    }
    
    /**
     * Affirme que cette salle a été visitée par le héro.
     */
    @Override
    public void setEstVisite()
    {
        this.estVisite = true;
    }
    
    /**
     * Détermine si cette salle est l'objet comparé est égale.
     * @param autre l'object à comparer
     * @return true si l'objet est une autre salle ayant les mêmes coordonnées, 
     *          false sinon.
     */
    @Override
    public boolean equals(Object autre)
    {
        if (autre == null || autre.getClass() != this.getClass())
            return false;
        
        Salle autreSalle = (Salle)autre;
        return autreSalle.x == this.x && autreSalle.y == this.y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
    
    /**
     * Détermine la direction sur l'axe des abscisses pour aller vers 
     * la salle d'arrivé en partant de la salle de départ.
     * @param depart la salle de départ
     * @param arrive la salle d'arrivé
     * @return 1 si elle se trouve vers la droite, 
     *        -1 si elle se trouve vers la gauche,
     *         0 si elle se trouve sur la même ligne.
     */
    public static int getDirectionX(ISalle depart, ISalle arrive)
    {
        if (arrive.getX() > depart.getX())
            return 1;
        else if (arrive.getX() < depart.getX())
            return -1;
        else
            return 0;
    }
    
    /**
     * Détermine la direction sur l'axe des ordonnées pour aller vers 
     * la salle d'arrivé en partant de la salle de départ.
     * @param depart la salle de départ
     * @param arrive la salle d'arrivé
     * @return 1 si elle se trouve vers le bas, 
     *        -1 si elle se trouve vers le haut,
     *         0 si elle se trouve sur la même colonne.
     */
    public static int getDirectionY(ISalle depart, ISalle arrive)
    {
        if (arrive.getY() > depart.getY())
            return 1;
        else if (arrive.getY() < depart.getY())
            return -1;
        else
            return 0;
    }
    
    /**
     * Détermine quelle est la distance séparant les deux salles données.
     * @param salle1
     * @param salle2
     * @return la distance séparant les deux salles données
     */
    public static int getDistance(ISalle salle1, ISalle salle2)
    {
        return Math.abs(salle2.getX() - salle1.getX()) +
               Math.abs(salle2.getY() - salle1.getY());
    }
}
