package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Goule;
import serveur.element.Element;
import static utilitaires.Constantes.*;

/**
 * Stratégie d'un Berserker
 */
public class StrategieGoule extends StrategiePersonnage {
	
	/**
	 * Cree un berserker, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du berserker
	 * @param nom le nom du berserker
	 * @param groupe le groupe du berserker
	 * @param caracts les caractéristiques du berserker
	 * @param nbTours nombre de tours pour ce berserker (si negatif, illimite)
	 * @param position position initiale du berserker dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategieGoule(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nbTours, position, logger, new Goule(nom, groupe,caracts));
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
		// les goules ne peuvent pas ramaser de potion, on les ignore
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
		// les goules ne peuvent pas ramaser de potion, on les ignore
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
		if(this.timerCapacite == 0){
			return super.agirRien(arene, refRMI, voisins);
		}
		else{
			this.timerCapacite = RAGE_TIMER;
			voisins.clear();
			return arene.rager(refRMI);
		}
	}
}
