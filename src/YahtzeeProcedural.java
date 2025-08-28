/*
 * Auteur : Zakaria Eddini
 * Date   : 20.08.2025
 * But    : Jeu Yahtzee en Java
 */
import java.util.Scanner;

public class YahtzeeProcedural {

    // Lance un dé
    public static int lancerDe() {
        return (int) (Math.random() * 6 + 1);
    }

    // Lance tous les dés
    public static int[] lancerTousLesDes(int nombreDeDes) {
        int[] resultat = new int[nombreDeDes];
        for (int i = 0; i < nombreDeDes; i++) {
            resultat[i] = lancerDe();
        }
        return resultat;
    }

    // Affiche les résultats des dés
    public static void afficherDes(int[] resultatDes) {
        for (int i = 0; i < resultatDes.length; i++) {
            System.out.print((i + 1) + "[" + resultatDes[i] + "] ");
        }
        System.out.println();
    }

    // l'entrée utilisateur
    public static String lireEntreeUtilisateur() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez les numéros des dés à relancer (0 pour garder) : ");
        return scanner.nextLine();
    }

    // Relance les dés sélectionnés
    public static void relancerDes(int[] resultatDes, String entree) {
        String[] numeros = entree.split(" ");
        for (String choix : numeros) {
            if (!choix.equals("0")) {
                int index = Integer.parseInt(choix) - 1;
                if (index >= 0 && index < resultatDes.length) {
                    resultatDes[index] = lancerDe();
                } else {
                    System.out.println("Numéro de dé invalide : " + (index + 1));
                }
            }
        }
    }

    // Compte les occurrences de chaque face
    public static int[] compterOccurrences(int[] des) {
        int[] occurrences = new int[6];
        for (int de : des) {
            occurrences[de - 1]++;
        }
        return occurrences;
    }

    // Vérifie si c'est un full house
    public static boolean estFullHouse(int[] occurrences) {
        boolean aTrois = false;
        boolean aDeux = false;
        for (int compte : occurrences) {
            if (compte == 3)
                aTrois = true;
            if (compte == 2)
                aDeux = true;
        }
        return aTrois && aDeux;
    }

    // Vérifie si c'est une petite suite
    public static boolean estPetiteSuite(int[] occurrences) {
        return contientSuite(occurrences, 4);
    }

    // Vérifie si c'est une grande suite
    public static boolean estGrandeSuite(int[] occurrences) {
        return contientSuite(occurrences, 5);
    }

    // Vérifie s'il y a deux paires différentes
    public static boolean estDoublePaire(int[] occurrences) {
        int nombreDePaires = 0;
        for (int compte : occurrences) {
            if (compte == 2) {
                nombreDePaires++;
            }
        }
        return nombreDePaires >= 2;
    }

    // Détecte une suite de valeurs consécutives
    public static boolean contientSuite(int[] occurrences, int longueur) {
        int compte = 0;
        for (int occurrence : occurrences) {
            if (occurrence > 0) {
                compte++;
                if (compte >= longueur)
                    return true;
            } else {
                compte = 0;
            }
        }
        return false;
    }

    // Afficher uniquement les combinaisons disponibles avec leurs scores
    public static void afficherCombinaisonsDisponibles(int[] des, boolean[] combinaisonsUtilisees) {
        String[] lesCombinaisons = {"Paire", "Double Paire", "Brelan", "Carré", "Full House", "Petite Suite", "Grande Suite", "Yahtzee"};

        System.out.println("\n--- COMBINAISONS DISPONIBLES ---");
        int index = 1;
        for (int i = 0; i < lesCombinaisons.length; i++) {
            if (!combinaisonsUtilisees[i]) {
                int score = calculerScoreCombinaison(des, i);
                System.out.println(index + ". " + lesCombinaisons[i] + " : " + score + " pts");
                index++;
            }
        }

        if (index == 1) {
            System.out.println("Toutes les combinaisons ont été utilisées ");
        }else{
            System.out.println("Vous devez choisir une combinaison disponible ");
        }
    }

    // Calcule le score pour une combinaison spécifique
    public static int calculerScoreCombinaison(int[] des, int typeCombinaison) {
        int[] occ = compterOccurrences(des);

        switch (typeCombinaison) {
            case 0: // Paire
                for (int compte : occ) {
                    if (compte >= 2) return 5;
                }
                return 0;

            case 1:
                return estDoublePaire(occ) ? 10 : 0;

            case 2: // Brelan
                for (int i = 0; i < occ.length; i++) {
                    if (occ[i] >= 3) {
                        return (i + 1) * 3;
                    }
                }
                return 0;

            case 3: // Carré
                for (int i = 0; i < occ.length; i++) {
                    if (occ[i] >= 4) {
                        return (i + 1) * 4;
                    }
                }
                return 0;

            case 4:
                return estFullHouse(occ) ? 25 : 0;

            case 5:
                return estPetiteSuite(occ) ? 30 : 0;

            case 6:
                return estGrandeSuite(occ) ? 40 : 0;

            case 7: // Yahtzee
                for (int compte : occ) {
                    if (compte == 5) return 50;
                }
                return 0;

            default:
                return 0;
        }
    }

    // Choisir une combinaison puis la retire
    public static int choisirCombinaison(Scanner scanner, boolean[] combinaisonsUtilisees, int[] des) {
        String[] lesCombinaisons = {"Paire", "Double Paire", "Brelan", "Carré", "Full House", "Petite Suite", "Grande Suite", "Yahtzee"};

        // Afficher les combinaisons disponibles
        int[] choixDisponibles = new int[8];
        int nombreDisponibles = 0;

        for (int i = 0; i < combinaisonsUtilisees.length; i++) {
            if (!combinaisonsUtilisees[i]) {
                choixDisponibles[nombreDisponibles] = i;
                nombreDisponibles++;
            }
        }

        System.out.print("Choisissez une combinaison à utiliser (1-" + nombreDisponibles + ") : ");
        int choix = scanner.nextInt() - 1;

        while (choix < 0 || choix >= nombreDisponibles) {
            System.out.print("Choix invalide. Veuillez réessayer (1-" + nombreDisponibles + ") : ");
            choix = scanner.nextInt() - 1;
        }

        int combinaisonChoisie = choixDisponibles[choix];
        combinaisonsUtilisees[combinaisonChoisie] = true;
        System.out.println("\nVous avez choisi : " + lesCombinaisons[combinaisonChoisie]);
        return calculerScoreCombinaison(des, combinaisonChoisie);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean[] combinaisonsUtilisees = new boolean[8];
        int scoreTotal = 0;
        int manche = 1;
        
        while (manche <= 5) {
            System.out.println("\n---- MANCHE " + manche + " ----");
            int[] resultatDes = lancerTousLesDes(5);

            for (int i = 0; i < 2; i++) {
                System.out.println("\nPhase " + (i + 1));
                afficherDes(resultatDes);
                String entree = lireEntreeUtilisateur();

                if (entree.trim().equals("0")) {
                    break;
                }

                relancerDes(resultatDes, entree);
            }


            System.out.println("\nRésultat final :");
            afficherDes(resultatDes);

            // Affichage des combinaisons disponibles avec scores
            afficherCombinaisonsDisponibles(resultatDes, combinaisonsUtilisees);

            int scoreObtenu = choisirCombinaison(scanner, combinaisonsUtilisees, resultatDes);
            if (scoreObtenu >= 0) {
                scoreTotal += scoreObtenu;
            } else {
                break;
            }
            // Affiche le score apres chaque manche
            System.out.println("\nScore total actuel : " + scoreTotal + " pts");
            manche++;
        }
        System.out.println("Score final : " + scoreTotal + " pts");

        
        scanner.close();
    }
}