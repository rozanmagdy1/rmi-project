import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Server implements Encyclopedia {

    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            Encyclopedia stub = (Encyclopedia) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(3000);
            registry.bind("Encyclopedia", stub);

            System.out.println("Server started...");
        } catch (AlreadyBoundException | RemoteException e) {
            System.err.println("RMI Server exception: " + e.getMessage());
        }
    }


    @Override
    public int count(String text) throws RemoteException {
        return text.length();
    }

    @Override
    public String repeatedWords(String text) throws RemoteException {
        String[] words = text.split(" ");
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            if (freq.containsKey(word)) {
                freq.put(word, freq.get(word) + 1);
            } else {
                freq.put(word, 1);
            }
        }
        String[] repeated = freq.keySet().stream()
                .filter(w -> freq.get(w) > 1)
                .toArray(String[]::new);
        Arrays.sort(repeated);
        return String.join(" ", repeated);
    }

    @Override
    public String longest(String text) throws RemoteException {
        String[] words = text.split(" ");
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    @Override
    public String shortest(String text) throws RemoteException {
        String[] words = text.split(" ");
        String shortest = null;
        for (String word : words) {
            if (shortest == null || word.length() < shortest.length()) {
                shortest = word;
            }
        }
        return shortest;
    }

    @Override
    public String repeat(String text) throws RemoteException {
        String[] words = text.split(" ");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
        }
        return wordCount.toString();
    }

}