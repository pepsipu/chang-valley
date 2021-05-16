package g5.server;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Creating Remote interface for our application
public interface IServer extends Remote {
    boolean sanityCheck() throws RemoteException;
    UUID addNewPlayer() throws RemoteException;
    void updateState(UUID uuid, Vector3f position, Quaternionf orientation) throws RemoteException;
    Map<UUID, PlayerState> getRemotePlayers() throws RemoteException;
}
