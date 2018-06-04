
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tobias
 */
public class Main {
       
    public static void main(String args[]){
        try {
            URL url = Resources.getResource("adult.data");
            String text = Resources.toString(url, Charsets.UTF_8);
            List<String[]> entries = Splitter.on("\n").omitEmptyStrings().splitToList(text)
                    .stream().map(entry -> entry.split(", ")).collect(Collectors.toList());
            entries.forEach(entry -> System.out.println(String.join(",", entry)));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
