package juego;

import java.awt.Image;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private mago mago ;
	private murcielago[] murcielagos;
	private piedra[] piedras;
	private Image panelHechizos;
	private Image fondo;
	double bordIz, bordDe, bordSu, bordIn;
	boolean izq; boolean aba; boolean der; boolean arr;
	
	Juego() { //cocalover4ever
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg"); 
		this.panelHechizos = Herramientas.cargarImagen("cosas/panelhechizos.png");
		this.mago= new mago(100,100);
		this.murcielagos = new murcielago[10];
			for (int i = 0; i < murcielagos.length; i++) {
				double x = 200 + Math.random() * 800;  
				double y = 100 + Math.random() * 600; 
				murcielagos[i] = new murcielago(x, y); }
		this.piedras = new piedra[4];
        	int[] coorY = {200, 500, 300, 600}; 
        	int[] coorX = {200, 400, 700, 700};
        	for (int i =0; i < piedras.length; i ++ ) {
        		this.piedras[i] = new piedra (coorX[i],coorY[i]); }
        this.izq = false; this.arr = false; this.der = false; this.aba = false;
		this.entorno.iniciar(); }

	public void tick() {
		// --- Dibuja el fondo y el panel de hechizos ---
	    entorno.dibujarImagen(fondo, 500, 400, 0);
	    entorno.dibujarImagen(panelHechizos, 1120, 400, 0);
	    // --- Si el mago murio, salimos del tick ---
	    if (mago == null) {
	    	return; }
	    // --- Dibuja las piedras ---
	    for (piedra piedra : piedras) {
	        piedra.dibujar(entorno);}
	    // --- Dibuja al mago ---
	    mago.dibujar(entorno);
	    // --- Movimiento del mago ---
	    if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	        mago.mover(-1, 0);
	        if (hayColisionConPiedras(mago) || mago.y <= 20) {
	            mago.mover(1, 0); }}
	    
	    if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
	        mago.mover(1, 0);
	        if (hayColisionConPiedras(mago) || mago.y >= 780) {
	            mago.mover(-1, 0); }}
	    
	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        mago.mover(0, -1);
	        if (hayColisionConPiedras(mago) || mago.x <= 20) {
	            mago.mover(0, 1); }}

	    if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        mago.mover(0, 1);
	        if (hayColisionConPiedras(mago) || mago.x >= 1280) {
	            mago.mover(0, -1); }}
	    // --- Muestra la vida del mago ---
	    entorno.cambiarFont("Arial", 24, java.awt.Color.RED);
	    entorno.escribirTexto("Vida: " + mago.vida, 50, 50);
	    // --- Muerte del mago ---
	    if (mago.vida <= 0) {
            mago = null; }
	    // --- Movimiento y dibujo de los murcielagos ---
	    for (int i = 0; i < murcielagos.length; i++) {
	        murcielago m = murcielagos[i];
	        if (m != null) {
	            m.mover(mago.x, mago.y);
	            m.dibujar(entorno);
	            
	    if (MurcielagoTocaMago(mago, m)) {
	        mago.vida -= 10;
	        murcielagos[i] = null; }}}}
	
	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		public boolean hayColisionConPiedras(mago m) {
		    for (piedra p : piedras) {
		        boolean colisionIzq = Math.abs(m.bordIz - p.bordDe) < 10 && m.bordSu < p.bordIn && m.bordIn > p.bordSu;
		        boolean colisionDer = Math.abs(m.bordDe - p.bordIz) < 10 && m.bordSu < p.bordIn && m.bordIn > p.bordSu;
		        boolean colisionAba = Math.abs(m.bordIn - p.bordSu) < 10 && m.bordDe > p.bordIz && m.bordIz < p.bordDe;
		        boolean colisionArr = Math.abs(m.bordSu - p.bordIn) < 10 && m.bordDe > p.bordIz && m.bordIz < p.bordDe;

		        if (colisionIzq || colisionDer || colisionAba || colisionArr) {
		            return true; }}
		    return false; }
		
		private boolean MurcielagoTocaMago(mago mago, murcielago murcielago) {
		    double distancia = Math.sqrt(Math.pow(mago.x - murcielago.x, 2) + Math.pow(mago.y - murcielago.y, 2));
		    return distancia < 40; }

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego(); }}