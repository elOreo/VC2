/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.data;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import vc2.gui.Main;

/**
 *
 * @author Tobias
 */
public final class Loader {

    private Loader(){

    }

    public static Optional<Collection<Adult>> loadData(){
         try {
            URL url = Resources.getResource("adult.data");
            String text = Resources.toString(url, Charsets.UTF_8);
            List<Adult> entries = Splitter.on("\n").omitEmptyStrings().splitToList(text)
                    .stream()
                    .map(entry -> entry.split(", "))
                    //.map(x -> {System.out.println(x.length); return x;})
                    .map(Adult::new)
                    .collect(Collectors.toList());
            return Optional.of(entries);
        } catch (IOException ex) {
            return Optional.empty();
        }
    }
}
