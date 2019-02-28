/**
 * Created by vmiclott on 25/02/2015.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    public static void main(String[] args) {
        if(args.length!=2){
            System.err.println("Usage: java prog2.nio.ToUpperMain infile outfile");
        }else{
            new Omzetten().omzetten(args[0],args[1]);
        }
    }


}
