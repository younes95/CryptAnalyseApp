package methode;

public class Noeud {
	
private float pourcentage;
private String cle;

public Noeud(float pourcentage, String cle) {
	super();
	this.pourcentage = pourcentage;
	this.cle = cle;
}
public float getPourcentage() {
	return pourcentage;
}
public void setPourcentage(float pourcentage) {
	this.pourcentage = pourcentage;
}
public String getCle() {
	return cle;
}
public void setCle(String cle) {
	this.cle = cle;
}

}
