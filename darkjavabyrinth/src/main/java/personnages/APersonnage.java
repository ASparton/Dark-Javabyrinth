package personnages;

import labyrinthe.ISalle;

/**
 * Classe abstraite permettant de déplacer un personnage.
 * @author Sparton Alexandre
 */
public abstract class APersonnage implements IPersonnage {
    
    protected ISalle salleCourante;

    /**
     * @return la position courante du personnage.
     */
    @Override
    public ISalle getPosition()
    {
        return this.salleCourante;
    }
    
    /**
     * Définit la salle courante du personnage.
     * @param s la nouvelle salle courante du personnage.
     */
    @Override
    public void setPosition( ISalle s)
    {
        this.salleCourante = s;
    }
}
