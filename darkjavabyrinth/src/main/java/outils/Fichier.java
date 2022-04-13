package outils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import labyrinthe.Salle;

/**
 *
 * @author INFO Professors team
 */
public class Fichier {
    Scanner sc=null;
    
    public Fichier(String nomFichier){
        try{
	    sc = new Scanner(new File(nomFichier));
	}
	catch(Exception e){ System.out.println(e); }     
    }
    
  // retourne le prochain entier dans le fichier
  // retourne -1 s'il n'y en a pas
    public int lireNombre(){
        if (sc.hasNextInt()){
            return sc.nextInt();
        }
        return -1;
    }
    
    /**
     * Détermine si un fichier de labyrinthe est valide ou non.
     * @param nomFichier le fichier à tester
     * @return true si le fichier est valide, false sinon
     */
    static public boolean testValide(String nomFichier)
    {
        return Fichier.testCoordonneesSallesFichier(new File(nomFichier)) &&
                Fichier.testPasDeDoublonFichier(new File(nomFichier));
    }
    
    /**
     * Test si toutes les coordonnées sont bien 
     *      comprises entre 0 et les dimensions du labyrinthe.
     * @param f le fichier à tester
     * @return true si toutes les coordonnées sont valides, false sinon
     */
    static public boolean testCoordonneesSallesFichier(File f)
    {
        try {
            Scanner fileReader = new Scanner(f);
            int width = fileReader.nextInt();
            int height = fileReader.nextInt();
            
            int x, y;
            while (fileReader.hasNext())
            {
                x = fileReader.nextInt();
                if (x < 0 || x > width)
                    return false;

                y = fileReader.nextInt();
                if (y < 0 || y > height)
                    return false;
            }
            return true;
            
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Test si une coordonnée est présente en double dans le fichier donnée.
     * @param f le fichier à tester
     * @return true si aucune coordonnée n'est présente en double, false sinon
     */
    static public boolean testPasDeDoublonFichier(File f)
    {
        try {
            Scanner fileReader = new Scanner(f);
            // On passe les deux premiers nombres correspondant aux dimensions
            fileReader.nextInt(); fileReader.nextInt();
            HashSet<Salle> coordonnees = new HashSet<>();
            
            while (fileReader.hasNext())
            {
                Salle salle = new Salle(fileReader.nextInt(), fileReader.nextInt());   
                boolean ajoutReussi = coordonnees.add(salle);
                if (!ajoutReussi)
                    return false;
            }
            return true;
            
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
