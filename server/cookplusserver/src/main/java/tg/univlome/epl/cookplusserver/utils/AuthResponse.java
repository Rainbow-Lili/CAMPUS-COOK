package tg.univlome.epl.cookplusserver.utils;

public class AuthResponse {

    private Long id;
    private String email;
    private String nom;
    private String role;
    private String token;
    private long expiresIn;

    public AuthResponse() {
    }

    public AuthResponse(Long id, String email, String nom, String role, String token, long expiresIn) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.role = role;
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
