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
    pocion[] pociones = new pocion[10]; 
    // --- Imagenes ---
    private Image fondo;
    private Image panelHechizos;
    private Image corazon;
    private Image energia;
    private Image murcielag;
    private Image imagenGameOver;
    private Image imagenWinner;
    private Image hechizos;
    private Image imagenPocion;
    // --- Gif Hechizos ---
    private Image crystalExplosion;
    private Image fireball;
    private Image silverLining;
    private Image crystalExplosionRojo;
    private Image fireballRojo;
    private Image silverLiningRojo;
    private Image gifFireball, gifCrystalExplosion, gifSilverLining;
    // --- Objetos de Hechizo ---
    private Hechizo hechizoCrystalExplosion;
    private Hechizo hechizoFireball;
    private Hechizo hechizoSilverLining;
    // --- Estados ---
    private boolean estaEnGameOver = false;
    private int murcielagosEliminados = 0;
    private int totalMurcielagosGenerados = 10;
    private String hechizoActivo = null;
    private int ticksHechizo = 0;
    private String hechizoaLanzar = null;
    private Point posLanzamiento = null;
    private int energiaMagica = 100;
    // --- Movimientos y Bordes ---
    double bordIz, bordDe, bordSu, bordIn;
    boolean izq, aba, der, arr; 
	public static Image cargarImagen(String ruta, int ancho, int alto) {
	    ImageIcon icon = new ImageIcon(Juego.class.getResource("/" + ruta)); 
	    Image img = icon.getImage();
	    return img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH); }
	
	Juego() { // pepsilover4ever
        this.entorno = new Entorno(this, "Trabajo Practico: El camino de Gondolf", 1300, 800);
        // --- Carga las imagenes ---
        this.fondo = Herramientas.cargarImagen("cosas/fondo.jpeg");
        this.panelHechizos = Herramientas.cargarImagen("cosas/panelhechizos.png");
        this.corazon = cargarImagen("cosas/corazon.png", 60, 60);
        this.energia = cargarImagen("cosas/energia.png", 60, 60);
        this.murcielag = cargarImagen("cosas/murcielag.png", 60, 60);
        this.imagenGameOver = Herramientas.cargarImagen("cosas/gameover.png");
        this.imagenWinner = Herramientas.cargarImagen("cosas/winner.png");
        this.crystalExplosion = cargarImagen("cosas/crystalExplosion.png", 300, 210);
        this.fireball = cargarImagen("cosas/fireball.png", 250, 100);
        this.silverLining = cargarImagen("cosas/silverLining.png", 250, 100);
        this.hechizos = cargarImagen("cosas/hechizos.png", 300, 150);
        this.imagenPocion = Herramientas.cargarImagen("cosas/pocion.png"); 
        this.crystalExplosionRojo = cargarImagen("cosas/crystalExplosionRojo.png", 250, 100);
        this.fireballRojo = cargarImagen("cosas/fireballRojo.png", 250, 100);
        this.silverLiningRojo = cargarImagen("cosas/silverLiningRojo.png", 250, 120);
        this.gifFireball = Herramientas.cargarImagen("cosas/fireball.gif");
        this.gifCrystalExplosion = Herramientas.cargarImagen("cosas/crystalExplosion.gif");
        this.gifSilverLining = Herramientas.cargarImagen("cosas/silverLining.gif");
        // --- Inicia los objetos ---
        hechizoCrystalExplosion = new Hechizo("Crystal Explosion", 0, 40);   
        hechizoFireball = new Hechizo("Fireball", 20, 120);                  
        hechizoSilverLining = new Hechizo("Silver Lining", 100, 9999);       
        // --- Inicia los personajes y elementos ---
        this.mago = new mago(100, 100);
        this.murcielagos = new murcielago[10];
        for (int i = 0; i < murcielagos.length; i++) {
            double x = 50 + Math.random() * (900 - 50); 
            double y = 50 + Math.random() * (entorno.alto() - 100); 
            murcielagos[i] = new murcielago(x, y); }
        this.piedras = new piedra[4];
        int[] coorY = { 200, 500, 300, 600 };
        int[] coorX = { 200, 400, 700, 700 };
        for (int i = 0; i < piedras.length; i++) {
            this.piedras[i] = new piedra(coorX[i], coorY[i]); }
        this.pociones = new pocion[10]; 
        // --- Inicia los estados ---
        this.izq = false;
        this.arr = false;
        this.der = false;
        this.aba = false;
        // --- Inicia el entorno ---
        this.entorno.iniciar(); }
	
	public void tick() {
			entorno.dibujarImagen(fondo, 500, 400, 0);
			entorno.dibujarImagen(panelHechizos, 1120, 400, 0);
			if (estaEnGameOver) {
			    entorno.dibujarImagen(imagenGameOver, 650, 400, 0);

			    //salir del estado d gameOver
			    Point pos = entorno.getMousePosition();
			    if (entorno.sePresionoBoton(1) && pos != null) {
			        int x = pos.x;
			        int y = pos.y;

			        if (x >= 475 && x <= 825 && y >= 625 && y <= 700) {
			            reiniciarJuego();
			            estaEnGameOver = false;
			        }
			    }
			    return;
			}
			if (mago == null)
				return;
			
			for (piedra piedra : piedras) {
				piedra.dibujar(entorno);
			}
			//dibuja las pociones 
			for (int i = 0; i < pociones.length; i++) {
			    if (pociones[i] != null && pociones[i].activa) {
			        pociones[i].dibujar(entorno);
			    }
			}
			//Dibuja los botones originales// boton rojo al estar seleccionado
			entorno.dibujarImagen("crystalExplosion".equals(hechizoaLanzar) ? crystalExplosionRojo : crystalExplosion, 1120, 250, 0);
			entorno.dibujarImagen("fireball".equals(hechizoaLanzar) ? fireballRojo : fireball, 1120, 350, 0);
			entorno.dibujarImagen("silverLining".equals(hechizoaLanzar) ? silverLiningRojo : silverLining, 1120, 450, 0);
			entorno.dibujarImagen(hechizos, 1120, 100, 0);
			mago.dibujar(entorno);

			//dibuja el hechizo correspondiente al boton seleccionado 
			if (ticksHechizo > 0 && hechizoActivo != null) {
				Image imagen = null;
				if ("crystalExplosion".equals(hechizoActivo)) {
					imagen = gifCrystalExplosion;
				} else if ("fireball".equals(hechizoActivo)) {
					imagen = gifFireball;
				} else if ("silverLining".equals(hechizoActivo)) {
					imagen = gifSilverLining;
				}
			// escala(tamaño) de los hechizos 
				if (imagen != null && posLanzamiento != null) {
				    double escala = hechizoActivo.equals("crystalExplosion") ? 0.3 :
				                    hechizoActivo.equals("fireball") ? 0.7 :
				                    hechizoActivo.equals("silverLining") ? 5.0 : 1.0;
				    entorno.dibujarImagen(imagen, posLanzamiento.x, posLanzamiento.y, 0, escala);
				}
				//duracion del hechizo en pantalla
				ticksHechizo--;
				if (ticksHechizo == 0) {
					posLanzamiento = null;
					hechizoActivo = null;
				}
			}
			// hechizo activo, rango, daño
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
			        if (hechizo.nombre.equals("Silver Lining")) {
			            int anchoVisible = 995;
			            int altoVisible = entorno.alto();

			            for (int i = 0; i < murcielagos.length; i++) {
			                murcielago m = murcielagos[i];
			                if (m != null) {
			                    if (m.x >= 0 && m.x <= anchoVisible && m.y >= 0 && m.y <= altoVisible) {
			                        murcielagos[i] = null;
			                        murcielagosEliminados++;
			                        agregarPocion(m.x, m.y); 
			                    }
			                }
			            }
			        } else {
			            for (int i = 0; i < murcielagos.length; i++) {
			                murcielago m = murcielagos[i];
			                if (m != null) {
			                    double dx = m.x - posLanzamiento.x;
			                    double dy = m.y - posLanzamiento.y;
			                    double distancia = Math.sqrt(dx * dx + dy * dy);

			                    if (distancia <= hechizo.radioEfecto) {
			                        murcielagos[i] = null;
			                        murcielagosEliminados++;
			                        agregarPocion(m.x, m.y);
			                    }
			                }
			            }
			        }
			    }
			}
			//movimientos del mago
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
			// + energia x pociones 
			for (int i = 0; i < pociones.length; i++) {
			    if (pociones[i] != null && pociones[i].activa) {
			        if (pociones[i].recoger(mago.x, mago.y)) {
			        	energiaMagica += 10;
			        	if (energiaMagica > 100) {
			        	    energiaMagica = 100;
			        	}
			        }
			    }
			}
			//lanzar el hechizo cn el mouse
			if (entorno.sePresionoBoton(1)) { // se activa cuando haces click izq
				Point pos = entorno.getMousePosition();
				if (pos != null) {
					int xClick = pos.x;
					int yClick = pos.y;
					int botonX = 995;

					String hechizoSeleccionado = seleccionDeHechizo(xClick, yClick);

					if (hechizoSeleccionado != null) {
						hechizoaLanzar = hechizoSeleccionado;
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
							if (hechizo.nombre.equals("Crystal Explosion") || energiaMagica >= hechizo.costoEnergia) {
							    energiaMagica -= hechizo.costoEnergia;
							    hechizoActivo = hechizoaLanzar;
							    ticksHechizo = 30;
							    posLanzamiento = new Point(xClick, yClick);
							    hechizoaLanzar = null;
							} else {
							    
							}
						}
					}
				}
			}
			//parte inferior de la botonera
			entorno.dibujarImagen(corazon, 1020, 555, 0);
			entorno.cambiarFont("Palatino Linotype", 44, java.awt.Color.RED);
			entorno.escribirTexto(mago.vida + "", 1070, 570);

			entorno.dibujarImagen(energia, 1020, 626, 0);
			entorno.cambiarFont("Palatino Linotype", 44, java.awt.Color.BLUE);
			entorno.escribirTexto(energiaMagica + "", 1070, 640);

			entorno.dibujarImagen(murcielag, 1020, 700, 0);
			entorno.cambiarFont("Palatino Linotype", 44, java.awt.Color.BLACK);
			entorno.escribirTexto(murcielagosEliminados + "/50", 1070, 715);
			if (mago.vida <= 0) {
			    estaEnGameOver = true;
			    return;
			}
			//murcielagos eliminados hechizo
			if (murcielagosEliminados >= 50) {
			    entorno.dibujarImagen(imagenWinner, 650, 400, 0);
			    Point pos = entorno.getMousePosition();
			    if (entorno.sePresionoBoton(1) && pos != null) {
			        int x = pos.x;
			        int y = pos.y;
			        if (x >= 200 && x <= 1110 && y >= 510 && y <= 700) {
			            reiniciarJuego();
			        }
			    }
			    return;
			}
			//murcielagos quitan vida //murcielago desaparecer
			for (int i = 0; i < murcielagos.length; i++) {
				murcielago m = murcielagos[i];
				if (m != null) {
					m.mover(mago.x, mago.y);
					m.dibujar(entorno);
					if (MurcielagoTocaMago(mago, m)) {
					    mago.vida -= 10;
					    murcielagos[i] = null;
					    murcielagosEliminados++;
					}
				}
			}
			int murcielagosVivos = 0;
			for (murcielago m : murcielagos) {
			    if (m != null)
			        murcielagosVivos++;
			}
			while (murcielagosVivos < 10 && totalMurcielagosGenerados < 50) {
			    for (int i = 0; i < murcielagos.length; i++) {
			        if (murcielagos[i] == null) {
			            murcielagos[i] = crearMurcielagoEnBorde();
			            murcielagosVivos++;
			            totalMurcielagosGenerados++;
			            break;
			        }
			    }
			}
		}
		//boton de las imagenes de los hechizos (dnd responde el click)
		private String seleccionDeHechizo(int x, int y) {
			int botonX = 995;
			int botonAncho = 250;
			if (x >= botonX && x <= botonX + botonAncho) {
				if (y >= 240 && y <= 320)
					return "crystalExplosion";
				if (y >= 340 && y <= 420)
					return "fireball";
				if (y >= 440 && y <= 520)
					return "silverLining";
			}
			return null;
		}
		//colisiones mago cn el margen de piedra
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
		//colision entre mago y murcielago
		private boolean MurcielagoTocaMago(mago mago, murcielago murcielago) {
			double distancia = Math.sqrt(Math.pow(mago.x - murcielago.x, 2) + Math.pow(mago.y - murcielago.y, 2));
			return distancia < 40;
		}
		//crea murcielago aleatoriamente en cualquier borde
		private murcielago crearMurcielagoEnBorde() {
		    double x = 0, y = 0;
		    int lado = (int)(Math.random() * 4);

		    switch (lado) {
		        case 0:
		            x = 0;
		            y = Math.random() * entorno.alto();
		            break;
		        case 1:
		            x = 900;
		            y = Math.random() * entorno.alto();
		            break;
		        case 2:
		            x = Math.random() * 900;
		            y = 0;
		            break;
		        case 3:
		            x = Math.random() * 900;
		            y = entorno.alto();
		            break;
		    }
		    return new murcielago(x, y);
		}
		//probabilidad pociones 
		private void agregarPocion(double x, double y) {
		     if (Math.random() < 0.5) {
		        for (int i = 0; i < pociones.length; i++) {
		            if (pociones[i] == null || !pociones[i].activa) {
		                pociones[i] = new pocion(x, y, imagenPocion);
		                break;
		            }
		        }
		     }
		}
		
		private void reiniciarJuego() {
		    this.mago = new mago(100, 100);
		    this.energiaMagica = 100;
		    this.hechizoaLanzar = null;
		    this.hechizoActivo = null;
		    this.ticksHechizo = 0;
		    this.posLanzamiento = null;
		    this.murcielagosEliminados = 0;
		    this.totalMurcielagosGenerados = 10;

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

		    for (int i = 0; i < pociones.length; i++) {
		        pociones[i] = null; 
		    }
		}
		@SuppressWarnings("unused")
		public static void main(String[] args) {
			Juego juego = new Juego();
		}
	}