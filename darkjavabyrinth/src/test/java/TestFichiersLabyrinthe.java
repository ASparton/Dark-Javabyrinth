import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

import labyrinthe.LabyrintheGraphe;
import outils.Fichier;

/**
 * Contient les tests à effectuer pour s'assurer qu'un fichier de création
 * de labyrinthe est valide.
 * @author INFO Professors team & Sparton Alexandre
 */
public class TestFichiersLabyrinthe {

    private File[] getFiles(File repertoire) {
        if (!repertoire.isDirectory()) {
            fail("testCoordonneesSalles - les tests ne concernent pas un répertoire");
        }
        File[] fichiers = repertoire.listFiles();
        return fichiers;
    }

    @Test
    public void testCoordonneesSalles() {
        File repertoire = new File("labys/");
        File[] fichiers = getFiles(repertoire);
        for (File file : fichiers)
            assertTrue(Fichier.testCoordonneesSallesFichier(file));
    }


    @Test
    public void testPasDeDoublon() {
        File repertoire = new File("labys/");
        File[] fichiers = getFiles(repertoire);
        for (File file : fichiers)
            assertTrue(Fichier.testPasDeDoublonFichier(file));
    }

    @Test
    public void testChemin() {
        File repertoire = new File("labys/");
        File[] fichiers = getFiles(repertoire);
        for (File fichier : fichiers)
        {
            LabyrintheGraphe laby = new LabyrintheGraphe();
            laby.creerLabyrinthe(fichier.getPath());
            assertTrue(laby.chemin(laby.getEntree(), laby.getSortie()) != null);
        }
    }
}
