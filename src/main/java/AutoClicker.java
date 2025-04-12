import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClicker {

    static boolean clickIzq = false; // Autoclick izquierdo activo o no
    static boolean clickDer = false; // Autoclick derecho activo o no
    static boolean programRunning = true; // Programa activo o no

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();

        // Registrar el listener global
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_X) {
                    clickIzq = !clickIzq;
                    System.out.println("Autoclicker IZQUIERDO " + (clickIzq ? "ACTIVADO" : "DESACTIVADO"));
                } else if (e.getKeyCode() == NativeKeyEvent.VC_C) {
                    clickDer = !clickDer;
                    System.out.println("Autoclicker DERECHO " + (clickDer ? "ACTIVADO" : "DESACTIVADO"));
                } else if (e.getKeyCode() == NativeKeyEvent.VC_P) {
                    programRunning = false;
                    System.out.println("Programa detenido.");
                }
            }

            @Override public void nativeKeyReleased(NativeKeyEvent e) {}
            @Override public void nativeKeyTyped(NativeKeyEvent e) {}
        });

        System.out.println("Autoclicker listo.\nPresiona 'X' para iniciar/detener el click izquierdo\nPresiona 'C' para iniciar/detener el click derecho\nxPresiona 'P' para cerrar el autoclicker.");
        Thread.sleep(2000);

        while (programRunning) {
            if (clickIzq) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }

            if (clickDer) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            }

            Thread.sleep(100); // Para que no explote la PCerda, antes de volver a revisar retrasa la revision 0.1 segundos, de lo contrario se revisaria infinitamente consumiendo el 100% de la CPU
        }

        GlobalScreen.unregisterNativeHook(); // Limpiezap
    }

}
