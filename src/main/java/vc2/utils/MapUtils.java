/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vc2.utils;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

/**
 *
 * @author Tobias
 */

public final class MapUtils {
    private MapUtils(){
    
    }
    
    public static <U, V> BiConsumer<Map<U, V>, Map<U, V>> mergeMaps(BinaryOperator<V> merger){
        return (target, source) -> {
            source.forEach((key, value) -> target.merge(key, value, merger));
        };
    }
   
}
