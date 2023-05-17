import java.rmi.RemoteException;

public class Thread3 implements Runnable{
    String text;
    Encyclopedia analyzer;
    String result;

    public Thread3(String text,Encyclopedia analyzer){
        this.text = text;
        this.analyzer =analyzer;
    }

    @Override
    public void run() {
        try {
            System.out.println("the name of thread is " + Thread.currentThread().getName());
            this.result = analyzer.longest(text);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResult() {
        return result;
    }
}