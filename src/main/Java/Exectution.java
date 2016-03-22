import java.io.IOException;

public class Exectution {
    public static void main(String[] args) {
        try {
            ReadFile dataProcess = new ReadFile();
            dataProcess.process();
        }catch (IOException ex){
            System.out.println("error");
        }

    }
}
