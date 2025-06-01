package juego;

public class Hechizo {
    public String nombre;
    public int costoEnergia;
    public int radioEfecto; // o ancho/alto si el área no es circular

    public Hechizo(String nombre, int costoEnergia, int radioEfecto) {
        this.nombre = nombre;
        this.costoEnergia = costoEnergia;
        this.radioEfecto = radioEfecto;
    }
}