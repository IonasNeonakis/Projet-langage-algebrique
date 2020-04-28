import automate.Automate;
import automate.NoeudAutomate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpecificationLexicale {
    public Automate automateEntiers ;
    public Automate automateIdentificateurs ;

    private List<String> listMotCLes = new ArrayList<>(Arrays.asList(
            "program",
            "begin",
            "end",
            "break",
            "while",
            "for",
            "if",
            "do",
            "true",
            "false",
            "from",
            "not",
            "then",
            "else",
            "and",
            "or"
    ));



    public SpecificationLexicale() {
        listMotCLes.add("program");
        NoeudAutomate a1n1 = new NoeudAutomate(false,1);
        NoeudAutomate a1n2 = new NoeudAutomate(true,2);
        NoeudAutomate a1n3 = new NoeudAutomate(true,3);

        a1n1.ajouterTransition("0", a1n2);

        a1n1.ajouterTransition("1",a1n3);
        a1n1.ajouterTransition("2",a1n3);
        a1n1.ajouterTransition("3",a1n3);
        a1n1.ajouterTransition("4",a1n3);
        a1n1.ajouterTransition("5",a1n3);
        a1n1.ajouterTransition("6",a1n3);
        a1n1.ajouterTransition("7",a1n3);
        a1n1.ajouterTransition("8",a1n3);
        a1n1.ajouterTransition("9",a1n3);

        a1n3.ajouterNombres(a1n3);

        this.automateEntiers =  new Automate(3,a1n1);

        this.automateEntiers.ajouterEtat(a1n1,0);
        this.automateEntiers.ajouterEtat(a1n2,1);
        this.automateEntiers.ajouterEtat(a1n3,2);


        NoeudAutomate a2n1 = new NoeudAutomate(false,1);
        NoeudAutomate a2n2 = new NoeudAutomate(true,2);


        a2n1.ajouterAlphabet(a2n2);

        a2n2.ajouterAlphabet(a2n2);
        a2n2.ajouterNombres(a2n2);

        this.automateIdentificateurs = new Automate(2, a2n1);

        this.automateIdentificateurs.ajouterEtat(a2n1,0);
        this.automateIdentificateurs.ajouterEtat(a2n2,1);

    }

    public Automate getAutomateEntiers() {
        return automateEntiers;
    }


    public Automate getAutomateIdentificateurs() {
        return automateIdentificateurs;
    }

    public String remplacer(File algo1) throws FileNotFoundException {
        Scanner scAlgo =  new Scanner(algo1);
        StringBuilder remplacement = new StringBuilder();
        String ligne ;
        while (scAlgo.hasNextLine()){
            ligne = scAlgo.nextLine();
            ligne = ligne.replaceAll(";", " ;");
            String [] mots = ligne.strip().split(" ");
            for (String mot: mots) {
                if (this.automateIdentificateurs.estValide(mot)) {
                    if (this.listMotCLes.contains(mot)) {
                        remplacement.append(" ").append(mot);
                    } else {
                        remplacement.append(" ident");
                    }
                } else if (this.automateEntiers.estValide(mot)) {
                    remplacement.append(" entier");
                } else {
                    if (mot.equals(";"))
                        remplacement.append(mot);
                    else remplacement.append(" ").append(mot);
                }
            }
        }
        return remplacement.toString().trim().replaceAll(" ; ",";");
    }


}
