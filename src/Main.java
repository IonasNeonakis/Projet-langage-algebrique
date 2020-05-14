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
            specificationSyntaxique.calculerPremiers();
            specificationSyntaxique.calculerSuivants();
            specificationSyntaxique.afficherPremiers();
            specificationSyntaxique.afficherSuivants();
            specificationSyntaxique.calculerTableProduction();
            specificationSyntaxique.afficherTableProduction();
            //System.out.println(specificationSyntaxique.analyserTexte(s));
            //g.analyseChaine("s");
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }
    }
}
