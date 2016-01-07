package client;


import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.Arene;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Druide;
import serveur.interaction.Concocter;
import serveur.vuelement.VueElement;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;
import static utilitaires.Constantes.*;

/**
 * Druide: améliore les potions avant de les boire, ne cherche pas le combat en-dessous d'un seuil de force
 */
public class StrategieDruide extends StrategiePersonnage {
	
	/**
	 * Crée un druide, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du druide
	 * @param nom le nom du druide
	 * @param groupe le groupe du druide
	 * @param caracts les caractéristiques du druide
	 * @param nbTours nombre de tours pour ce druide (si negatif, illimite)
	 * @param position position initiale du druide dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieDruide(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Druide(nom, groupe,caracts));
	}
	
	/**
	 * action effectuée si le personnage est à proximité d'une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirPotion(IArene arene, int refRMI, int refCible, HashMap<Integer, Point> voisins) throws RemoteException{
		if(this.timerCapacite > 0){
			// s'il ne peut pas la concocter, il l'a boit
			
			voisins.remove(refCible);
			return super.agirPotion(arene,refRMI,refCible,voisins);
		}else{
			// sinon il l'a modifie
			this.timerCapacite = CONCOCTER_TIMER;
			voisins.clear();
			return arene.concoctePotion(refRMI, refCible);
		}
	}
	
	/**
	 * action effectuée si le personnage voit un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage ciblé
	 * @param elemPlusProche personnage ciblé
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		if(console.getPersonnage().getCaract(Caracteristique.FORCE) >= DRUIDE_SEUIL_FORCE){
			// si le druide a suffisamment de force, il charge
			return super.voitPersonnage(arene, refRMI, refCible, elemPlusProche, voisins);
		}else{
			// sinon il l'ignore
			voisins.remove(refCible);
			return false;
		}
	}
}
