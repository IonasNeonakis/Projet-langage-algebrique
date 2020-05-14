# Projet-langage-algebrique
 
 Pour lancer le projet il suffit de lancer le main et de suivre les instructions
 
 L'éxécutable est celui-là :  out/artifacts/Projet_langage_algebrique_jar/Projet-langage-algebrique.jar
 
 
#Choix de développement

Analyse Lexicale : 

Pour créer l'automate j'ai d'abord créé une classe NoeudAutomate qui correspont à un état d'un automate (voir le fichier source pour la doc)
Ainsi un automate est composé d'états, chaque état 


Analyse syntaxique :

Ma grammaire est composé de ces attributs : 
```
    private Map<String, List<String>> reglesProduction; // car chaque clé est un nonTerminal et ses valeurs sont ces productions
    private Set<String> nonTerminaux; // Est l'ensemble sans doublons de non termianux
    private Set<String> terminaux; //Est l'ensemble sans doublons de termianux

    private String nonTerminalS; // est l'axiome de départ, il doit faire parti des nonTerminaux 

    private Map<String, Set<String>> premiers; Chaque clé est un nonTerminal, et la valeur est l'ensemble des premiers sans doublons
    private Map<String, Set<String>> suivants;  Chaque clé est un nonTerminal, et la valeur est l'ensemble des suivants sans doublons

    private Map<String,Map<String,String>> tableAnalyse;
```

Les classes Specification Lexicale et Specification Syntaxique sont des façades vers les classes principales