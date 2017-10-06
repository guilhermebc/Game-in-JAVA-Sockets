/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;
import javax.swing.JOptionPane;

/**
 *
 * @author guilhermecosta
 */
public class Player {
    private String playerName;
    
    
    public Player(String name){
        this.playerName = name;
    }
    
    public void setPlayer(String player){
        this.playerName = player;
        
    }
    
    public String getPlayer(){
       return this.playerName;
    }   
}
