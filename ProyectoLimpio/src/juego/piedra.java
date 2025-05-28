package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class piedra {
	public double x, y;
	public double bordIz, bordDe, bordSu, bordIn;
	private Image piedra;

	
	public piedra(double x, double y) {
        this.x = x;
        this.y = y;
        this.piedra = Herramientas.cargarImagen("cosas/piedra.png");
        this.bordIz = x - 2;
        this.bordDe = x + 2;
        this.bordSu = y - 2;
        this.bordIn = y + 2;

    }

    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(piedra, this.x, this.y, 0, 0.2);
    }
	}
 //HOL AHOLA COMO ESTAN