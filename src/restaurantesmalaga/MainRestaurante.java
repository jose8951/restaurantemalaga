package restaurantesmalaga;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import restaurantesmalaga.model.Restaurante;

public class MainRestaurante {

	private static final String RUTA_FICHERO = "restaurantes.txt";

	public static void main(String[] args) throws IOException {
		// TODO Cargar la lista de restaurantes del fichero
		File file = new File(RUTA_FICHERO);
		if (file.exists()) {
			System.out.println("FICHERO EXISTE!, a parsearlo :)");
			// convierto a PATH para usar el nuevo API y así ir más rápido
			Path path = file.toPath();
			// leo todo el fichero en una línea
			List<String> lineas = Files.readAllLines(path);
			List<Restaurante> lisRest = cargarRestaurantes(lineas);
			System.out.println("La lista ORIGINAL tiene " + lisRest.size() + " restaurante");
			mostrarRestaurantes(lisRest);

			// agregar un nuevo registro
			Restaurante restNuevo = new Restaurante();

			restNuevo.setNombre("McDonadls1");
			restNuevo.setDireccion("MC Donadls Plza de la Marina");
			restNuevo.setWeb("www.mcdonalds.com");
			restNuevo.setFichaGoogle("https://goo.gl/maps/DUmVjnSZeX6Y9n448");
			restNuevo.setLatitud(36.7184846f);
			restNuevo.setLongitud(-4.4909181f);
			restNuevo.setBarrio("centro");
			restNuevo.setEspecialidades(List.of("hamburguesas", "patas fritas", "helados"));

			Restaurante r5 = lisRest.get(4);
			boolean esta = buscarRestaurante(lisRest, r5);
			System.out.println("R5 está en la lista " + esta);

			esta = buscarRestaurante(lisRest, restNuevo);
			System.out.println("RNUEVO está en la lista " + esta);

			// buscar por especialidad
			List<Restaurante> lre = buscarRestaurantesPorEspecialidad(lisRest, "Gazpacho");
			System.out.println("Mostrando resultado con especialidad Gazpacho");
			mostrarRestaurantes(lre);

			// otra busqueda
			lre = buscarRestaurantesPorEspecialidad(lisRest, "flamenco en vivo");
			System.out.println("Mostrando resultado con especialidad flamenco en vivo");
			mostrarRestaurantes(lre);

			// lamBDA
			// System.out.println("Mostrando rest con especialidad helados CON LAMBDa" );
			// mostrarRestaurantesLambda(lre);

			// lre=buscarPorPrecioMedio(lisRest, 50);
			// System.out.println("Mostrando rest con precio menor o a = a 50" );
			// mostrarRestaurantesLambda(lre);

			System.out.println("Buscar promedio de Restaurantes por barrio");
			// List<Restaurante> mostrarPromedio = buscarPromedioPorBarrio(lisRest,
			// "Malagueta");
			float mostrarPromedio = buscarPromedioPorBarrio(lisRest, "Malagueta");
			System.out.println("promedio del barrio Malagueta " + mostrarPromedio);

			System.out.println("el restaurante más caro de todos los barrios.");
			Restaurante restauranteMasCaro = restauranteMasCaro(lisRest);
			System.err.println("El restaurante mas caro es:\n" + restauranteMasCaro);
			
			System.out.println("el restaurante más barato de todos los barrios");
			Restaurante restauranteMasBarato= restauranteMasBarato(lisRest);
			System.err.println("El restauraten más barado es:\n"+restauranteMasBarato);
			

		} else {
			System.out.println("NO EXISTE el fichero en esa ruta :(");
		}

	}

	private static List<Restaurante> cargarRestaurantes(List<String> lineas) {
		List<Restaurante> lRestaurantes = null;
		Restaurante restauranteAux = null;
		int numlinea = 0;
		lRestaurantes = new ArrayList<>();
		restauranteAux = new Restaurante();

		for (String linea : lineas) {
			numlinea++;
			switch (numlinea) {
			case 1:
				restauranteAux.setNombre(linea);
				break;
			case 2:
				restauranteAux.setDireccion(linea);
				break;
			case 3:
				restauranteAux.setWeb(linea);
				break;
			case 4:
				restauranteAux.setFichaGoogle(linea);
				break;
			case 5:
				restauranteAux.setLatitud(Float.parseFloat(linea));
				break;
			case 6:
				restauranteAux.setLongitud(Float.parseFloat(linea));
				break;
			case 7:
				restauranteAux.setBarrio(linea);
				break;
			case 8:
				String[] especialidades = linea.split(",");
				quitarBlancos(especialidades);
				List<String> lespecialidades = Arrays.asList(especialidades);
				restauranteAux.setEspecialidades(lespecialidades);
				break;

			case 9:
				Supplier<Integer> generaPrecioMedio = () -> {
					int numeroAleatorio = 0;
					Random random = new Random();
					numeroAleatorio = 20 + random.nextInt(100);
					return numeroAleatorio;
				};

				restauranteAux.setPrecioMedio(generaPrecioMedio.get());
				lRestaurantes.add(restauranteAux);
				numlinea = 0;
				restauranteAux = new Restaurante();
				break;

			}
		}

		return lRestaurantes;
	}

