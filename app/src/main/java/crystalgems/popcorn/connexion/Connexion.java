package crystalgems.popcorn.connexion;

/**
 * Created by Antoine on 30/03/2017.
 */

public class Connexion {
    private static final Connexion instance = new Connexion();
    private int userId = 0;

    private Connexion() {
    }

    public static Connexion getInstance() {
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
