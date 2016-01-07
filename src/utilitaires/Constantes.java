package utilitaires;

import java.awt.Color;

import serveur.element.Caracteristique;
import serveur.vuelement.VueElement;

/**
 * Definit des constantes utiles et quelques methodes tres simples.
 * Les calculs sont dans la classe {@code Calculs}.
 *
 */
public class Constantes {
	
	/**
	 * Adresse IP par defaut (localhost). 
	 */
	public static final String IP_DEFAUT = "localhost";

	/**
	 * Port utilise par defaut.
	 */
	public static final int PORT_DEFAUT = 5099;
	
	/**
	 * Nombre de tours par defaut (30 minutes).
	 */
	public static final int NB_TOURS_DEFAUT = 60 * 30;
	
	/**
	 * Nombre de tours par defaut pour un personnage client.
	 */
	public static final int NB_TOURS_PERSONNAGE_DEFAUT = NB_TOURS_DEFAUT;
	
	/**
	 * Taille du champ de vision des personnages. 
	 */
	public static final int VISION = 30;
	
	/**
	 * Quantite d'initiative retiree a l'attaquant et ajoutee au defenseur
	 * lors d'un duel.
	 */
	public static final int INCR_DECR_INITIATIVE_DUEL = 10;
	
	/**
	 * Contient les distances de projection (suite a un duel) associees a 
	 * chaque "quart" de force. 
	 * Par exemple, si l'attaquant a une valeur de force inferieure ou egale a
	 * un quart de la valeur maximale de force, alors il projetera a la 
	 * distance donnee dans la premiere case.
	 * La deuxieme case contient la distance pour un attaquant ayant une force
	 * superieure au quart de la force possible, mais inferieure a la moitie ;
	 * la troisieme, entre la moitie et les trois quarts ; 
	 * la quatrieme, entre les trois quarts et le maximum possible.
	 */
	public static final int[] DISTANCE_PROJECTION = {4, 5, 6, 7};
	
	/**
	 * Distance minimale entre deux elements pour qu'une interaction soit
	 * possible. 
	 */
	public static final int DISTANCE_MIN_INTERACTION = 2;

	/**
	 * Abscisse minimale de l'arene.
	 */
	public static final int XMIN_ARENE = 0;

	/**
	 * Abscisse maximale de l'arene.
	 */
	public static final int XMAX_ARENE = 100;
	
	/**
	 * Ordonnee minimale de l'arene.
	 */
	public static final int YMIN_ARENE = 0;

	/**
	 * Ordonnee maximale de l'arene.
	 */
	public static final int YMAX_ARENE = 100;
	
	/**
	 * Couleur sur l'IHM pour les personnages qui sont morts.
	 */
	public static final Color COULEUR_MORTS = new Color(112, 112, 112);
	
	// Constantes pour les interactions
	/**
	 * Quantitée de vie perdue à chaque crise rage
	 */
	public static final int RAGE_MALUS = -20;
	
	/**
	 * Quantitées de force et d'initiative gagnées à chaque crise de rage
	 */
	public static final int RAGE_BONUS = 10;
	
	/**
	 * Valeur ajoutée au caractéristique d'une potion lors de concocter
	 */
	public static final int CONCOCTER_BONUS = 15;
	
	// Constantes pour les duels
	/**
	 * Quantitée de force que gagne le Berserker à chaque fois qu'il est attaqué
	 */
	public static final int BERSERK_FORCE = 15;
	
	/**
	 * Quantitée de vie que gagne le vampire à chaque fois qu'il attaque
	 */
	public static final int DRAIN = 10;
	
	/**
	 * Quantitée de vie que gagne la goule à chaque fois qu'elle tue quelqu'un
	 */
	public static final int CANNIBALISME = 30;
	
	// Constantes pour les stratégies
	/**
	 * Le seuil de point de vie au dessus du quel le berserker charge
	 */
	public static final double BERSERK_SEUIL_ATK = 0.2 * Caracteristique.VIE.getMax();
	
	/**
	 * Le seuil de point de vie au dessous du quel le berserker se soigne
	 */
	public static final double BERSERK_SEUIL_SOIN = 0.8 * Caracteristique.VIE.getMax();
	
	/**
	 * Seuil à partir duquel le druide charge
	 */
	public static final int DRUIDE_SEUIL_FORCE = 35;
	
	/**
	 * Timer du druide lui permettant d'améliorer les potions
	 */
	public static final int CONCOCTER_TIMER = 2;
	
	// autres
	
	/**
	 * Valeur max de malus d'un poison
	 */
	public static final int POISON_MAX = 25;
	
	// methodes
	/**
	 * Retourne le nom de la classe de l'objet passe en parametre, sous 
	 * forme de chaine de caracteres.
	 * @param o object
	 * @return chaine de caracteres representant la classe de l'objet
	 */
	public static String nomClasse(Object o) {
		return o.getClass().getSimpleName();
	}
	
	/**
	 * Renvoie le nom RMI associe a une adresse, un port et un nom.
	 * @param ip adresse
	 * @param port port 
	 * @param nom nom
	 * @return nom RMI de la forme "rmi://..."
	 */
	public static String nomRMI(String ip, int port, String nom) {
		return "rmi://" + ip + ":" + port + "/" + nom;
	}

	/**
	 * Cree une nom raccourci d'un client (pour les logs). 
	 * @param client client
	 * @return chaine de caracteres contenant la reference RMI de client, son 
	 * nom et son groupe. 
	 */
	public static String nomRaccourciClient(VueElement<?> client) {
		return "(Client" + client.getRefRMI() + " * " + client.getElement().getNomGroupe() + ")";
	}

	/**
	 * Cree le nom complet d'un client (pour les logs).
	 * @param client client
	 * @return chaine de caracters contenant la classe du client, sa reference
	 * RMI, son nom et son groupe.
	 */
	public static String nomCompletClient(VueElement<?> client) {
		return "("+ nomClasse(client) + client.getRefRMI() + " * " + client.getElement().getNomGroupe() + ")";
	}
}
