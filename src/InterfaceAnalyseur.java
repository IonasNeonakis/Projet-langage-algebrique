import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InterfaceAnalyseur {
    private SpecificationSyntaxique specificationSyntaxique;
    private SpecificationLexicale specificationLexicale;

    public InterfaceAnalyseur(){
        this.specificationSyntaxique = new SpecificationSyntaxique();
        this.specificationLexicale = new SpecificationLexicale();
    }

    /**
     * Méthode pour tapper une grammaire
     */
    public void demanderDeTapperUneGrammaire() {
        System.out.println("Début de l'analyseur syntaxique");
        String choix ;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Tapez 1 pour lire depuis un fichier");
            System.out.println("Tapez 2 pour entrer une grammaire à la main");
            choix = sc.nextLine();
        } while (!(choix.equals("1") || choix.equals("2")));
        if (choix.equals("1")) {
            while (true) {
                System.out.println("Entrez le nom du fichier qui se trouve dans le dossier src/grammaires (déjà present : GrammaireG' )");
                System.out.println("Fichiers déjà presentes : GrammaireG' ");
                choix = sc.nextLine();
                try {
                    File f = new File("src/grammaires/" + choix);
                    this.specificationSyntaxique.lireGrammaire(f);
                } catch (FileNotFoundException e) {
                    System.out.println("fichier non trouvé");
                    continue;
                }
                break;
            }
        }else {
            System.out.println("Entez l'axiome S (Sous la forme S -> a | b | c)");
            choix = sc.nextLine();
            this.specificationSyntaxique.enregisterPremiereLigneAxiome(choix);
            System.out.println("Entre le reste de la grammaire sous forme de regles de productions (A -> S a | b | c R ... )");
            System.out.println("En sepparant chaque terminal et non terminal");
            System.out.println("Tapez sur entrer après chaque production");
            System.out.println("Une fois fini tapez sur 0");
            while (true){
                System.out.println("regle :");
                choix = sc.nextLine();
                if (choix.equals("0"))
                    break;
                else {
                    this.specificationSyntaxique.enregisterLigne(choix);
                }
            }

        }
        while (true) {
            do {
                this.specificationSyntaxique.calculerPremiers();
                this.specificationSyntaxique.calculerSuivants();
                this.specificationSyntaxique.calculerTableProduction();
                System.out.println("Tapez 1 pour afficher les premiers");
                System.out.println("Tapez 2 pour afficher les suivants");
                System.out.println("Tapez 3 pour afficher la table de production");
                System.out.println("Tapez 4 pour quitter");
                choix = sc.nextLine();
            } while (!(choix.equals("1") || choix.equals("2") || choix.equals("3") || choix.equals("4")));
            switch (choix) {
                case "1":
                    this.specificationSyntaxique.afficherPremiers();
                    break;
                case "2":
                    this.specificationSyntaxique.afficherSuivants();
                    break;
                case "3":
                    this.specificationSyntaxique.afficherTableProduction();
                    break;
                case "4":
                    System.exit(0);
            }
        }
    }

    /**
     * Méthode pour analyser un fichier
     */
    public void analyserUnFichier(){
        this.specificationSyntaxique.loadGPrim();
        System.out.println("Début de la vérification");
        String choix ;
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Entez le nom de l'algorithme qui se trouve dans src/programmesAnnexes");
            System.out.println("Fichiers déjà presents : Exemple1, Exemple2, Exemple3, Exemple4, Exemple5, Exemple6");
            System.out.println("Tapez 0 pour sortir");
            choix = sc.nextLine();
            try {
                if (choix.equals("0"))
                    System.exit(0);
                String leProgramme = this.specificationLexicale.remplacer(new File("src/programmesAnnexes/" + choix));
                boolean res = this.specificationSyntaxique.analyserTexte(leProgramme);
                if (res){
                    System.out.println("le program est bien écrit");
                }else {
                    System.out.println("Il y a une erreur dans le programme");
                }

            } catch (FileNotFoundException e) {
                System.out.println("Fichier non trouveé, tapez un autre nom");
            }

        }
    }

    /**
     * Méthode pour lancer l'interface
     */
    public void demarrer(){
        String choix;

        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Tapez 1 pour entrer une grammaire");
            System.out.println("Tapez 2 pour tester un programme");
            choix=sc.nextLine();
            switch (choix){
                case "1" :
                    this.demanderDeTapperUneGrammaire();
                    break;
                case  "2":
                    this.analyserUnFichier();
                    break;
            }
        }while (!(choix.equals("1") || choix.equals("2")));
    }
}

