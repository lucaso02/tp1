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
	double bordIz, bordDe, bordSu, bordIn;
	boolean izq;
	boolean aba;
	boolean der;
	boolean arr;
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg"); // Carga el fondo que esta en la carpeta "cosas"ProyectoLimpio
		this.panelHechizos = Herramientas.cargarImagen("cosas/panelhechizos.png");
		this.p1= new mago(100,100);
		this.p2= new murcielago(100,600); 
		this.piedras = new piedra[4];
        int[] coorY = {200, 500, 300, 600};
        int[] coorX = {200, 400, 700, 700};
        
		// Inicializar lo que haga falta para el juego
        for (int i =0; i < piedras.length; i ++ ) {
        	this.piedras[i] = new piedra (coorX[i],coorY[i]);
        }
        this.izq = false;
        this.arr = false;
        this.der = false;
        this.aba = false;
        
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
	    entorno.dibujarImagen(fondo, 500, 400, 0);
	    entorno.dibujarImagen(panelHechizos, 1120, 400, 0);

	    for (piedra piedra : piedras) {
	        piedra.dibujar(entorno);
	    }

	    p1.dibujar(entorno);

	    if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	        p1.mover(-1, 0);
	        if (hayColisionConPiedras(p1) || p1.y <= 20) {
	            p1.mover(1, 0); // revierte
	        }
	    }

	    if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
	        p1.mover(1, 0);
	        if (hayColisionConPiedras(p1) || p1.y >= 780) {
	            p1.mover(-1, 0); // revierte
	        }
	    }

	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        p1.mover(0, -1);
	        if (hayColisionConPiedras(p1) || p1.x <= 20) {
	            p1.mover(0, 1); // revierte
	        }
	    }

	    if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        p1.mover(0, 1);
	        if (hayColisionConPiedras(p1) || p1.x >= 1280) {
	            p1.mover(0, -1); // revierte
	        }
	    }

	    p2.mover(p1.x, p1.y);
	    p2.dibujar(entorno);
	}

	
		public boolean hayColisionConPiedras(mago m) {
		    for (piedra p : piedras) {
		        boolean colisionIzq = Math.abs(m.bordIz - p.bordDe) < 10 && m.bordSu < p.bordIn && m.bordIn > p.bordSu;
		        boolean colisionDer = Math.abs(m.bordDe - p.bordIz) < 10 && m.bordSu < p.bordIn && m.bordIn > p.bordSu;
		        boolean colisionAba = Math.abs(m.bordIn - p.bordSu) < 10 && m.bordDe > p.bordIz && m.bordIz < p.bordDe;
		        boolean colisionArr = Math.abs(m.bordSu - p.bordIn) < 10 && m.bordDe > p.bordIz && m.bordIz < p.bordDe;

		        if (colisionIzq || colisionDer || colisionAba || colisionArr) {
		            return true;
		        }
		    }
		    return false;
		}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}