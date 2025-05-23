package juego;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class murcielagos {
    private double x, y;
    private Image imagen;

     public murcielagos(double x, double y) {
        this.x = x;
        this.y = y;
        this.imagen = Herramientas.cargarImagen("cosas/murcielago.gif");
    }

    public void dibujar(Entorno e) {
        e.dibujarImagen(this.imagen, this.x, this.y, 0, 0.2);
    }

    public void mover(double objetivoX, double objetivoY) {
        double velocidad = 1;
        double dx = objetivoX - this.x;
        double dy = objetivoY - this.y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        double distanciaMinima = 20; // Por ejemplo, 20 píxeles

        if (distancia > distanciaMinima) {
            this.x += velocidad * dx / distancia;
            this.y += velocidad * dy / distancia;
        }
        // Si está dentro de distancia mínima, no se mueve
    }
}