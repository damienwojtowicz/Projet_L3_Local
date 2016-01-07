package serveur.element;

/**
 * Poison : une potion qui reduit les statistiques au lieu de les monter.
 */
public class Poison extends Potion {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur d'un poison avec le groupe qui l'a envoyée
	 * @param groupe groupe d'etudiants de la potion
	 */
	public Poison(String groupe) {
		super("Poison", groupe, utilitaires.GenererCaracts.remplirPoison());
	}
}
