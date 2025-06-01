package juego;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private mago mago;
	private murcielago[] murcielagos;
	private piedra[] piedras;
	private Image panelHechizos;
	private Image fondo;

	private Image crystalExplosion;
	private Image fireball;
	private Image silverLining;

	private Hechizo hechizoCrystalExplosion;
	private Hechizo hechizoFireball;
	private Hechizo hechizoSilverLining;
	private Point posLanzamiento = null;

	private Image gifFireball, gifCrystalExplosion, gifSilverLining;
	private String hechizoActivo = null;
	private int ticksHechizo = 0;

	private String hechizoaLanzar = null;
	private int energiaMagica = 100; // Podés ajustar este número

	public static Image cargarImagen(String ruta, int ancho, int alto) {
		ImageIcon icon = new ImageIcon(Herramientas.class.getResource(ruta));
		Image img = icon.getImage();
		return img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
	}

	double bordIz, bordDe, bordSu, bordIn;
	boolean izq, aba, der, arr;

	Juego() {// pepsilover4ever
		this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
		this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg");
		this.panelHechizos = Herramientas.cargarImagen("cosas/panelhechizos.png");

		this.crystalExplosion = cargarImagen("/cosas/crystalExplosion.png", 300, 210);
		this.fireball = cargarImagen("/cosas/fireball.png", 250, 100);
		this.silverLining = cargarImagen("/cosas/silverLining.png", 250, 100);

		this.hechizoCrystalExplosion = new Hechizo("Crystal Explosion", 30, 100);
		this.hechizoFireball = new Hechizo("Fireball", 50, 150);
		this.hechizoSilverLining = new Hechizo("Silver Lining", 0, 60);

		this.gifFireball = Herramientas.cargarImagen("cosas/fireball.gif");
		this.gifCrystalExplosion = Herramientas.cargarImagen("cosas/crystalExplosion.gif");
		this.gifSilverLining = Herramientas.cargarImagen("cosas/silverLining.gif");

		this.mago = new mago(100, 100);
		this.murcielagos = new murcielago[10];
		for (int i = 0; i < murcielagos.length; i++) {
			double x = 200 + Math.random() * 800;
			double y = 100 + Math.random() * 600;
			murcielagos[i] = new murcielago(x, y);
		}

		this.piedras = new piedra[4];
		int[] coorY = { 200, 500, 300, 600 };
		int[] coorX = { 200, 400, 700, 700 };
		for (int i = 0; i < piedras.length; i++) {
			this.piedras[i] = new piedra(coorX[i], coorY[i]);
		}

		this.izq = false;
		this.arr = false;
		this.der = false;
		this.aba = false;
		this.entorno.iniciar();
	}

	public void tick() {
		// --- Dibuja el fondo y el panel de hechizos ---
		entorno.dibujarImagen(fondo, 500, 400, 0);
		entorno.dibujarImagen(panelHechizos, 1120, 400, 0);
		// --- Si el mago murio, salimos del tick ---
		if (mago == null)
			return;
		// --- Dibuja las piedras ---
		for (piedra piedra : piedras) {
			piedra.dibujar(entorno);
		}

		entorno.dibujarImagen(crystalExplosion, 1120, 200, 0);
		entorno.dibujarImagen(fireball, 1120, 400, 0);
		entorno.dibujarImagen(silverLining, 1120, 600, 0);

		// --- Dibuja al mago ---
		mago.dibujar(entorno);

		if (ticksHechizo > 0 && hechizoActivo != null) {
			Image imagen = null;
			if ("crystalExplosion".equals(hechizoActivo)) {
				imagen = gifCrystalExplosion;
			} else if ("fireball".equals(hechizoActivo)) {
				imagen = gifFireball;
			} else if ("silverLining".equals(hechizoActivo)) {
				imagen = gifSilverLining;
			}

			if (imagen != null && posLanzamiento != null) {
				entorno.dibujarImagen(imagen, posLanzamiento.x, posLanzamiento.y, 0);
			}

			ticksHechizo--;
			if (ticksHechizo == 0) {
				posLanzamiento = null;
				hechizoActivo = null;
			}
		}
		// Movimiento del Mago
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('w')) {
			mago.mover(-1, 0);
			if (hayColisionConPiedras(mago) || mago.y <= 20)
				mago.mover(1, 0);
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('s')) {
			mago.mover(1, 0);
			if (hayColisionConPiedras(mago) || mago.y >= 780)
				mago.mover(-1, 0);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
			mago.mover(0, -1);
			if (hayColisionConPiedras(mago) || mago.x <= 20)
				mago.mover(0, 1);
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
			mago.mover(0, 1);
			if (hayColisionConPiedras(mago) || mago.x >= 900)
				mago.mover(0, -1);
		}

		// Seleccionar hechizo y lanzarlo
				if (entorno.sePresionoBoton(1)) {
					Point pos = entorno.getMousePosition();
					if (pos != null) {
						int xClick = pos.x;
						int yClick = pos.y;
						int botonX = 995;

						String hechizoSeleccionado = seleccionDeHechizo(xClick, yClick);

						if (hechizoSeleccionado != null) {
							hechizoaLanzar = hechizoSeleccionado;
							System.out.println("Seleccionaste " + hechizoaLanzar);
						}

						else if (hechizoaLanzar != null && xClick < botonX) {
							Hechizo hechizo = null;

							if (hechizoaLanzar.equals("crystalExplosion")) {
								hechizo = hechizoCrystalExplosion;
							} else if (hechizoaLanzar.equals("fireball")) {
								hechizo = hechizoFireball;
							} else if (hechizoaLanzar.equals("silverLining")) {
								hechizo = hechizoSilverLining;
							}

							if (hechizo != null) {
								if (energiaMagica >= hechizo.costoEnergia) {
									energiaMagica -= hechizo.costoEnergia;
									hechizoActivo = hechizoaLanzar;
									ticksHechizo = 30;
									posLanzamiento = new Point(xClick, yClick);
									System.out.println("Lanzaste " + hechizo.nombre + " en (" + xClick + ", " + yClick + ")");
									hechizoaLanzar = null;
								} else {
									System.out.println("No tienes suficiente energía para " + hechizo.nombre);
								}
							}
						}
					}
				}

		// --- Muestra la vida del mago ---

		entorno.cambiarFont("Arial", 24, java.awt.Color.RED);
		entorno.escribirTexto("Vida: " + mago.vida, 50, 50);
		entorno.cambiarFont("Arial", 24, java.awt.Color.blue);
		entorno.escribirTexto("Magia: " + energiaMagica, 50, 100);
		// --- Muerte del mago ---
		if (mago.vida <= 0)
			mago = null;
		// --- Movimiento y dibujo de los murcielagos ---
		for (int i = 0; i < murcielagos.length; i++) {
			murcielago m = murcielagos[i];
			if (m != null) {
				m.mover(mago.x, mago.y);
				m.dibujar(entorno);

				if (MurcielagoTocaMago(mago, m)) {
					mago.vida -= 10;
					murcielagos[i] = null;
				}
			}
		}
	}

	// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	private String seleccionDeHechizo(int x, int y) {

		int xClick = x;
		int yClick = y;
		int botonX = 995; // 1120 - 125
		int botonAncho = 250;

		if (xClick >= botonX && xClick <= botonX + botonAncho) {
			if (yClick >= 125 && yClick <= 275)
				return "crystalExplosion";

			if (yClick >= 325 && yClick <= 475)
				return "fireball";

			if (yClick >= 525 && yClick <= 675)
				return "silverLining";

		}

		return null;
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

	private boolean MurcielagoTocaMago(mago mago, murcielago murcielago) {
		double distancia = Math.sqrt(Math.pow(mago.x - murcielago.x, 2) + Math.pow(mago.y - murcielago.y, 2));
		return distancia < 40;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}