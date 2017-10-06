/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import Game.Board;
import Game.Piece;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Clock;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.xml.transform.OutputKeys;
import ludusbraille.LudusBrailleGame;

/**
 *
 * @author guilhermecosta
 */
public class Client {

    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;
    private LudusBrailleGame game;
    private Board board;
    private Piece piece = new Piece(9, 9, true);
    JLabel labelPosition = new JLabel();

    public Client(String host, int port, LudusBrailleGame game) {
        try {
            socket = new Socket(host, port);
            System.out.println("Conexão estabelecida com o servidor na porta: " + port + "\n");
            writer = new PrintWriter(socket.getOutputStream());
            this.game = game;
            new Thread(new ServerListener(socket)).start();

        } catch (IOException e) {
            System.out.println("Não foi possível se conectar com o servidor na porta" + port + "\n");
        }
    }

    public void runProtocol(String messageFromServer) {
        String splittedData[] = messageFromServer.split("#");
        String typeMessage = splittedData[0];

        if (typeMessage.equals("c")) {
            String chatMessage = splittedData[1];
            String playerMessage = splittedData[2];
            
            if(!chatMessage.equals("")){
                LudusBrailleGame.chatArea.append(playerMessage + ": " + chatMessage + "\n");
                LudusBrailleGame.chatArea.setCaretPosition(LudusBrailleGame.chatArea.getDocument().getLength());
            }
           
        } else if (typeMessage.equals("m")) {
            String rowStr = splittedData[1];
            String columnStr = splittedData[2];
            String typeStr = splittedData[3];
            
            int rowNumber = Integer.parseInt(rowStr);
            int columnNumber = Integer.parseInt(columnStr);
            int typeNumber = Integer.parseInt(typeStr);
            
            LudusBrailleGame.logArea.append("Peça lida no protocolo: " + typeNumber + "\n");
            LudusBrailleGame.logArea.setCaretPosition(LudusBrailleGame.logArea.getDocument().getLength());
            game.setLabelData(rowNumber, columnNumber, typeNumber);
            
        }else if(typeMessage.equals("i")) {
            String action = splittedData[1];
            String action2 = splittedData[2];
            
            game.startGame(action);
            
        }else if(typeMessage.equals("r")) {
            String action = splittedData[1];
            String action2 = splittedData[2];
            
            
            game.restartGame(action);
            
        }else if(typeMessage.equals("sr")) {
            String action = splittedData[1];
            String action2 = splittedData[2];
            game.surrenderGame(action);
        } else if(typeMessage.equals("turn")) {
            String action = splittedData[1];
            String action2 = splittedData[2];
            
//            game.isYourTurn();
        }
    }

    private class ServerListener implements Runnable {

        public ServerListener(Socket s) throws IOException {
            reader = new Scanner(s.getInputStream());
        }

        @Override
        public void run() {
            String textFromServer = "";
            while ((textFromServer = reader.nextLine()) != null) {
                System.out.println(textFromServer);
                runProtocol(textFromServer);
            }
        }
    }

    public void sendToServer(String protocol, String msg, String sender, String type) {
        
        if(protocol == "m"){
            writer.println(protocol + "#" + msg + "#" + sender + "#" + type);    
        }else {
            writer.println(protocol + "#" + msg + "#" + sender);    
        }
        writer.flush();
    }
}
