package juego;

import java.awt.Image;
import java.awt.Color;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private mago p1 ;
	private Image fondo; // inicia la herramienta Image
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.png"); // Carga el fondo que esta en la carpeta "cosas"ProyectoLimpio
		this.p1= new mago(100,100);
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
	{
		entorno.dibujarImagen(fondo, 600, 500, 0);
		// Procesamiento de un instante de tiempo
		// ...
		this.p1.dibujar(entorno);
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			this.p1.mover(-1,0);
		}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			this.p1.mover(1,0);
		}
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.p1.mover(0,1);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.p1.mover(0,-1);
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
