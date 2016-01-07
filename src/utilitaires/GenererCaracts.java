package utilitaires;

import java.util.HashMap;
import serveur.element.*;
import static utilitaires.Constantes.*;

public class GenererCaracts {
	/**
	 * Calcule une valeur aléatoire pour une caractéristique principale
	 * @param c une caractéristique
	 * @return un entier compris entre 50% et 100% du max de c
	 */
	private static int caractPrincipale(Caracteristique c){
		return Calculs.nombreAleatoire((int)(0.25 * c.getMax()),(int)(0.50 * c.getMax()));
	}
	/**
	 * Calcule une valeur aléatoire pour une caractéristique secondaire
	 * @param c une caractéristique
	 * @return un entier compris entre -25% et 25% du max de c
	 */
	private static int caractSecondaire(Caracteristique c){
		return Calculs.nombreAleatoire(0,(int)(0.25 * c.getMax()));
	}
	
	/**
	 * Renvoie une hashmap remplie selon une caracteristique principale
	 * @param c la caracteristique principale de la potion
	 * @return une hashmap pour configurer une potion
	 */
	public static HashMap<Caracteristique, Integer> remplirPotion(Caracteristique c){
		HashMap<Caracteristique, Integer> res = new HashMap<Caracteristique, Integer>();
		for(Caracteristique k : Caracteristique.values()){
			if(k == c){
				res.put(k, caractPrincipale(k));
			}
			else{
				res.put(k, caractSecondaire(k));
			}
		}
		return res;
	}
	
	/**
	 * Renvoie une hashmap remplie pour un poison
	 * @return une hashmap pour configurer le poison
	 */
	public static HashMap<Caracteristique, Integer> remplirPoison(){
		HashMap<Caracteristique, Integer> res = new HashMap<Caracteristique, Integer>();
		for(Caracteristique k : Caracteristique.values()){
			res.put(k, Calculs.nombreAleatoire(0, POISON_MAX));
		}
		return res;
	}
	
	/**
	 * Génère un nom de potion selon sa caracteristique principale
	 * @param c la caracteristique principale de la potion
	 * @return le nom de la potion
	 */
	public static String nommerPotion(Caracteristique c){
		switch(c){
		case VIE:
			return "Anduril";
		case FORCE:
			return "Excalibur";
			
		case INITIATIVE:
			return "Fine Lame";
		default:
			return "Eau Plate";
		}
	}
	
	/**
	 * A partir du nom d'une classe, génere une hashmap définissant les caractéristiques
	 * de la classe lors de son instanciation
	 * @param typePerso une chaine de caractères désigne le type de personnage crée
	 * @return une hashmap pour configurer les caractéristiques du personnage
	 */
	public static HashMap<Caracteristique, Integer> statsPerso(String typePerso){
		HashMap<Caracteristique, Integer> res = new HashMap<Caracteristique, Integer>();
		// tout les perso commencent avec 100 de vie
		res.put(Caracteristique.VIE, 100);
		switch(typePerso){
		case "Berserker":
			res.put(Caracteristique.FORCE, 15);
			res.put(Caracteristique.INITIATIVE, 30);
			return res;
		case "Druide":
			res.put(Caracteristique.FORCE, 10);
			res.put(Caracteristique.INITIATIVE, 10);
			return res;
		case "Vampire":
			res.put(Caracteristique.FORCE, 20);
			res.put(Caracteristique.INITIATIVE, 20);
			return res;
		case "Goule":
			res.put(Caracteristique.FORCE, 15);
			res.put(Caracteristique.INITIATIVE, 15);
			return res;
		case "Feticheur":
			res.put(Caracteristique.FORCE, 10);
			res.put(Caracteristique.INITIATIVE, 10);
			return res;
		default:
			res.put(Caracteristique.FORCE, 5);
			res.put(Caracteristique.INITIATIVE, 5);
			return res;
		}
	}
}
