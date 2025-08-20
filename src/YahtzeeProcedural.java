/*
 * Auteur: Zakaria Eddini
 * Date: 20.08.2025
 * But: Jeu Yahtzee en Java
 */


public class YahtzeeProcedural {

    // Lance un dé
    public static int lancerDe() {
        return (int)(Math.random() * 6 + 1);
    }

    // Affiche les résultats des dés
    public static void afficherDes(int[] resultatDes) {
        for (int i = 0; i < resultatDes.length; i++) {
            System.out.println("Résultat du " + (i+1) + " dé est : " + resultatDes[i]);
        }
    }

    // Fonction principale
    public static int[] lancerTousLesDes(int nombreDeDes) {
        int[] resultat = new int[nombreDeDes];
        for (int i = 0; i < nombreDeDes; i++) {
            resultat[i] = lancerDe();
        }
        return resultat;
    }
    public static void main(String[] args) {
        int[] resultatDes = lancerTousLesDes(5);
        afficherDes(resultatDes);
    }
}