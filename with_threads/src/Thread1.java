import java.rmi.RemoteException;

public class Thread1 implements Runnable{
    Encyclopedia analyzer;
    String text;
    int result;

    public Thread1(String text, Encyclopedia analyzer) {
        this.text = text;
        this.analyzer = analyzer;
    }

    @Override
    public void run() {
        try {
            System.out.println("the name of thread is " + Thread.currentThread().getName());
            this.result = analyzer.count(text);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int getResult() {
        return result;
    }

}