import automate.Automate;
import grammaire.Grammaire;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
       SpecificationLexicale specificationLexicale = new SpecificationLexicale();
        try {
            String s = specificationLexicale.remplacer(new File("src/programmesAnnexes/Exemple3"));
            //System.out.println(s);
            SpecificationSyntaxique specificationSyntaxique= new SpecificationSyntaxique();
            Grammaire g = specificationSyntaxique.getG();
            g.calculerTousLesPremiers();
            //g.afficherPremiers();
            g.calculerTousSuivant();
            g.afficherSuivant();
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }
    }
}
