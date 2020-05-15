import grammaire.Grammaire;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationSyntaxiqueTest {



    @Test
    void calculerPremiers() {
        SpecificationSyntaxique specificationSyntaxique = new SpecificationSyntaxique();
        specificationSyntaxique.loadGPrim();
        specificationSyntaxique.calculerPremiers();
        Grammaire g = specificationSyntaxique.getG();
        Map<String, Set<String>> prems = g.getPremiers();
        Map<String, Set<String>> vraiPrems = new HashMap<>();

        vraiPrems.put("Expression'",new HashSet<>(Arrays.asList("+","-","ε")));
        vraiPrems.put("Affectation'",new HashSet<>(Arrays.asList("ident", "entier", "(", "true", "false")));
        vraiPrems.put("I",new HashSet<>(Arrays.asList("break", "ident", "for", "while", "if")));
        vraiPrems.put("S",new HashSet<>(Arrays.asList("program")));
        vraiPrems.put("TExpression",new HashSet<>(Arrays.asList("ident", "entier", "(")));
        vraiPrems.put("OpRel",new HashSet<>(Arrays.asList("<=", "!=", "<", "=", ">", ">=")));
        vraiPrems.put("OpPrio",new HashSet<>(Arrays.asList("*", "/")));
        vraiPrems.put("Affectation",new HashSet<>(Arrays.asList("ident")));
        vraiPrems.put("TBExpression'",new HashSet<>(Arrays.asList("ε", "and")));
        vraiPrems.put("GBExpression",new HashSet<>(Arrays.asList("ident", "entier", "true", "false", "(")));
        vraiPrems.put("Condition",new HashSet<>(Arrays.asList("ident", "entier", "(")));
        vraiPrems.put("FBExpression",new HashSet<>(Arrays.asList("not", "ident", "entier", "true", "false", "(")));
        vraiPrems.put("ValBool",new HashSet<>(Arrays.asList("true","false")));
        vraiPrems.put("TExpression'",new HashSet<>(Arrays.asList("*", "/", "ε")));
        vraiPrems.put("For",new HashSet<>(Arrays.asList("for")));
        vraiPrems.put("While",new HashSet<>(Arrays.asList("while")));
        vraiPrems.put("TBExpression",new HashSet<>(Arrays.asList("not", "ident", "entier", "true", "false", "(")));
        vraiPrems.put("Valeur",new HashSet<>(Arrays.asList("ident", "entier")));
        vraiPrems.put("BExpression'",new HashSet<>(Arrays.asList("or", "ε")));
        vraiPrems.put("VarNum",new HashSet<>(Arrays.asList("ident", "entier")));
        vraiPrems.put("VarNum'",new HashSet<>(Arrays.asList("ε", "[")));
        vraiPrems.put("Expression",new HashSet<>(Arrays.asList("ident", "entier", "(")));
        vraiPrems.put("BExpression",new HashSet<>(Arrays.asList("not", "ident", "entier", "true", "false", "(")));
        vraiPrems.put("OpPasPrio",new HashSet<>(Arrays.asList("+", "-")));
        vraiPrems.put("If",new HashSet<>(Arrays.asList("if")));
        vraiPrems.put("LI",new HashSet<>(Arrays.asList("break", "ident", "for", "while", "if")));
        vraiPrems.put("LI'",new HashSet<>(Arrays.asList("ε", ";")));
        vraiPrems.put("FExpression",new HashSet<>(Arrays.asList("ident", "entier", "(")));

        assertEquals(prems,vraiPrems);


    }

    @Test
    void calculerSuivants() {
        SpecificationSyntaxique specificationSyntaxique = new SpecificationSyntaxique();
        specificationSyntaxique.loadGPrim();
        Grammaire g = specificationSyntaxique.getG();
        Map<String, Set<String>> suivants = g.getSuivants();
        Map<String, Set<String>> vraiSuivants = new HashMap<>();


        vraiSuivants.put("Expression'",new HashSet<>(Arrays.asList("<=", "or", ")", "do", "then", "and", "else", "end", ";", "<", "!=", "end.", "]", "=", ">", ">=")));
        vraiSuivants.put("Affectation'",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("I",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("S",new HashSet<>(Arrays.asList("$")));
        vraiSuivants.put("TExpression",new HashSet<>(Arrays.asList("<=", "or", ")", "+", "do", "then", "-", "and", "else", "end", ";", "<","!=", "end.", "]", "=",">", ">=")));
        vraiSuivants.put("OpRel",new HashSet<>(Arrays.asList("ident","entier","(")));
        vraiSuivants.put("OpPrio",new HashSet<>(Arrays.asList("ident","entier","(")));
        vraiSuivants.put("Affectation",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("TBExpression'",new HashSet<>(Arrays.asList("or","do","then")));
        vraiSuivants.put("GBExpression",new HashSet<>(Arrays.asList("or","and","do","then")));
        vraiSuivants.put("Condition",new HashSet<>(Arrays.asList( "or","and","do","then")));
        vraiSuivants.put("FBExpression",new HashSet<>(Arrays.asList("or","and","do","then")));
        vraiSuivants.put("ValBool",new HashSet<>(Arrays.asList("or","and","else","end",";","do","then","end.")));
        vraiSuivants.put("TExpression'",new HashSet<>(Arrays.asList("<=","or",")","+","do","then","-","and","else","end",";","<","!=","end.","]","=",">",">=")));
        vraiSuivants.put("For",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("While",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("TBExpression",new HashSet<>(Arrays.asList("or","do","then")));
        vraiSuivants.put("Valeur",new HashSet<>(Arrays.asList("to","do")));
        vraiSuivants.put("BExpression'",new HashSet<>(Arrays.asList("do","then")));
        vraiSuivants.put("VarNum",new HashSet<>(Arrays.asList("<=","or",")","*","+","do","then","-","/","and","else","end",";","<","!=","end.","]","=",">",">=")));
        vraiSuivants.put("VarNum'",new HashSet<>(Arrays.asList("<=", "or", ")", "*", "+", "do", "then", "-", "/", "and", "else", "end", ";", "<", "!=", "end.", "]", "=", ">", ">="))); // faux
        vraiSuivants.put("Expression",new HashSet<>(Arrays.asList("<=","or",")","do","then","and","else","end",";","<","!=","end.","]","=",">",">=")));
        vraiSuivants.put("BExpression",new HashSet<>(Arrays.asList("do","then")));
        vraiSuivants.put("OpPasPrio",new HashSet<>(Arrays.asList("ident","entier","(")));
        vraiSuivants.put("If",new HashSet<>(Arrays.asList("else","end",";","end.")));
        vraiSuivants.put("LI",new HashSet<>(Arrays.asList("else","end","end.")));
        vraiSuivants.put("LI'",new HashSet<>(Arrays.asList("else","end","end.")));
        vraiSuivants.put("FExpression",new HashSet<>(Arrays.asList("<=", "or", ")", "*", "+", "do", "then", "-", "/", "and", "else", "end", ";", "<", "!=", "end.", "]", "=", ">", ">=")));


        assertEquals(vraiSuivants,suivants);

        for (Map.Entry<String, Set<String>> entry : suivants.entrySet()) {
            assertEquals(entry.getValue(),vraiSuivants.get(entry.getKey()));

            if (vraiSuivants.get(entry.getKey()).equals(entry.getValue()))
                System.out.println(true);
            else {
                System.out.println(false);
                System.out.println(entry.getKey());
            }
        }





    }

    @Test
    void calculerTableProduction() {
        SpecificationSyntaxique specificationSyntaxique = new SpecificationSyntaxique();
        try {
            specificationSyntaxique.lireGrammaire(new File("src/grammaires/GrammaireTest"));
            specificationSyntaxique.calculerPremiers();
            specificationSyntaxique.calculerSuivants();
            specificationSyntaxique.calculerTableProduction();
            Grammaire g =specificationSyntaxique.getG();
            Map<String, Map<String, String>> tableAnalyse = g.getTableAnalyse();
            Map<String,Map<String,String>> vraiTable =  new HashMap<>();

            Map<String,String> colonneA = new HashMap<>();
            colonneA.put("a","a S b b");
            colonneA.put("b","ε");
            colonneA.put("$","ε");
            vraiTable.put("A",colonneA);
            Map<String,String> colonneS = new HashMap<>();

            colonneS.put("a","a A");
            colonneS.put("b","ε");
            colonneS.put("$","ε");
            vraiTable.put("S",colonneS);

            assertEquals(vraiTable,tableAnalyse);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void analyserTexte() {
        SpecificationSyntaxique specificationSyntaxique = new SpecificationSyntaxique();
        specificationSyntaxique.loadGPrim();
        specificationSyntaxique.calculerPremiers();
        specificationSyntaxique.calculerSuivants();
        specificationSyntaxique.calculerTableProduction();
        SpecificationLexicale specificationLexicale = new SpecificationLexicale();
        try {
            String s = specificationLexicale.remplacer(new File("src/programmesAnnexes/Exemple1"));
            assertTrue(specificationSyntaxique.analyserTexte(s));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}