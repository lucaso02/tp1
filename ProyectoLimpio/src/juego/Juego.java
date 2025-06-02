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
	private Image imagenGameOver;
	private boolean estaEnGameOver = false;
	private Image crystalExplosion;
	private Image fireball;
	private Image silverLining;

	private Image crystalExplosionRojo;
	private Image fireballRojo;
	private Image silverLiningRojo;

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
		this.imagenGameOver = Herramientas.cargarImagen("cosas/gameover.png");
		this.crystalExplosion = cargarImagen("/cosas/crystalExplosion.png", 300, 210);
		this.fireball = cargarImagen("/cosas/fireball.png", 250, 100);
		this.silverLining = cargarImagen("/cosas/silverLining.png", 250, 100);

		//Rojo!!
		this.crystalExplosionRojo = cargarImagen("/cosas/crystalExplosionRojo.png", 250, 100);
		this.fireballRojo = cargarImagen("/cosas/fireballRojo.png", 250, 100);
		this.silverLiningRojo = cargarImagen("/cosas/silverLiningRojo.png", 250, 120);

		hechizoCrystalExplosion = new Hechizo("Crystal Explosion", 10, 40);   // Gasta 10, área pequeña
		hechizoFireball = new Hechizo("Fireball", 50, 100);                  // Gasta 50, área media
		hechizoSilverLining = new Hechizo("Silver Lining", 100, 9999);       // Gasta 100, mata a todos

		this.gifFireball = Herramientas.cargarImagen("cosas/fireball.gif");
		this.gifCrystalExplosion = Herramientas.cargarImagen("cosas/crystalExplosion.gif");
		this.gifSilverLining = Herramientas.cargarImagen("cosas/silverLining.gif");

		this.mago = new mago(100, 100);
		this.murcielagos = new murcielago[10];
		for (int i = 0; i < murcielagos.length; i++) {
			double x = 50 + Math.random() * (995 - 100); // Máximo x antes del panel
			double y = 50 + Math.random() * (entorno.alto() - 100); // Considerando márgenes
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
		if (estaEnGameOver) {
		    entorno.dibujarImagen(imagenGameOver, 650, 400, 0); // Centro del entorno

		    Point pos = entorno.getMousePosition();
		    if (entorno.sePresionoBoton(1) && pos != null) {
		        int x = pos.x;
		        int y = pos.y;

		        // Coordenadas del botón "VOLVER A JUGAR" dentro de la imagen:
		        if (x >= 475 && x <= 825 && y >= 625 && y <= 700) {
		            reiniciarJuego();
		            estaEnGameOver = false;
		        }
		    }
		    return; // Salta el resto del tick si estamos en Game Over
		}
		// --- Si el mago murio, salimos del tick ---
		if (mago == null)
			return;
		// --- Dibuja las piedras ---
		for (piedra piedra : piedras) {
			piedra.dibujar(entorno);
		}

		// Si está seleccionado, mostrar la versión "Roja"
		entorno.dibujarImagen("crystalExplosion".equals(hechizoaLanzar) ? crystalExplosionRojo : crystalExplosion, 1120, 200, 0);
		entorno.dibujarImagen("fireball".equals(hechizoaLanzar) ? fireballRojo : fireball, 1120, 300, 0);
		entorno.dibujarImagen("silverLining".equals(hechizoaLanzar) ? silverLiningRojo : silverLining, 1120, 400, 0);

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
			    double escala = hechizoActivo.equals("crystalExplosion") ? 0.3 :
			                    hechizoActivo.equals("fireball") ? 0.7 :
			                    hechizoActivo.equals("silverLining") ? 5.0 : 1.0;
			    entorno.dibujarImagen(imagen, posLanzamiento.x, posLanzamiento.y, 0, escala);
			}

			ticksHechizo--;
			if (ticksHechizo == 0) {
				posLanzamiento = null;
				hechizoActivo = null;
			}
		}
		if (ticksHechizo == 1 && posLanzamiento != null && hechizoActivo != null) {
		    Hechizo hechizo = null;
		    if ("crystalExplosion".equals(hechizoActivo)) {
		        hechizo = hechizoCrystalExplosion;
		    } else if ("fireball".equals(hechizoActivo)) {
		        hechizo = hechizoFireball;
		    } else if ("silverLining".equals(hechizoActivo)) {
		        hechizo = hechizoSilverLining;
		    }

		    if (hechizo != null) {
		        for (int i = 0; i < murcielagos.length; i++) {
		            murcielago m = murcielagos[i];
		            if (m != null) {
		                double dx = m.x - posLanzamiento.x;
		                double dy = m.y - posLanzamiento.y;
		                double distancia = Math.sqrt(dx * dx + dy * dy);

		                if (distancia <= hechizo.radioEfecto) {
		                    murcielagos[i] = null; // Elimina murciélago
		                }
		            }
		        }
		    }
		}
		// Movimiento del Mago
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('w')) {
			mago.mover(-1, 0);
			if (hayColisionConPiedras(mago) || mago.y <= 40)
				mago.mover(1, 0);
		}
		if (entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('s')) {
			mago.mover(1, 0);
			if (hayColisionConPiedras(mago) || mago.y >= 762)
				mago.mover(-1, 0);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('a')) {
			mago.mover(0, -1);
			if (hayColisionConPiedras(mago) || mago.x <= 20)
				mago.mover(0, 1);
		}
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
			mago.mover(0, 1);
			if (hayColisionConPiedras(mago) || mago.x >= 910)
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
		if (mago.vida <= 0) {
		    estaEnGameOver = true;
		    return;
		}
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
		int botonX = 995; // x mínimo del panel
		int botonAncho = 250;

		if (x >= botonX && x <= botonX + botonAncho) {
			if (y >= 150 && y <= 250) // alrededor de y = 200
				return "crystalExplosion";

			if (y >= 250 && y <= 350) // alrededor de y = 300
				return "fireball";

			if (y >= 350 && y <= 450) // alrededor de y = 400
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
	private void reiniciarJuego() {
	    this.mago = new mago(100, 100);
	    this.energiaMagica = 100;
	    this.hechizoaLanzar = null;
	    this.hechizoActivo = null;
	    this.ticksHechizo = 0;
	    this.posLanzamiento = null;

	    this.murcielagos = new murcielago[10];
	    for (int i = 0; i < murcielagos.length; i++) {
	        double x = 50 + Math.random() * (995 - 100);
	        double y = 50 + Math.random() * (entorno.alto() - 100);
	        murcielagos[i] = new murcielago(x, y);
	    }

	    this.piedras = new piedra[4];
	    int[] coorY = { 200, 500, 300, 600 };
	    int[] coorX = { 200, 400, 700, 700 };
	    for (int i = 0; i < piedras.length; i++) {
	        this.piedras[i] = new piedra(coorX[i], coorY[i]);
	    }
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}