package guessthenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private List<Player> players;//Los jugadores
    private Player winner; //El jugador que gano la partida
    private int number; //El numero que se debe adivinar
    private boolean hasWinner; //Flag para repetir el juego
    private boolean exit; //Flag para seguir o salir del juego
    private int dificultad;
    private final Scanner scanner;
    private int pantalla;
    private int cantJugadores;

    public Game() {
        players = null;
        winner = null;
        number = 0;
        hasWinner = false;
        exit = false;
        scanner = new Scanner(System.in);
        pantalla = 1;
        cantJugadores = 0;
        dificultad = 0;
    }
    
    public void start() {
        while (!exit) {
            switch (pantalla) {
                case 1:
                    menu();
                    System.out.println("\n\n\n\n\n\n\n\n");
                    break;
                case 2:
                    cantidadDeJugadores();
                    System.out.println("\n\n\n\n\n\n\n\n");
                    break;
                case 3:
                    setPlayers();
                    System.out.println("\n\n\n\n\n\n\n\n");
                    break;
                case 4:
                    seleccionarNivel();
                    System.out.println("\n\n\n\n\n\n\n\n");
                    break;
                case 5:
                    juego();
                    System.out.println("\n\n\n\n\n\n\n\n");
                    break;
                case 6:
                    System.out.println("\t\tGRACIAS POR JUGAR!");
                    exit = true;
                    break;
            }
        }
    }

    private void menu() {
        int opcion;
        System.out.println("\tBienvenidos al juego de adivina el numero\n\n");
        System.out.println("1.Jugar");
        System.out.println("2.Salir");
        System.out.print("\nopcion: ");
        opcion = validarOpcion();
        switch (opcion) {
            case 1:
                System.out.println("\n\n\n\n\n\n");
                pantalla++;
                break;
            case 2:
                System.out.println("\n\n\n\n\n\n");
                pantalla = 6;
                break;
        }

    }
    
    private void cantidadDeJugadores() {
        System.out.println("\tJugadores\n\n");
        System.out.println("1. 1 Player");
        System.out.println("2. 2 Players");
        System.out.println("3. 3 Players");
        System.out.println("4. 4 Players");
        System.out.println("5. exit");
        System.out.print("\nopcion: ");

        int opcion = validarOpcion();
        if (opcion != 0) {
            if (opcion != 5) {
                cantJugadores = opcion;
                pantalla++;
            } else {
                pantalla = 6;
            }
        }
    }

    private void setPlayers() {
        List<Player> players = new ArrayList<>();
        System.out.println("\n\n\n\n\n\n");
        for (int i = 0; i < cantJugadores; i++) {
            String name;
            Player player;
            System.out.println("Escriba el nombre del Jugador " + (i + 1));
            name = scanner.nextLine();
            player = new Player(name);
            players.add(player);
            System.out.println("\n\n\n\n\n\n");
        }
        this.players = players;
        pantalla++;
    }

    private void seleccionarNivel() {

        System.out.println("\tSeleccionar Dificultad\n\n");
        System.out.println("1. Facil (numero del 1 al 10)");
        System.out.println("2. Medio (numero del 1 al 50)");
        System.out.println("3. Dificil (numero del 1 al 100)");
        System.out.println("4. Exit");
        System.out.print("\nOpcion: ");

        int opcion = validarOpcion();
        switch (opcion) {
            case 1:
                this.dificultad = opcion;
                this.number = Math.abs((int) (Math.random() * 10));
                pantalla++;
                break;
            case 2:
                this.dificultad = opcion;
                this.number = Math.abs((int) (Math.random() * 50));
                pantalla++;
                break;
            case 3:
                this.dificultad = opcion;
                this.number = Math.abs((int) (Math.random() * 100));
                pantalla++;
                break;
            case 4:
                pantalla = 6;
                break;
        }
    }
    
    private void juego() {
        while (!hasWinner) {
            System.out.println("\tAdivina el numero\n");
            for (Player player : players) {
                int number;
                System.out.printf("Turno de %s, digite un numero: ", player.getName());
                number = validarNumero();
                if (comprobarGanador(number)) {
                    winner = player;
                    aumentarPuntos(player);
                    hasWinner = true;
                    break;
                }
                System.out.println("\nNumero equivocado");
            }
            System.out.println("\n\n\n\n\n\n");
        }
        System.out.printf("El ganador es %s, la cantidad de puntos que tiene es %d\n", winner.getName(), winner.getPoints());
        System.out.println("\nDesean seguir jugando?");
        System.out.println("1. Jugar otra vez");
        System.out.println("2. Selecionar otra Dificultad");
        System.out.println("3. Seleccionar la cantidad de jugadores");
        System.out.println("4. Volver al menu principal");
        System.out.println("5. Salir");
        System.out.print("\nOpcion: ");
        hasWinner = false;
        int opcion = validarOpcion();
        switch (opcion) {
            case 1:
                setNumber();
                break;
            case 2:
                pantalla = 4;
                break;
            case 3:
                pantalla = 2;
                break;
            case 4:
                pantalla = 1;
                break;
            case 5:
                pantalla = 6;
        }
    }

    private int validarOpcion() {
        String opcion = scanner.nextLine();
        if (opcion.matches("^[0-9]+$")) {
            return Integer.parseInt(opcion);
        } else {
            return 0;
        }
    }
    
    private void setNumber(){
        switch (dificultad) {
            case 1:
                this.number = Math.abs((int) (Math.random() * 10));
                break;
            case 2:
                this.number = Math.abs((int) (Math.random() * 100));
                break;
            case 3:
                this.number = Math.abs((int) (Math.random() * 1000));
                break;
        }
    }

    private boolean comprobarGanador(int number) {
        return this.number == number;
    }

    private int validarNumero() {
        String number = scanner.nextLine();
        if (number.matches("^[0-9]+$")) {
            return Integer.parseInt(number);
        } else {
            System.out.println("Digite un numero: ");
            return validarNumero();
        }
    }

    private void aumentarPuntos(Player player) {
        int puntos = 0;
        switch (dificultad) {
            case 1:
                puntos = 10;
                break;
            case 2:
                puntos = 25;
                break;
            case 3:
                puntos = 50;
                break;
        }
        player.setPoints(player.getPoints() + puntos);
    }
}
