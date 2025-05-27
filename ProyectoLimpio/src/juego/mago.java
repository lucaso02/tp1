package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

 public class mago {
	 public double x,y;
	 private Image magoIzquierda;
	 private Image magoDerecha;
	 private Image magoActual;
	 
	 public mago(double x, double y) {
		 this.x=500;
		 this.y=400;
		 this.magoIzquierda = Herramientas.cargarImagen("cosas/magoizquierda.gif");
	     this.magoDerecha = Herramientas.cargarImagen("cosas/magoderecha.gif");
	     this.magoActual = magoDerecha;
	 }
	
	 public void dibujar(Entorno e) {
			e.dibujarImagen(this.magoActual, this.x, this.y, 0,0.4);
		}
	 public void mover(int vertical,int horizontal) {
			this.x += horizontal;
			this.y += vertical;
			if (horizontal < 0) {
		            this.magoActual = magoIzquierda;
		        } else if (horizontal > 0) {
		            this.magoActual = magoDerecha;
		        }
			
	   }
}
