package g5.server;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.rmi.RemoteException;
import java.util.*;

// yes, someone can extremely easily impersonate someone else
// no, I don't care lmao

// TODO: maybe add session cookies
public class Server implements IServer {
    Map<UUID, PlayerState> players = new HashMap<>();

    public boolean sanityCheck() throws RemoteException {
        return true;
    }

    // returns a uuid for the new user
    public UUID addNewPlayer() throws RemoteException {
        UUID uuid = UUID.randomUUID();
        players.put(uuid, new PlayerState());
        return uuid;
    }

    public void updateState(UUID uuid, Vector3f position, Quaternionf orientation) throws RemoteException {
        PlayerState playerState = players.get(uuid);
        playerState.updateState(position, orientation);
    }

    public Map<UUID, PlayerState> getRemotePlayers() throws RemoteException {
        return players;
    }

    public void removeIdlePlayers() {
        for (Map.Entry<UUID, PlayerState> player: players.entrySet()) {
            if (!player.getValue().hasHeartbeat()) {

            }
        }
    }
}