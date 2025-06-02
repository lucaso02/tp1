package juego;
import java.awt.Image;
import entorno.Entorno;

public class pocion {
    double x;
    double y;
    Image imagen;
    boolean activa; // Verdadero si la poción es visible y coleccionable

    public pocion(double x, double y, Image imagen) {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
        this.activa = true; // La poción comienza activa cuando se crea
    }

    public void dibujar(Entorno entorno) {
        if (activa) {
            entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.1);
        }
    }

    // Este método verifica si el mago (en magoX, magoY) está lo suficientemente cerca para recoger la poción.
    // Es posible que debas ajustar la distancia de colisión (por ejemplo, 30) según el tamaño de tus imágenes.
    public boolean recoger(double magoX, double magoY) {
        if (!activa) {
            return false; // Ya fue recogida
        }

        double distancia = Math.sqrt(Math.pow(this.x - magoX, 2) + Math.pow(this.y - magoY, 2));
        if (distancia < 30) { // Ajusta este valor según el tamaño de las imágenes del mago y la poción
            this.activa = false; // Desactiva la poción una vez recogida
            return true;
        }
        return false;
    }
}