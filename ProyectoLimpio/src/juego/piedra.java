package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class piedra {
	public double x, y;
	private Image piedra;
	
	public piedra(double x, double y) {
        this.x = x;
        this.y = y;
        this.piedra = Herramientas.cargarImagen("cosas/piedra.png"); 
    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(piedra, this.x, this.y, 0, 0.2);
    }
	}
