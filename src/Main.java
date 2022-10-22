//import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException{
        ExecutorService executor = Executors.newFixedThreadPool(10);

        ReadURL read = new ReadURL();


//        For some reason TimerTask was not working. Should fix it in the near future. That's why
//        the code is commented.
//        Timer timer = new Timer();
//        TimerTask task = new Schedule("https://esapi.justbooks.in/getCategorywise?page=1&categories=15");
//        timer.scheduleAtFixedRate(task, 300, 300);
//        TimerTask task1 = new Schedule("https://esapi.justbooks.in/getTitleDetails?titleId=281743");
//        timer.scheduleAtFixedRate(task1, 300, 300);

//        It's a infinite loop remember to kill the program
        for (;;) {
//            readURL takes the API enpoint for a GET method. If other methods need to be added,
//            a fresh method must be defined, will add in a future release
            read.readURL("https://esapi.justbooks.in/getCategorywise?page=1&categories=15");
            read.readURL("https://esapi.justbooks.in/getTitleDetails?titleId=281743");
            Thread.sleep(300);
        }


    }
}