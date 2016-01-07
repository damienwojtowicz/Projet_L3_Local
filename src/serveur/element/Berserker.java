package serveur.element;

import java.rmi.RemoteException;
import java.util.HashMap;

import serveur.Arene;
import serveur.vuelement.VuePersonnage;

import static utilitaires.Constantes.*;

/**
 * 
 * Berserker: un personnage qui gagne de la force lorsqu'il est attaqué.
 * 
 */
public class Berserker extends Personnage {

	private static final long serialVersionUID = 1L;

	/**
	 * Cree un berserker avec un nom et un groupe.
	 * @param nom du berserker
	 * @param groupe d'etudiants du berserker
	 * @param caracts caracteristiques du berserker
	 */
	public Berserker(String nom, String groupe, HashMap<Caracteristique, Integer> caracts){
		super("Berserker "+nom, groupe, caracts);
	}

	public void capaciteCombatAtt(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		arene.incrementeCaractPersonnage(defenseur, Caracteristique.FORCE, BERSERK_FORCE);
	}
	public void capaciteCombatDef(VuePersonnage defenseur,VuePersonnage attaquant, Arene arene) throws RemoteException{
		
	}
}
