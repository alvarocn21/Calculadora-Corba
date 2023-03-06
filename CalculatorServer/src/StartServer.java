import Calculator.Calc;
import Calculator.CalcHelper;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
 
public class StartServer {
 
    public static void main(String args[]) {
        try{
            // Creando e inicializando el ORB
            ORB orb = ORB.init(args, null);  
            
            // Cogemos la referencia del RootPOA y activamos el POAManager
	    POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
 
            // Creamos el servidor y registramos con el, el ORB
            CalcObject calcObj = new CalcObject();
            calcObj.setORB(orb); 
 
            // Cogemos el objeto de referencia desde el Servidor
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcObj);
            Calc href = CalcHelper.narrow(ref);
 
            // Cogemos el nombre del root
            // El NameService invoca the nombre del servidor
            org.omg.CORBA.Object nsRef =  orb.resolve_initial_references("NameService");
            
            NamingContextExt ncRef = NamingContextExtHelper.narrow(nsRef);
 
            // Unimos el nombre del objeto de refenciabind the Object Reference in Naming
            NameComponent path[] = ncRef.to_name("Calculator");
            ncRef.rebind(path, href);
 
            System.out.println("CalculatorServer esta escuchando...");
 
            // Espando a la invocacion del cliente
            orb.run();
            System.out.println("Etamos fuera");
        } 
        catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
