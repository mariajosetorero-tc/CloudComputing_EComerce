/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package vista;

import bloque1.Ejercicio1_ProcessBuilder;
import bloque1.Ejercicio2_Comunicacion;
import bloque2.Ejercicios3_HilosBasicos;
import bloque2.Ejercicio4_Sincronizacion;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL PSP =====");
            System.out.println("1. Entrega 1: Gestion de procesos");
            System.out.println("2. Entrega 2: Comunicacioón de procesos");
            System.out.println("3. Entrega 3: Calculo de rutas EcoFleet");
            System.out.println("4. Almacen automatizado");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
                opcion = -1;
                continue;
            }

            switch (opcion) {

                case 1:
                    Ejercicio1_ProcessBuilder.ejecutar(sc);
                    break;
                case 2:
                    Ejercicio2_Comunicacion.ejecutar(sc);
                    break;
                case 3:
                    Ejercicios3_HilosBasicos.ejecutar(sc);
                    break;
                case 4:
                    Ejercicio4_Sincronizacion.ejecutar();
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);

        sc.close();

    }
}
