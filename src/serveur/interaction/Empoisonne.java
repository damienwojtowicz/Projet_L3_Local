package serveur.interaction;

import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Feticheur;
import serveur.element.Poison;
import serveur.vuelement.VuePersonnage;

/**
 * action visant � poser un poison sur le sol
 *
 */
public class Empoisonne extends Interaction<VuePersonnage> {
	
	/**
	 * on d�finit un runnable nous permettant de lan�er des methodes synchronis�es,
	 * ici ajoutePotion
	 */
	private Runnable runPoison = new Runnable(){
		public void run(){
			try{
				arene.ajoutePotion(new Poison(defenseur.getElement().getGroupe()), defenseur.getPosition());
				logs(Level.INFO, "Poison pos� avec succes");
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
			// on instancie un runnablepour cr�er la potion
			new Thread(runPoison).start();
			defenseur.setPhrase("Je lache un poison");
		}
		else{
			logs(Level.INFO, "\nEmpoisonnement impossible");
		}
	}
}
