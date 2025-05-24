package juego;

import java.awt.Image;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private mago p1 ;
	private murcielagos p2;


	private Image fondo; // inicia la herramienta Image
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1000, 900);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg"); // Carga el fondo que esta en la carpeta "cosas"ProyectoLimpio
		this.p1= new mago(100,100);
		this.p2= new murcielagos(100,600);    
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{   entorno.dibujarImagen(fondo, 500, 400, 0);
		p1.dibujar(entorno);
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
		    if (p1.y > 20) {
		        p1.mover(-1, 0);
		    }
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
		    if (p1.y < 780) {
		        p1.mover(1, 0);
		    }
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
		    if (p1.x > 20) {
		        p1.mover(0, -1);
		    }
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
		    if (p1.x < 1280) {
		        p1.mover(0, 1);
		    }
		
		}
		this.p2.mover(p1.x, p1.y);
		this.p2.dibujar(entorno);
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
