/*
 * Auteur : Zakaria Eddini
 * Date   : 20.08.2025
 * But    : Jeu Yahtzee en Java
 */
import java.util.Arrays;
import java.util.Scanner;

public class YahtzeeProcedural {

    // Lance un dé
    public static int lancerDe() {
        return (int)(Math.random() * 6 + 1);
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

    // Vérifie si une face apparait
    public static boolean contientN(int[] occurrences, int n) {
        for (int compte : occurrences) {
            if (compte >= n)
                return true;
        }
        return false;
    }

    // Vérifie si c’est un full house
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



    // Vérifie si c’est une petite suite
    public static boolean estPetiteSuite(int[] occurrences) {
        return contientSuite(occurrences, 4);
    }

    // Vérifie si c’est une grande suite
    public static boolean estGrandeSuite(int[] occurrences) {
        return contientSuite(occurrences, 5);
    }

    // Détecte une suite de valeurs consécutives
    public static boolean contientSuite(int[] occurrences, int longueur) {
        int compte = 0;
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i] > 0) {
                compte++;
                if (compte >= longueur)
                    return true;
            } else {
                compte = 0;
            }
        }
        return false;
    }
    // Afficher uniquement les combinaisons en
    // Calcule et affiche les scores
    public static void calculerScore(int[] des) {
        int[] occ = compterOccurrences(des);

        System.out.println("Yahtzee (5 identiques) : " + (contientN(occ, 5) ? " +50 pts" : "X"));
        System.out.println("Carré (4 identiques) : " + (contientN(occ, 4) ? " +40 pts" : "X"));
        System.out.println("Paire (2 identiques) : " + (contientN(occ, 2) ? " +40 pts" : "X"));
        System.out.println("Brelan (3 identiques) : " + (contientN(occ, 3) ? " +30 pts" : "X"));
        System.out.println("Full House (3 + 2) : " + (estFullHouse(occ) ? " +25 pts" : "X"));
        System.out.println("Petite Suite (4 consécutifs) : " + (estPetiteSuite(occ) ? " +30 pts" : "X"));
        System.out.println("Grande Suite (5 consécutifs) : " + (estGrandeSuite(occ) ? " +40 pts" : "X"));
    }

    // Fonction principale
    public static void main(String[] args) {
        int[] resultatDes = lancerTousLesDes(5);
        for (int i = 0; i < 3; i++) {
            System.out.println("Phase " + (i + 1));
            afficherDes(resultatDes);
            String entree = lireEntreeUtilisateur();
            relancerDes(resultatDes, entree);
        }
        System.out.println("Résultat final :");
        afficherDes(resultatDes);
        calculerScore(resultatDes);
    }
}