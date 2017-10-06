/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import javax.swing.JPanel;
import Game.Piece;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import ludusbraille.LudusBrailleGame;
import static ludusbraille.LudusBrailleGame.chatArea;
/**
 *
 * @author guilhermecosta
 */
public class Board {
    public int[][] boardTable = new int[3][4];
    boolean isOccuped = false;
    public LudusBrailleGame game;
    private Piece pieceType = new Piece(9, 9, true);
   
    public Board(){
    }
    
    public void initBoard(){
        for(int i=0; i<3; i++){
            for(int j=0 ; j<4 ; j++){
                this.boardTable[i][j] = 0;
            }
        }
    }
    
    
//    
//    public void setCell(int row, int column, int type){
//        // if 1, 2, 3 ... board[ij[j]
//        //isOccuped true
//        if(type == 0){
//            // 1 peca coroa
//            this.boardTable[row][column] = 0;
//            
//        }
//        if(type == 1){
//            // 1 peca coroa
//            this.boardTable[row][column] = 1;   
//            
//        }
//        if(type == 2){
//            // 2 peca circular
//            this.boardTable[row][column] = 2;  
//            
//        }
//        if(type == 3){
//            // 3 as duas pecas juntas
//            this.boardTable[row][column] = 3;    
//            
//        }
//        if(type < 1 || type > 3){
//            JOptionPane.showConfirmDialog(null, "Jogada inválida!");
//        }
//    }
    public int getCell(int row, int column){
        return this.boardTable[row][column]; 
    }
}
