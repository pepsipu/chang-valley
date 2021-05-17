package g5.server;

import g5.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Registrar extends Server {
    public static void main(String[] args) {
        // tutorialspoint code /shrug
        try {
            // Instantiating the implementation class
            Server server = new Server();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            IServer stub = (IServer) UnicastRemoteObject.exportObject(server, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(Constants.PORT);

            registry.bind("Server", stub);

            (new Thread(() -> {
                while (true) {
                    server.removeIdlePlayers();
                }
            })).start();

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
