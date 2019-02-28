import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vmiclott on 25/02/2015.
 */
public class Omzetten {


    private Iterable<String> readAllLines(InputStream in) {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, Charset.forName("UTF-8")))
        ) {
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Fout bij het inlezen:" + e);
        }
        return result;
    }

    private void write(OutputStream stream, Iterable<String> result) {
        try (Writer writer =
                     new OutputStreamWriter(stream, Charset.forName("UTF-8"))) {
            PrintWriter out = new PrintWriter(writer);
            for (String s : result) {
                out.println(s);
            }
        } catch (IOException e) {
            System.err.println("Fout bij het uitschrijven:" + e);
        }
    }

    public void omzetten (String in, String out) {
        try {
            List<String> result = new ArrayList<>();
            Iterable<String> strings = null;
            if(in==null){
                System.err.println("Geen input gegeven.");
            }else if(in.startsWith("http://") || in.startsWith("https://")){
                InputStream input = new URL(in).openStream();
                strings = readAllLines(input);
            }else if(in.startsWith("-")){
                strings = readAllLines(System.in);
            }else{
                Path inpath = Paths.get(in);
                strings = Files.readAllLines(inpath);
            }

            for (String string : strings) {
                if(string.startsWith("From: ")){
                    result.add("<hr> <strong> From: </strong>" + string.substring(6) + "<br>");
                }
                else if(string.startsWith("Date: ")){
                    result.add("<strong> Date: </strong>" + string.substring(6) + "<br>");
                }
                else if(string.startsWith("Subject: ")){
                    result.add("<strong> Subject: </strong>" + string.substring(9) + "<br> <p>");
                }
                else if(string.isEmpty()){
                    result.add("</p><p>");
                }
                else{
                    result.add(string);
                }
            }

            if(out==null){
                System.err.println("Geen output gegeven.");
            }else if(out.startsWith("-")){
                write(System.out, result);
            }else {
                for (String string : strings) {
                    if (string.startsWith("From: ")) {
                        Path outpath = Paths.get(out);
                        Files.write(outpath, result);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Kon omzetting niet doen wegens een invoer- of uitvoerfout: " + e);
        }
    }
}
