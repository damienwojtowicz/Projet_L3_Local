package serveur.interaction;

import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Feticheur;
import serveur.element.Poison;
import serveur.vuelement.VuePersonnage;

/**
 * action visant à poser un poison sur le sol
 *
 */
public class Empoisonne extends Interaction<VuePersonnage> {
	
	
	private Runnable runPoison = new Runnable(){
		public void run(){
			try{
				arene.ajoutePotion(new Poison(defenseur.getElement().getNomGroupe()), defenseur.getPosition());
				logs(Level.INFO, "Poison posé avec succes");
			}
			catch(Exception e){
				logs(Level.INFO, "\nErreur lors d'un empoisonnement : " + e.toString());
			}
		}
	};
	/**
	 * 
	 * @param arene arene
	 * @param feticheur Vuepersonnage du Feticheur
	 */
	public Empoisonne(Arene arene, VuePersonnage feticheur) {
		super(arene, feticheur, feticheur);
	}
	
	public void interagit(){
		// si le personnage est vivant, si c'est bien un feticheur
		if(defenseur.getElement().estVivant() && defenseur.getElement() instanceof Feticheur) {
			new Thread(runPoison).start();
			defenseur.setPhrase("Je lache un poison");
		}
		else{
			logs(Level.INFO, "\nEmpoisonnement impossible");
		}
	}
}
