import grammaire.Grammaire;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe de la partie specification syntaxique qui contient la grammaire G'
 */
public class SpecificationSyntaxique {

    private Grammaire g;

    /**
     * Méthode qui lit une grammaire depuis un fichier
     * @param file est le fichier
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     */
    public void lireGrammaire(File file) throws FileNotFoundException {
        this.g = new Grammaire();
        Scanner scFile = new Scanner(file);
        String ligne;
        String premiereLigne = scFile.nextLine();
        this.enregisterPremiereLigneAxiome(premiereLigne);
        while (scFile.hasNextLine()) {
            ligne = scFile.nextLine();
            enregisterLigne(ligne);
        }
    }

    /**
     * Méthode qui enregistre la premiere ligne de la grammaire
     * @param premiereLigne
     */
    public void enregisterPremiereLigneAxiome(String premiereLigne){
        String NtS= premiereLigne.split("->")[0].trim();
        g.definirAxiomeS(NtS);
        enregisterLigne(premiereLigne);
    }

    /**
     * Méthode qui enregistre une production
     * @param ligne est la ligne a enregister
     */
    public void enregisterLigne(String ligne){
        String[] splitted = ligne.split("->");
        String nonTerminal = splitted[0].trim();
        g.ajouterNonTerminal(nonTerminal);
        String[] productions = splitted[1].split("\\|");
        for (String production : productions) {
            production = production.trim();
            String[] mot = production.split(" ");
            for (String s : mot) {
                if (Character.isUpperCase(s.charAt(0))){ // si c'est une minuscule
                    g.ajouterNonTerminal(s);
                }else {
                    g.ajouterTerminal(s);
                }
            }
            g.ajouterRegleProduction(nonTerminal,production);
        }
    }


    public SpecificationSyntaxique() {
        this.g = new Grammaire();
    }

    /**
     * Méthode qui charge la grammaire G'
     */
    public void loadGPrim(){
        g.ajouterNonTerminal("S","LI","LI'","I","Affectation","Affectation'","While","For","If","ValBool",
                "BExpression","BExpression'","TBExpression","TBExpression'","FBExpression","GBExpression",
                "Expression","Expression'","TExpression","TExpression'","FExpression","OpPrio","OpPasPrio",
                "VarNum","VarNum'","Valeur","Condition","OpRel");

        g.ajouterTerminal("program","ident","begin","end.","<-","while","break","do","end","for","from","to","if","then",
                "else","true","false","or","and","not","*","/","+","-","entier","<=","<",">",">=","=","!=","(",")",";","[","]");


        g.ajouterRegleProduction("S","program ident begin LI end.");
        g.ajouterRegleProduction("LI","I LI'");
        g.ajouterRegleProduction("LI'","; LI");
        g.ajouterRegleProduction("LI'","ε");

        g.ajouterRegleProduction("I","Affectation");
        g.ajouterRegleProduction("I","While");
        g.ajouterRegleProduction("I","For");
        g.ajouterRegleProduction("I","If");
        g.ajouterRegleProduction("I","break");

        g.ajouterRegleProduction("Affectation","ident <- Affectation'");
        g.ajouterRegleProduction("Affectation'","Expression");
        g.ajouterRegleProduction("Affectation'","ValBool");
        g.ajouterRegleProduction("While","while BExpression do LI end");
        g.ajouterRegleProduction("For","for ident from Valeur to Valeur do LI end ");
        g.ajouterRegleProduction("If","if BExpression then LI else LI end");
        g.ajouterRegleProduction("ValBool","true");
        g.ajouterRegleProduction("ValBool","false");

        g.ajouterRegleProduction("BExpression","TBExpression BExpression'");
        g.ajouterRegleProduction("BExpression'","or TBExpression BExpression'");
        g.ajouterRegleProduction("BExpression'","ε");

        g.ajouterRegleProduction("TBExpression","FBExpression TBExpression'");
        g.ajouterRegleProduction("TBExpression'","and FBExpression TBExpression'");
        g.ajouterRegleProduction("TBExpression'","ε");
        g.ajouterRegleProduction("FBExpression","not FBExpression");
        g.ajouterRegleProduction("FBExpression","GBExpression");

        g.ajouterRegleProduction("GBExpression","ValBool");
        g.ajouterRegleProduction("GBExpression","Condition");

        g.ajouterRegleProduction("Expression","TExpression Expression'");
        g.ajouterRegleProduction("Expression'","OpPasPrio TExpression Expression'");
        g.ajouterRegleProduction("Expression'","ε");
        g.ajouterRegleProduction("TExpression","FExpression TExpression'");
        g.ajouterRegleProduction("TExpression'","OpPrio FExpression TExpression'");
        g.ajouterRegleProduction("TExpression'","ε");

        g.ajouterRegleProduction("FExpression","VarNum");
        g.ajouterRegleProduction("FExpression","( Expression )");

        g.ajouterRegleProduction("OpPrio","*");
        g.ajouterRegleProduction("OpPrio","/");
        g.ajouterRegleProduction("OpPasPrio","+");
        g.ajouterRegleProduction("OpPasPrio","-");

        g.ajouterRegleProduction("VarNum","ident VarNum'");
        g.ajouterRegleProduction("VarNum","entier");
        g.ajouterRegleProduction("VarNum'","[ Expression ]");
        g.ajouterRegleProduction("VarNum'","ε");
        g.ajouterRegleProduction("Valeur","ident");
        g.ajouterRegleProduction("Valeur","entier");

        g.ajouterRegleProduction("Condition","Expression OpRel Expression");
        g.ajouterRegleProduction("OpRel","<=");
        g.ajouterRegleProduction("OpRel","<");
        g.ajouterRegleProduction("OpRel",">");
        g.ajouterRegleProduction("OpRel",">=");
        g.ajouterRegleProduction("OpRel","=");
        g.ajouterRegleProduction("OpRel","!=");

        g.definirAxiomeS("S");

        this.calculerPremiers();
        this.calculerSuivants();
        this.calculerTableProduction();

    }

    /**
     * Calcule les premiers
     */
    public void calculerPremiers(){
        this.g.calculerTousLesPremiers();
    }


    /**
     * Calcule les suivants
     */
    public void calculerSuivants(){
        this.g.calculerTousSuivant();
    }

    public void afficherPremiers(){
        this.g.afficherPremiers();
    }

    public void afficherSuivants(){
        this.g.afficherSuivant();
    }

    /**
     * calcule la table de production
     */
    public void calculerTableProduction(){
        this.g.construireTable();
    }

    public void afficherTableProduction(){
        this.g.afficherTableProduction();
    }

    /**
     * Méthode qui analyse le texte à partir de la table de production
     * @param s est la chaine a analyée
     * @return true si le texte est correcte, false sinon
     */
    public boolean analyserTexte(String s){
        g.calculerTousSuivant();
        return g.analyseChaine(s);
    }


    public void afficherGrammaire() {
        System.out.println(g.toString());
    }

    public void afficherNonTerminaux(){
        g.afficherNonTerminaux();
    }

    public void afficherTerminaux() {
        g.afficherTerminaux();
    }

    public Grammaire getG() {
        return this.g;
    }
}
