import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Encyclopedia extends Remote {
    public int count(String text) throws RemoteException;
    public String repeatedWords(String text) throws RemoteException;
    public String longest(String text) throws RemoteException;
    public String shortest(String text) throws RemoteException;
    public String repeat(String text) throws RemoteException;

}