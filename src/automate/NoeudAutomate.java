package automate;

/**
 * Class qui représente un état de l'automate
 */
public class NoeudAutomate {
    private boolean estFinal;
    private int numeroEtat;
    private NoeudAutomate[] transition; // sont les transitions possible à partir de cet état

    private static int LONGUEUR_ALPHABET = 26 + 10 + 1 ; // longueur alphabet =  [a - z] + [0-9] + "-"

    /**
     * Création d'un noeud avec un nombre de transition égal à la longueu de l'alphabet : ici 36
     * @param estFinal true si l'état est final
     * @param numeroEtat est le numéro de l'état
     */
    public NoeudAutomate(boolean estFinal, int numeroEtat) {
        this.estFinal = estFinal;
        this.numeroEtat = numeroEtat;
        this.transition = new NoeudAutomate[LONGUEUR_ALPHABET];
    }

    /**
     * Méthode qui ajoute une transition à un noeud
     * @param n est l'état d'arrivé
     * @param s est le ou les caractères de transition
     */
    public void ajouterTransition(NoeudAutomate n, String... s){ // on stock les lettre de 0 a 25 et les chiffres de 26 à 35 et le "-" en 36
        for (String s1: s) {
            if (s1.length() != 1) {
                throw new RuntimeException("S doit être de longueur 1");
            }
            int codeAsci = s1.codePointAt(0);

            if ((codeAsci < 48 || codeAsci > 57) && (codeAsci < 97 || codeAsci > 122)) {
                throw new RuntimeException("la lettre doit être entre [a-z], [0-9] et -");
            }

            if(s1.equals("-")){
                this.transition[36] = n;
            }

            if (codeAsci >= 97) { // si c'est une lettre
                this.transition[codeAsci - 97] = n;
            } else // si c'est un chiffre
                this.transition[codeAsci - 48 + 26] = n;
        }
    }

    /**
     * Ajoute tout l'alphabet français à ce noeud en destination de n
     * @param n est le etat d'arrivée
     */
    public void ajouterAlphabet(NoeudAutomate n){
        this.ajouterTransition(n,"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
    }

    /**
     * Ajoute les nombres de 0 à 9 au noued
     * @param n est l'état d'arrivée
     */
    public void ajouterNombres(NoeudAutomate n){
        this.ajouterTransition(n,"0","1","2","3","4","5","6","7","8","9");

    }

    /**
     * Méthode qui retourne la transition avec le mot passé en paramètres
     * @param s est le mot passé en paramètres
     * @return le nouvel état ou null si la transition n'existe pas
     */
    public NoeudAutomate getTransition(String s){
        if (s.length() != 1){
            throw new RuntimeException("S doit être de longueur 1");
        }

        if (s.equals("-")){
            return this.transition[36];
        }

        int codeAsci = s.codePointAt(0);

        if(( codeAsci < 48 || codeAsci > 57) && ( codeAsci < 97 || codeAsci > 122)){
            throw new RuntimeException("la lettre doit être entre [a-z] et [0-9]");
        }

        if (codeAsci >= 97){ // si c'est une lettre
            return this.transition[codeAsci - 97];
        }else // si c'est un chiffre
            return this.transition[codeAsci - 48 + 26];
    }




    public boolean estFinal() {
        return this.estFinal;
    }


    @Override
    public String toString() {
        return "automate.NoeudAutomate{" +
                "estFinal=" + estFinal +
                ", numeroEtat=" + numeroEtat +
                '}';
    }
}
