/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame;

import java.util.List;

/**
 *
 * @author simonescaboro
 */
public class MagicPrinter {
    public static final MagicPrinter instance = new MagicPrinter();
    
    public void printCreatures(List<Creature> list) {
        int i = 0;
        for(Creature c : list) { 
            System.out.println((i+1) + ") " + c.name());
            i++;
        }
    }
    
    public void printEnchantments(List<Enchantment> list) {
        int i = 0;
        for(Enchantment e : list) { 
            System.out.println((i+1) + ") " + e.name());
            i++;
        } 
    }
}
