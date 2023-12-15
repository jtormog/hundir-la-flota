import java.util.Arrays;

public class preuna {

    static final char agua ='-';
    static final char lancha ='L';
    static final int barco = 'B';
    static final int crucero = 'C';
    static final int portaviones = 'P';

    static final int tamanyoLancha = 1;
    static final int tamanyoBarco = 3;
    static final int tamanyoCrucero = 4;
    static final int tamanyoPortaviones = 5;

    static int lanchasCantidad = 5;
    static int barcosCantidad = 3;
    static int crucerosCantidad = 1;
    static int portavionesCantidad = 1;

    static char[][] tablero = new char[10][10];
    public static void main(String[] args) {

        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[i].length; j++){
                tablero[i][j] = agua;
            }
        }
        llenarTablero();
        for (int i = 0; i < tablero.length; i++){
            System.out.println(Arrays.toString(tablero[i]));
        }
    }
    
    
    static void llenarTablero() {

        int totalBarcos = (lanchasCantidad + barcosCantidad + crucerosCantidad + portavionesCantidad);
        System.out.println("totalBarcos:" + totalBarcos);
        char tipoBarco = lancha;

        while (totalBarcos > 0) {

            if (tipoBarco == lancha) {
                System.out.println("a");
                lanchaPosicionAleatoria();
                System.out.println("b");
                totalBarcos--;
                tipoBarco = barco;
            } else if (tipoBarco == barco) {
                barcoPosicionAleatoria();
                totalBarcos--;
                tipoBarco = crucero;
            } else if (tipoBarco == crucero) {
                cruceroPosicionAleatoria();
                totalBarcos--;
                tipoBarco = portaviones;
            } else if (tipoBarco == portaviones) {
                portavionesPosicionAleatoria();
                totalBarcos--;
            }
    
        }return;

    }
    static void lanchaPosicionAleatoria(){

    int xEjePosicionAleatoria = 0;
    int yEjePosicionAleatoria = 0;

        while (lanchasCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) {
                    tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = lancha;
                    
            }
            lanchasCantidad--;
                    
        }return;

    }
    static void barcoPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (barcosCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = barco;
            barcosCantidad--;
                    
        }return;

    }
    static void cruceroPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (crucerosCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = crucero;
            
            crucerosCantidad--;
        }return;

    }
    static void portavionesPosicionAleatoria(){

        int xEjePosicionAleatoria = 0;
        int yEjePosicionAleatoria = 0;

        while (portavionesCantidad > 0) {

            xEjePosicionAleatoria = (int) (Math.random() * 10);
            yEjePosicionAleatoria = (int) (Math.random() * 10);

            if (tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] == agua) tablero[xEjePosicionAleatoria][yEjePosicionAleatoria] = portaviones;
            
            portavionesCantidad--;
                    
            }return;

    }
}