//import org.apache.hc.core5.http.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ReadURL {

    public static void readURL(String inURL) throws IOException {

//        Using FileWriter instead of PrintStream, hence commentes
//        PrintStream printStream = new PrintStream(new File("log.txt"));
//        PrintStream console = System.out;
//        System.setOut(printStream);

        FileWriter fileWriter = new FileWriter("log.txt", true);

        Reader streamReader = null;
        try {
            URL url = new URL(inURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            testing if status codes write to the file. hence commented
//            String s = Integer.toString(status);
//            System.out.println(status);
//            fileWriter.append(s);
            String status;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine=reader.readLine())!=null){
                content.append(inputLine);
            }
            in.close();



            if (responseCode > 299){
                fileWriter.append("timestamp: " + ZonedDateTime
                        .now( ZoneId.systemDefault() )
                        .format( DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" )) + "status code" + responseCode);
                streamReader = new InputStreamReader(connection.getErrorStream());
                fileWriter.append((CharSequence) streamReader);
            }

            if (responseCode == 200){
                CheckStatus checkStatus = new CheckStatus();
                String response = checkStatus.r(inURL);
                if (response=="false"){
                    fileWriter.append("timestamp: " + ZonedDateTime
                            .now( ZoneId.systemDefault() )
                            .format( DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" )) + "false returned");
                }
            }
        } catch (MalformedURLException e) {
            fileWriter.append("timestamp: " + ZonedDateTime
                    .now( ZoneId.systemDefault() )
                    .format( DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" )) + "malformed url exception");
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            fileWriter.append("timestamp: " + ZonedDateTime
                    .now( ZoneId.systemDefault() )
                    .format( DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" )) + "protocol exception");
            throw new RuntimeException(e);
        } catch (IOException e) {
            fileWriter.append("timestamp: " + ZonedDateTime
                    .now( ZoneId.systemDefault() )
                    .format( DateTimeFormatter.ofPattern( "uuuu.MM.dd.HH.mm.ss" )) + "random runtime exception");
            throw new RuntimeException(e);
        }
        fileWriter.close();
    }

}
