package serveur.element;

/**
 * Une potionSpecialisee : une potion possedant une caracteristique principale donnant un bonus plus important.
 */
public class PotionSpecialisee extends Potion {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur d'une potion specialisée avec le groupe qui l'a envoyée et sa 
	 * caracteristique principale.
	 * @param groupe groupe d'etudiants de la potion
	 * @param c la caracteristique principale potion
	 */
	public PotionSpecialisee(String groupe,Caracteristique c) {
		super(utilitaires.GenererCaracts.nommerPotion(c), groupe, utilitaires.GenererCaracts.remplirPotion(c));
	}
}
