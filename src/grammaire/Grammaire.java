package grammaire;

import java.util.*;

public class Grammaire {
    Map<String, List<String>> reglesProduction ;
    Set<String> nonTerminaux;
    Set<String> terminaux;
    
    String nonTerminalS;
    List<String> terminauxS;


    public Grammaire(){
        this.reglesProduction =  new HashMap<>();
        this.nonTerminaux = new HashSet<>();
        this.terminaux = new HashSet<>();
    }

    public boolean ajouterTerminal(String... s){
        return this.terminaux.addAll(Arrays.asList(s));
    }

    public boolean ajouterNonTerminal(String... s){
        return this.nonTerminaux.addAll(Arrays.asList(s));
    }

    public void ajouterRegleProduction(String nonTerminal, String uneREgle){
        List<String> prod = this.reglesProduction.get(nonTerminal);

        if (prod == null)
            prod = new ArrayList<>();
        prod.add(uneREgle);
        this.reglesProduction.put(nonTerminal, prod);
    }

    public void afficherNonTerminaux(){
        System.out.println("Non terminaux : " + this.nonTerminaux.toString());
    }

    public void afficherTerminaux(){
        System.out.println("Terminaux : " + this.terminaux.toString());
    }

    public void definirAxiomeS(String s){
        this.nonTerminalS = s;
        this.terminauxS = this.reglesProduction.get(s);
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();

        for ( Map.Entry<String, List<String>> entry : this.reglesProduction.entrySet()) {
            StringBuilder lesProd = new StringBuilder();
            for(String uneprod : entry.getValue()){
                lesProd.append(uneprod).append(" | ");
            }
            lesProd = new StringBuilder(lesProd.substring(0, lesProd.length() - 3)); // changer le -3 en -2 si besoin
            if(entry.getKey().equals(nonTerminalS)){
                s.insert(0, entry.getKey() + " -> " + lesProd + "\n");
            }else{
                s.append(entry.getKey()).append(" -> ").append(lesProd).append("\n");
            }
        }
        return s.toString().toString();
    }
}