	private static void quitarBlancos(String[] especialidades) {
		for (int z = 0; z < especialidades.length; z++) {
			// quita espacios por delante y por detrás
			especialidades[z] = especialidades[z].trim();
		}
	}

	private static void mostrarRestaurantes(List<Restaurante> listRest) {
		System.out.println("Mostrando restaurantes...");
		for (Restaurante r : listRest) {
			System.out.println(r.toString());
		}
	}

	private static boolean buscarRestaurante(List<Restaurante> lisRest, Restaurante r5) {
		boolean estaRestaurante = false;
		int pos_actual = 0;
		int longitud = lisRest.size();
		Restaurante restauranteAux = null;

		while (pos_actual < longitud && !estaRestaurante) {
			restauranteAux = lisRest.get(pos_actual);
			estaRestaurante = restauranteAux.equals(restauranteAux);
			pos_actual++;
		}

		return estaRestaurante;
	}

	private static List<Restaurante> buscarRestaurantesPorEspecialidad(List<Restaurante> lRestaurantes,
			String especialidad) {

		List<Restaurante> listaRestaurantesPorEspecialidad = null;
		List<String> listaEspecialidesActual = null;
		listaRestaurantesPorEspecialidad = new ArrayList<>();

		for (Restaurante restActual : lRestaurantes) {
			// obtengo especialidad
			listaEspecialidesActual = restActual.getEspecialidades();
			// System.out.println(listaEspecialidesActual);
			// ??está la especialidad aquí?¿
			if (listaEspecialidesActual.contains(especialidad)) {
				// la especialidad está en la lista de especialades del restaurante
				listaRestaurantesPorEspecialidad.add(restActual);
			}
		}
		return listaRestaurantesPorEspecialidad;
	}

	private static void mostrarRestaurantesLambda(List<Restaurante> listRest) {
//		for(Restaurante a: listRest) {
//			System.err.println(a);
//		}		
		listRest.forEach(restaurante -> System.err.println(restaurante));
	}

	private static List<Restaurante> buscarPorPrecioMedio(List<Restaurante> listRest, float presupuesto) {
		List<Restaurante> lRestConPrecioMaximoBuscado = null;
		lRestConPrecioMaximoBuscado = listRest.stream().filter(r -> {
			return (r.getPrecioMedio() <= presupuesto);
		}).toList();
		return lRestConPrecioMaximoBuscado;

	}

//	private static List<Restaurante> buscarPromedioPorBarrio(List<Restaurante> lRestaurantes, String barrio) {
//
//		List<Restaurante> lRestauranteBarrio = null;
//		String listaEspecialidesActual = null;
//		int resultado = 0;
//		lRestauranteBarrio = new ArrayList<>();
//		for (Restaurante resActual : lRestaurantes) {
//			listaEspecialidesActual = resActual.getBarrio();
//			if (listaEspecialidesActual.contains(barrio)) {
//				lRestauranteBarrio.add(resActual);
//				resultado = resultado + resActual.getPrecioMedio();
//			}
//		}
//		float mostrar = (float) resultado / lRestauranteBarrio.size();
//		System.err.println("valor " + mostrar);
//		return lRestauranteBarrio;
//	}

	private static float buscarPromedioPorBarrio(List<Restaurante> lRestaurantes, String barrio) {
		float resultado = 0;
		float valorPromedio = 0;
		String listaBarrio = null;
		int contador = 0;
		for (Restaurante resActual : lRestaurantes) {
			listaBarrio = resActual.getBarrio();
			if (listaBarrio.contains(barrio)) {
				System.out.println(resActual);
				valorPromedio = valorPromedio + resActual.getPrecioMedio();
				contador++;
			}
		}
		resultado = valorPromedio / contador;
		return resultado;
	}

	private static Restaurante restauranteMasCaro(List<Restaurante> lRestaurantes) {
		Restaurante resMasCaro = null;		
		int precioMayor = 0;
		for (Restaurante resActual : lRestaurantes) {
			System.out.println(resActual);
			if (resActual.getPrecioMedio() > precioMayor) {
				resMasCaro = resActual;
				precioMayor = resActual.getPrecioMedio();
			}
		}
		return resMasCaro;
	}
	
	private static Restaurante restauranteMasBarato(List<Restaurante>lRestaurantes) {
		Restaurante resMasCaro = null;		
		int precioMayor = 0;
		for (Restaurante resActual : lRestaurantes) {
			System.out.println(resActual);
			if (resActual.getPrecioMedio() < precioMayor) {
				resMasCaro = resActual;
				precioMayor = resActual.getPrecioMedio();
			}
		}
		return resMasCaro;
	}

}
