/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
* Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */

import Calculator.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import java.util.*;
 
public class StartClient {
    private static Calc calcObj;
    
    /**
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        try {
            // crear e inicializar el ORB
	    ORB orb = ORB.init(args, null);
            
            // Obtener el contexto de nomenclatura raíz
	    org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            
            // Utilice NamingContextExt en lugar de NamingContext. Esto es 
            // parte del Servicio de nomenclatura interoperable. 
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            // resolver la referencia de objeto en nomenclatura
	    calcObj = (Calc) CalcHelper.narrow(ncRef.resolve_str("Calculator"));

            while(true) {
                // Pedir información y leerla
                System.out.println("------------------------------------------");
                System.out.println("Introduce los parametros en formato [operator][sp][operand1][sp][operand2]."
                        + "\nPor ejemplo: + 1 2");
                Scanner c=new Scanner(System.in);
		String input = c.nextLine();
                
                // if the command is exit, request the server to shutdown
                if (input.toLowerCase().equals("Salir")) {
                    calcObj.exit();
                    break;
                }
                
                // test the input
                String[] inputParams = input.split(" ");
                if (inputParams.length != 3) {
                    System.out.println("Excepceion del cliente: Numero de parametro errorneo. Prueba de nuevo...");
                    continue;
                }
                int operatorCode;
                int operand1;
                int operand2;
                
                // set calculation type
                if (inputParams[0].equals("+")) {
                    operatorCode = 1;
                }
                else if (inputParams[0].equals("-")) {
                    operatorCode = 2;
                }
                else if (inputParams[0].equals("*")) {
                    operatorCode = 3;
                }
                else if (inputParams[0].equals("/")) {
                    operatorCode = 5;
                }
                else if (inputParams[0].equals("R")) {
                    operatorCode = 4;
                }
                else {
                    System.out.println("Excepceion del cliente: Operacion no reconocida. Prueba de nuevo...");
                    continue;
                }
                
                 //    Los operandos de entrada de prueba son enteros
                try {
                    operand1 = Integer.parseInt(inputParams[1]);
                    operand2 = Integer.parseInt(inputParams[2]);
                }
                catch (NumberFormatException e) {
                    System.out.println("Excepceion del cliente: Formato de numeros erroneos. Prueba de nuevo...");
                    continue;
                }
                
                
                if (operatorCode == 4 && operand2 == 0) {
                    System.out.println("Excepceion del cliente: No se puede dividir entre 0. Prueba de nuevo...");
                    continue;
                }
                
                
		int result = calcObj.calculate(operatorCode, operand1, operand2);
                String resultDisplay = "";
                if (result == Integer.MAX_VALUE) {
                    resultDisplay = "Excepceion del cliente: Tiene que ser un interger overflow. Prueba de nuevo...";
                }
                else if (result == Integer.MIN_VALUE) {
                    resultDisplay = "Excepceion del cliente: Tiene que ser un integer underflow. Prueba de nuevo...";
                }
                else {
                    resultDisplay = String.valueOf(result);
                }
		System.out.println("The result is: " + resultDisplay);
            }
        }
        catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
