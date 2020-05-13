import grammaire.Grammaire;

import java.util.Arrays;

public class SpecificationSyntaxique {

    private Grammaire g;


    public SpecificationSyntaxique() {
        this.g = new Grammaire();

        g.ajouterNonTerminal("S","LI","LI'","I","Affectation","Affectation'","While","For","If","ValBool",
                "BExpression","BExpression'","TBExpression","TBExpression'","FBExpression","GBExpression",
                "Expression","Expression'","TExpression","TExpression'","FExpression","OpPrio","OpPasPrio",
                "VarNum","VarNum'","Valeur","Condition","OpRel");

        g.ajouterTerminal("program","ident","begin","end.","<-","while","break","do","end","for","from","to","if","then",
                "else","true","false","or","and","not","*","/","+","-","entier","<=","<",">",">=","=","!=","(",")",";","[","]");


        g.ajouterRegleProduction("S","program ident begin LI end.");
        g.ajouterRegleProduction("LI","I LI'");
        g.ajouterRegleProduction("LI'","; LI");
        g.ajouterRegleProduction("LI'","ε"); // epsilon ici

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

    }

    public Grammaire getG() {
        return g;
    }

    @Override
    public String toString() {
        return g.toString();
    }
}
