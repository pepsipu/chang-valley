package g5.changvalley.game;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import g5.Constants;
import g5.changvalley.game.objects.Player;
import g5.server.IServer;
import g5.server.PlayerState;

public class NetworkClient {
    private static IServer server;
    private static UUID uuid;

    public static void construct() throws Exception {
        // Getting the registry
        Registry registry = LocateRegistry.getRegistry(Constants.PORT);

        // Looking up the registry for the remote object
        server = (IServer) registry.lookup("Server");

        assert server.sanityCheck();
        uuid = server.addNewPlayer();
    }

    public static void update(Player player) {
        try {
            server.updateState(uuid, player.getPosition(), player.getOrientation());
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Map<UUID, PlayerState> getRemotePlayers() {
        try {
            return server.getRemotePlayers();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    public static UUID getUuid() {
        return uuid;
    }
}
