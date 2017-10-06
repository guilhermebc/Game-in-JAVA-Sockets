/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

/**
 *
 * @author guilhermecosta
 */
public class Piece {
    private int crownPiece, circlePiece;
    private int type;
    private boolean isValid;
    
    public Piece(int crownPiece, int circlePiece, boolean validatePiece){
        this.crownPiece = crownPiece;
        this.circlePiece = circlePiece;
        this.isValid = true;
    }
    
    public void setCrownPiece(int crownPiece){
        this.crownPiece = crownPiece;
    }
    
    public int getCrownPiece(){
        return this.crownPiece;
    }
    
    public void setCirclePiece(int circlePiece){
        this.circlePiece = circlePiece;
    }
    
    public void resetPieces(){
        this.circlePiece = 9;
        this.crownPiece = 9;
    }
    
    public int getCirclePiece(){
        return this.circlePiece;
    }
    
    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }
    
    public boolean getIsValid(){
        return this.isValid;
    }
    
    public void setTypePiece(int typeNumber){
        this.type = typeNumber;
    }
    
    public int getTypePiece(){
        return this.type;
    }
}
