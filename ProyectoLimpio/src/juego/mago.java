package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

 public class mago {
	 public double x,y;
	 double ancho, alto;
	 double bordIz, bordDe, bordSu, bordIn;
	 double escala;
	 public int vida;
	 private Image magoIzquierda;
	 private Image magoDerecha;
	 private Image magoActual;
	 
	 public mago(double x, double y) {
		 this.x=500;
		 this.y=400;
		 this.magoIzquierda = Herramientas.cargarImagen("cosas/magoizquierda.gif");
	     this.magoDerecha = Herramientas.cargarImagen("cosas/magoderecha.gif");
	     this.magoActual = magoDerecha;
	     this.escala = 0.4;
	     this.vida = 100;
	     this.alto = this.magoActual.getHeight(null) * this.escala;
	     this.ancho = this.magoActual.getWidth(null) * this.escala;
//aaaaaaaaaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	     
	 }
	
	 public void dibujar(Entorno e) {
			e.dibujarImagen(this.magoActual, this.x, this.y, 0,this.escala);
		}
	 public void mover(int vertical,int horizontal) {
			this.x += horizontal;
			this.y += vertical;
			this.bordIn = y + this.alto /2;
			this.bordSu = y - this.alto /1.2;
			this.bordIz =x - this.ancho /4;
			this.bordDe = x + this.ancho /4;
			if (horizontal < 0) {
		            this.magoActual = magoIzquierda;
		        } else if (horizontal > 0) {
		            this.magoActual = magoDerecha;
		        }
			
	   }
}