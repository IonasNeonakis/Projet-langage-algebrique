package automate;

public class Automate {
    private int nombreEtats;
    private NoeudAutomate etatInitial;
    private NoeudAutomate[] etats;

    public Automate(int nombreEtats, NoeudAutomate etatInitial) {
        this.nombreEtats = nombreEtats;
        this.etatInitial = etatInitial;
        this.etats = new NoeudAutomate[nombreEtats];
    }

    public void ajouterEtat(NoeudAutomate noeudAutomate, int index){
        this.etats[index] = noeudAutomate;
    }


    public boolean estValide(String mot){
        NoeudAutomate noeudAutomateActuel = this.etatInitial ;
        for (int i = 0; i < mot.length() ; i++) {
            String lettreActuelle = String.valueOf(mot.charAt(i));
            int codeAsci = lettreActuelle.codePointAt(0);
            if(( codeAsci < 48 || codeAsci > 57) && ( codeAsci < 97 || codeAsci > 122)) // si le caractere n'est pas dans le langage alors on retourne faux
                return false;
            noeudAutomateActuel = noeudAutomateActuel.getTransition(lettreActuelle);
            if (noeudAutomateActuel == null)
                return false;
        }
        return noeudAutomateActuel.estFinal();
    }


}
