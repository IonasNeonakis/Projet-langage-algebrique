import automate.Automate;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
       SpecificationLexicale specificationLexicale = new SpecificationLexicale();
        try {
            String s = specificationLexicale.remplacer(new File("src/programmesAnnexes/Exemple3"));
            //System.out.println(s);
            SpecificationSyntaxique specificationSyntaxique= new SpecificationSyntaxique();
            System.out.println(specificationSyntaxique);
        } catch (FileNotFoundException e) {
            System.out.println("fichier non trouvé");
        }
    }
}
