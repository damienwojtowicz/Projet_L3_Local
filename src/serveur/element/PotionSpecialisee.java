package serveur.element;

public class PotionSpecialisee extends Potion {
	public PotionSpecialisee(String groupe,Caracteristique c) {
		super(utilitaires.GenererCaracts.nommerPotion(c), groupe, utilitaires.GenererCaracts.remplirPotion(c));
	}
}
