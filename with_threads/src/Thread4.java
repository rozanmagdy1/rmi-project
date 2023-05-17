import java.rmi.RemoteException;

public class Thread4 implements Runnable {
    String text;
    Encyclopedia analyzer;
    String result;

    public Thread4(String text,Encyclopedia analyzer){
        this.text = text;
        this.analyzer =analyzer;
    }

    @Override
    public void run() {
        try {
            System.out.println("the name of thread is " + Thread.currentThread().getName());
            this.result = analyzer.shortest(text);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResult() {
        return result;
    }
}