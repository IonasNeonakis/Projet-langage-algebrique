package grammaire;

import java.util.*;

/**
 * Classe qui gère une gramaire
 */
public class Grammaire {
    private Map<String, List<String>> reglesProduction;
    private Set<String> nonTerminaux;
    private Set<String> terminaux;

    private String nonTerminalS;
    private List<String> terminauxS;

    private Map<String, Set<String>> premiers;
    private Map<String, Set<String>> suivants;

    private Map<String,Map<String,String>> tableAnalyse;


    public Grammaire() {
        this.reglesProduction = new HashMap<>();
        this.nonTerminaux = new HashSet<>();
        this.terminaux = new HashSet<>();
        this.premiers = new HashMap<>();
        this.suivants = new HashMap<>();
        this.tableAnalyse = new HashMap<>();
    }

    /**
     * Méthode qui prépare les hashSet des suivants et qui ajoute $ à S
     */
    public void loadSuivants(){
        for (String nT: this.nonTerminaux ) {
            this.suivants.put(nT,new HashSet<>());
        }
        this.suivants.put(this.nonTerminalS, new HashSet<>(Collections.singletonList("$")));
    }

    /**
     * Méthode qui ajoute un terminal à la grammaire
     * @param s est un tableau de terminaux
     */
    public void ajouterTerminal(String... s) {
        this.terminaux.addAll(Arrays.asList(s));
    }

    /**
     * Méthode qui ajoute non terminal à la gramaire
     * @param s est un tableau de non terminaux
     */
    public void ajouterNonTerminal(String... s) {
        this.nonTerminaux.addAll(Arrays.asList(s));
    }

    /**
     * Méthode pour ajouter une régle de production
     * @param nonTerminal est le non terminal à gauche de la fleche
     * @param uneREgle est une production du non terminal
     */
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

    /**
     * Méthode pour définir l'axiome S
     * @param s est le non terminal choisi
     */
    public void definirAxiomeS(String s) {
        this.nonTerminalS = s;
        this.terminauxS = this.reglesProduction.get(s);
    }

    /**
     * Méthode qui affiche une grammaire
     * @return le string de la grammaire
     */
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

    /**
     * Méthode qui calcule tous les premiers d'un non terminal
     * @param fg est tout ce qui se trouve à gauche de la fleche, autrement dit un non terminal
     * @return les premiers calculés
     */
    public Set<String> calculerTousLesPremiers(String fg) {
        List<String> reglesProd = this.reglesProduction.get(fg);
        Set<String> tot = new HashSet<>();
        for (String fd : reglesProd) {
            Set<String> calcs = calculerUnPremier(fg, fd);
            tot.addAll(calcs);
        }
        this.premiers.put(fg, tot);
        return tot;
    }

    /**
     * Méthode qui calcule un premier d'une regle de production
     * @param fg ce qui se trouve à gauche de la fleche
     * @param fd ce qui se trouve à droite de la fleche
     * @return les premiers
     */
    public Set<String> calculerUnPremier(String fg, String fd) {
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
                Set<String> premYi = calculerTousLesPremiers(splited[i]);
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

    /**
     * Méthode qui est utilisée par calculerLePremierDUneProduction afin de calculer les premiers d'une production
     * @param fg est ce qui se trouve a gauche de la fleche
     * @return les premiers
     */
    public Set<String> calculerTousLesPremiersDuneSousProduction(String fg) {
        List<String> reglesProd = this.reglesProduction.get(fg);
        Set<String> tot = new HashSet<>();
        for (String fd : reglesProd) {
            Set<String> calcs = calculerLePremierDUneProduction(fd);
            tot.addAll(calcs);
        }
        return tot;
    }

    /**
     * Méthode qui calcule le premier de cette production uniquement, utilisé pour la table de production
     * @param fd est ce qui se trouve a droite de la fleche
     * @return les premiers
     */
    public Set<String> calculerLePremierDUneProduction(String fd) {
        Set<String> premiersDejaCalcules = new HashSet<>();

        String[] splited = fd.split(" ");
        if (this.terminaux.contains(splited[0]) || this.isEpsilon(splited[0])) {
            premiersDejaCalcules.add(splited[0]);
            return premiersDejaCalcules;
        } else if (this.nonTerminaux.contains(splited[0])) {
            for (int i = 0; i < splited.length; i++) {
                Set<String> premYi = calculerTousLesPremiersDuneSousProduction(splited[i]);
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

    /**
     * Méthode qui calcule tous les premiers
     */
    public void calculerTousLesPremiers() {
        for (Map.Entry<String, List<String>> entry : this.reglesProduction.entrySet()) {
            this.calculerTousLesPremiers(entry.getKey());
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

    /**
     * Méthode qui calcule tous les suivant d'un non terminal
     * @param nT est un non terminal
     * @return les suicants de nt
     */
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

    /**
     * Méthode qui calcule tous les suivants de la grammaire
     */
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

    /**
     * Méthode qui contruit la table d'analyse
     */
    public void construireTable() {
        for (Map.Entry<String, List<String>> entry : this.reglesProduction.entrySet()) {
            String gA = entry.getKey();
            for (String alpha : entry.getValue()) {
                Set<String> tousa = this.calculerLePremierDUneProduction(alpha);
                Map<String, String> t2;
                if (!this.tableAnalyse.containsKey(gA)) {
                    t2 = new HashMap<>();
                } else {
                    t2 = this.tableAnalyse.get(gA);
                }
                for (String a : tousa) {
                    if (!a.equals("ε"))
                        t2.put(a, alpha);
                }
                this.tableAnalyse.put(gA, t2);
                if (tousa.contains("ε")) {
                    for (String b : this.suivants.get(gA)) {
                        t2.put(b, alpha);
                    }
                    this.tableAnalyse.put(gA, t2);
                }
            }
        }
    }



    public void afficherTableProduction(){
        System.out.println(this.tableAnalyse.toString());
    }

    /**
     * Méthode qui analyse la chaine en paramètres
     * @param s est la chaine a analyser
     * @return true si la chaine est dans la grammaire, false sinojn
     */
    public boolean analyseChaine(String s){
        Queue<String> pile = Collections.asLifoQueue(new ArrayDeque<>());
        pile.add("$");
        pile.add(nonTerminalS);

        String[] Ssplitted = s.split(" ");
        int i = 0 ;
        while (i < Ssplitted.length){
            String tetePile = pile.element();
            String mot = Ssplitted[i];
            if (tetePile.equals(mot)){
                i++;
                pile.remove();
            }else{
                String nouvelleTete;
                try {
                    nouvelleTete = this.tableAnalyse.get(tetePile).get(mot); // mettre un try catch qui retourne faux ici
                }catch (Exception e){
                    System.out.println("tete de pile :" +tetePile + " mot :" + mot);
                    //System.out.println(e.getMessage());
                    return false;
                }
                pile.remove();
                if (nouvelleTete == null){
                    return false;
                }
                if (!nouvelleTete.equals("ε")){
                    String[] nouvelleTeteSplit = nouvelleTete.split(" ");
                    for(int j = nouvelleTeteSplit.length -1 ; j >= 0 ; j--){
                        pile.add(nouvelleTeteSplit[j]);
                    }
                }
            }
        }
        return pile.element().equals("$");
    }




}


