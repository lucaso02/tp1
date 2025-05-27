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
	private murcielago p2;
	private piedra[] piedras;
	private Image panelHechizos;
	private Image fondo; // inicia la herramienta Image
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg"); // Carga el fondo que esta en la carpeta "cosas"ProyectoLimpio
		this.panelHechizos = Herramientas.cargarImagen("cosas/panelhechizos.png");
		this.p1= new mago(100,100);
		this.p2= new murcielago(100,600); 
		this.piedras = new piedra[4];
        this.piedras[0] = new piedra(200, 200);
        this.piedras[1] = new piedra(400, 500);
        this.piedras[2] = new piedra(700, 300);
        this.piedras[3] = new piedra(700, 600);
		// Inicializar lo que haga falta para el juego
        
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
		entorno.dibujarImagen(panelHechizos, 1120, 400, 0);
		
		for (piedra piedra : piedras) {
		    piedra.dibujar(entorno);
		    
		}
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

