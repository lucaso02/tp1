package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class murcielagos {
    private double x, y;
    private Image murcielagoIzquierda;
	private Image murcielagoDerecha;
	private Image murcielagoActual;

     public murcielagos(double x, double y) {
        this.x = x;
        this.y = y;
        this.murcielagoIzquierda = Herramientas.cargarImagen("cosas/murcielagoizquierda.gif");
	    this.murcielagoDerecha = Herramientas.cargarImagen("cosas/murcielagoderecha.gif");
	    this.murcielagoActual = murcielagoDerecha;
    }

    public void dibujar(Entorno e) {
        e.dibujarImagen(this.murcielagoActual, this.x, this.y, 0, 0.2);
    }

    public void mover(double objetivoX, double objetivoY) {
        double velocidad = 0.7;
        double dx = objetivoX - this.x;
        double dy = objetivoY - this.y;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        if (dx < 0) {
            this.murcielagoActual = murcielagoIzquierda;
        } else if (dx > 0) {
            this.murcielagoActual = murcielagoDerecha;
        }

        double distanciaMinima = 20; // Por ejemplo, 20 píxeles

        if (distancia > distanciaMinima) {
            this.x += velocidad * dx / distancia;
            this.y += velocidad * dy / distancia;
        }
        // Si está dentro de distancia mínima, no se mueve
    }
}