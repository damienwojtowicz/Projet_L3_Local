package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Feticheur;
import serveur.element.Element;
import static utilitaires.Constantes.*;

/**
 * Stratégie d'un Feticheur
 */
public class StrategieFeticheur extends StrategiePersonnage {
	
	/**
	 * Cree un Feticheur, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du Feticheur
	 * @param nom le nom du Feticheur
	 * @param groupe le groupe du Feticheur
	 * @param caracts les caractéristiques du Feticheur
	 * @param nbTours nombre de tours pour ce Feticheur (si negatif, illimite)
	 * @param position position initiale du Feticheur dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieFeticheur(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Feticheur(nom, groupe,caracts));
	}
	
	/**
	 * action effectuée si le personnage est à proximitée d'une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	@Override
	protected boolean agirPotion(IArene arene, int refRMI, int refCible, HashMap<Integer, Point> voisins) throws RemoteException{
		// Le Feticheur ignore les potions
		voisins.remove(refCible);;
		return false;
	}
	
	/**
	 * action effectuée si le personnage voit une potion
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id de la potion ciblée
	 * @param elemPlusProche potion ciblée
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	@Override
	protected boolean voitPotion(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		// Le Feticheur ignore les potions
		voisins.remove(refCible);;
		return false;
	}
	
	/**
	 * action effectuée si le personnage voit un personnage
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param refCible id du personnage ciblé
	 * @param elemPlusProche personnage ciblée
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean voitPersonnage(IArene arene, int refRMI, int refCible, Element elemPlusProche, HashMap<Integer, Point> voisins) throws RemoteException{
		// Le Feticheur ne cherche pas à se battre
		voisins.remove(refCible);;
		return false;
	}
	
	/**
	 * action effectuée si le personnage ne voit rien
	 * @param arene du personnage
	 * @param refRMI id du personnage
	 * @param voisins vision du personnage 
	 * @throws RemoteException
	 */
	protected boolean agirRien(IArene arene, int refRMI, HashMap<Integer, Point> voisins) throws RemoteException{
		if(this.timerCapacite > 0){
			// si le timer n'est pas fini, il erre
			return super.agirRien(arene, refRMI, voisins);
		}
		else{
			// sinon il pose un poison
			this.timerCapacite = EMPOISONNE_TIMER;
			voisins.clear();
			return arene.lancerPoison(refRMI);
		}
	}
}
