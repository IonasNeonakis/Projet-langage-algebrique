package grammaire;

import java.util.*;

public class Grammaire {
    private Map<String, List<String>> reglesProduction;
    private Set<String> nonTerminaux;
    private Set<String> terminaux;

    private String nonTerminalS;
    private List<String> terminauxS;

    private Map<String, Set<String>> premiers;
    private Map<String, Set<String>> suivants;

    private String[][] tableAnalyse;




    public Grammaire() {
        this.reglesProduction = new HashMap<>();
        this.nonTerminaux = new HashSet<>();
        this.terminaux = new HashSet<>();
        this.premiers = new HashMap<>();
        this.suivants = new HashMap<>();

    }

    public void loadSuivants(){
        for (String nT: this.nonTerminaux ) {
            this.suivants.put(nT,new HashSet<>());
        }
        this.suivants.put("S", new HashSet<>(Collections.singletonList("$")));
    }

    public void ajouterTerminal(String... s) {
        this.terminaux.addAll(Arrays.asList(s));
    }

    public void ajouterNonTerminal(String... s) {
        this.nonTerminaux.addAll(Arrays.asList(s));
    }

    public void ajouterRegleProduction(String nonTerminal, String uneREgle) {
        List<String> prod = this.reglesProduction.get(nonTerminal);

        if (prod == null)
            prod = new ArrayList<>();
        prod.add(uneREgle);
        this.reglesProduction.put(nonTerminal, prod);
    }

    public void afficherNonTerminaux() {
        System.out.println("Non terminaux : " + this.nonTerminaux.toString());
    }

    public void afficherTerminaux() {
        System.out.println("Terminaux : " + this.terminaux.toString());
    }

    public void definirAxiomeS(String s) {
        this.nonTerminalS = s;
        this.terminauxS = this.reglesProduction.get(s);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : this.reglesProduction.entrySet()) {
            StringBuilder lesProd = new StringBuilder();
            for (String uneprod : entry.getValue()) {
                lesProd.append(uneprod).append(" | ");
            }
            lesProd = new StringBuilder(lesProd.substring(0, lesProd.length() - 3)); // changer le -3 en -2 si besoin
            if (entry.getKey().equals(nonTerminalS)) {
                s.insert(0, entry.getKey() + " -> " + lesProd + "\n");
            } else {
                s.append(entry.getKey()).append(" -> ").append(lesProd).append("\n");
            }
        }
        return s.toString();
    }

    public boolean isEpsilon(String s) {
        return s.equals("ε");
    }


    public Set<String> calculerLesPremiers(String fg) {
        List<String> reglesProd = this.reglesProduction.get(fg);
        Set<String> tot = new HashSet<>();
        for (String fd : reglesProd) {
            Set<String> calcs = premiersRecUnSeul(fg, fd);
            tot.addAll(calcs);
        }
        this.premiers.put(fg, tot);
        return tot;
    }

    public Set<String> premiersRecUnSeul(String fg, String fd) {
        Set<String> premiersDejaCalcules = this.premiers.get(fg);
        if (premiersDejaCalcules == null) {
            premiersDejaCalcules = new HashSet<>();
        }
        String[] splited = fd.split(" ");
        if (this.terminaux.contains(splited[0]) || this.isEpsilon(splited[0])) {
            premiersDejaCalcules.add(splited[0]);
            return premiersDejaCalcules;
        } else if (this.nonTerminaux.contains(splited[0])) {
            for (int i = 0; i < splited.length; i++) {
                Set<String> premYi = calculerLesPremiers(splited[i]);
                premiersDejaCalcules.addAll(premYi);
                if (!premYi.contains("ε")) {
                    return premiersDejaCalcules;
                }
            }
            premiersDejaCalcules.add("ε");
            return premiersDejaCalcules;
        } else {
            System.out.println("Erreur : " + splited[0] + " n'est ni terminal ni non terminal");
            return null;
        }
    }

    public void calculerTousLesPremiers() {
        for (Map.Entry<String, List<String>> entry : this.reglesProduction.entrySet()) {
            this.calculerLesPremiers(entry.getKey());
        }
    }

    public void afficherPremiers() {
        StringBuilder s = new StringBuilder();
        s.append("Voici la liste des premiers :  \n");
        for (Map.Entry<String, Set<String>> entry : this.premiers.entrySet()) {
            s.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append("\n");
        }
        System.out.println(s);
    }


    public Set<String> suivant(String nT) {
        Set<String> suivB = this.suivants.get(nT);
        if (suivB == null){
            suivB = new HashSet<>();
        }
        for (Map.Entry<String,List<String>> entry: this.reglesProduction.entrySet()) {
            for (String droite: entry.getValue()) {
                String[] splited = droite.split(" ");
                for (int i = 0; i < splited.length; i++){
                    String mot = splited[i];
                    if (mot.equals(nT)){
                        if (i+1 < splited.length){ // le cas 2
                            if (this.terminaux.contains(splited[i+1])){
                                suivB.add(splited[i+1]);
                            }else {
                                Set<String> premierSuivant = new HashSet<>(premiers.get(splited[i + 1]));
                                premierSuivant.remove("ε");
                                suivB.addAll(premierSuivant);
                                if (this.premiers.get(splited[i+1]).contains("ε")){
                                    Set<String> suivA = this.suivants.get(entry.getKey());
                                    suivB.addAll(suivA);
                                }
                            }
                        }else{// le cas 3a
                            Set<String> suivA = this.suivants.get(entry.getKey());
                            suivB.addAll(suivA);
                        }

                    }
                }
            }
        }
        return suivB;
    }

    public void calculerTousSuivant(){
        this.loadSuivants();
        boolean doitContinuer = true;
        while (doitContinuer){

            Map<String, Set<String>> avant = new HashMap<>();
            for (Map.Entry<String,Set<String>> entry: this.suivants.entrySet()) {
                Set<String> set = new HashSet<>(entry.getValue());
                avant.put(entry.getKey(),set);
            }
            for (String nT : nonTerminaux) {
                this.suivant(nT);
            }
            if (avant.equals(this.suivants)){
                doitContinuer=false;
            }
        }
    }

    public void afficherSuivant(){
        StringBuilder s = new StringBuilder();
        s.append("Voici la liste des Suivants :  \n");
        for (Map.Entry<String, Set<String>> entry : this.suivants.entrySet()) {
            s.append(entry.getKey()).append(" : ").append(entry.getValue().toString()).append("\n");
        }
        System.out.println(s);
    }


}


