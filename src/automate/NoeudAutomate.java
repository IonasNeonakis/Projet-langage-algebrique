package automate;

public class NoeudAutomate {
    private boolean estFinal;
    private int numeroEtat;
    private NoeudAutomate[] transition;

    private static int LONGUEUR_ALPHABET = 26 + 10 ; // longueur alphabet =  [a - z] + [0-9]

    public NoeudAutomate(boolean estFinal, int numeroEtat) {
        this.estFinal = estFinal;
        this.numeroEtat = numeroEtat;
        this.transition = new NoeudAutomate[LONGUEUR_ALPHABET];
    }

    public void ajouterTransition(String s, NoeudAutomate n){ // on stock les lettre de 0 a 25 et les chiffres de 26 à 35
        if (s.length() != 1){
            throw new RuntimeException("S doit être de longueur 1");
        }
        int codeAsci = s.codePointAt(0);

        if(( codeAsci < 48 || codeAsci > 57) && ( codeAsci < 97 || codeAsci > 122)){
            throw new RuntimeException("la lettre doit être entre [a-z] et [0-9]");
        }

        if (codeAsci >= 97){ // si c'est une lettre
            this.transition[codeAsci - 97 ] = n;
        }else // si c'est un chiffre
            this.transition[codeAsci - 48 + 26] = n;
    }


    public void ajouterAlphabet(NoeudAutomate n){
        this.ajouterTransition("a",n);
        this.ajouterTransition("b",n);
        this.ajouterTransition("c",n);
        this.ajouterTransition("d",n);
        this.ajouterTransition("e",n);
        this.ajouterTransition("f",n);
        this.ajouterTransition("g",n);
        this.ajouterTransition("h",n);
        this.ajouterTransition("i",n);
        this.ajouterTransition("j",n);
        this.ajouterTransition("k",n);
        this.ajouterTransition("l",n);
        this.ajouterTransition("m",n);
        this.ajouterTransition("n",n);
        this.ajouterTransition("o",n);
        this.ajouterTransition("p",n);
        this.ajouterTransition("q",n);
        this.ajouterTransition("r",n);
        this.ajouterTransition("s",n);
        this.ajouterTransition("t",n);
        this.ajouterTransition("u",n);
        this.ajouterTransition("v",n);
        this.ajouterTransition("w",n);
        this.ajouterTransition("x",n);
        this.ajouterTransition("y",n);
        this.ajouterTransition("z",n);
    }

    public void ajouterNombres(NoeudAutomate n){
        this.ajouterTransition("0",n);
        this.ajouterTransition("1",n);
        this.ajouterTransition("2",n);
        this.ajouterTransition("3",n);
        this.ajouterTransition("4",n);
        this.ajouterTransition("5",n);
        this.ajouterTransition("6",n);
        this.ajouterTransition("7",n);
        this.ajouterTransition("8",n);
        this.ajouterTransition("9",n);

    }

    public NoeudAutomate getTransition(String s){
        if (s.length() != 1){
            throw new RuntimeException("S doit être de longueur 1");
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
