import automate.Automate;
import grammaire.Grammaire;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
       SpecificationLexicale specificationLexicale = new SpecificationLexicale();
        try {
            String s = specificationLexicale.remplacer(new File("src/programmesAnnexes/Exemple6"));
            System.out.println(s);
            SpecificationSyntaxique specificationSyntaxique= new SpecificationSyntaxique();
            Grammaire g = specificationSyntaxique.getG();
            g.calculerTousLesPremiers();
            //g.afficherPremiers();
            g.calculerTousSuivant();
            //g.afficherSuivant();
            g.construireTable();
            //g.afficherTableProduction();
            //g.afficherTableProduction();
            System.out.println(g.analyseChaine(s));
            //g.analyseChaine("s");
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }
    }
}
