package org.example.domain;

public class Partner extends Users{
    private boolean asset;

    public Partner() {}

    public Partner(int id, String name, String email, String password, Rol rol, boolean asset) {
        super(id, name, email, password, rol);
        this.asset = asset;
    }

    public Partner(String name, String email, String password, Rol rol, boolean asset) {
        super(name, email, password, rol);
        this.asset = asset;
    }
    // ✅ Constructor sin boolean (para consultas simples)
    public Partner(int id, String name, String email, String password, Rol rol) {
        super(id, name, email, password, rol);
    }

    public Partner(String name, String email, String password, Rol rol) {
        super(name, email, password, rol); // ✅ Llama al constructor padre
    }


    public boolean isAsset() {
        return asset;
    }

    public void setAsset(boolean asset) {
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "Partner: " + getName() + " (" + getEmail() + ")";
    }
}
