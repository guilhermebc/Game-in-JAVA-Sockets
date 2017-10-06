/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ludusbraille.LudusBrailleGame;

/**
 *
 * @author guilhermecosta
 */
public class Server {

    private ServerSocket server;
    public List<PrintWriter> writersList = new ArrayList<PrintWriter>();

    public Server() {
        try {
            server = new ServerSocket(8000);
            System.out.println("Servidor aguardando conexão dos clientes...");
            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado com o servidor!");
                
                PrintWriter writer = new PrintWriter(socket.getOutputStream()); 
                writersList.add(writer); //adicionando clientes
                System.out.println("Quantidade de clientes: " + writersList.size());

                if(writersList.size() == 2){
                    System.out.println("lista de writers para implementar turno: " + writersList.toString());
//                    System.out.println("lista de writers: " + writersList.toString());

//                    System.out.println("lista de writers: " + writersList.get(1));
//                    System.out.println("lista de writers: " + writersList.get(0));
                }
                
                new Thread(new ClientListener(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Não foi possível estabelecer conexão com o servidor!");
        }
    }

    private class ClientListener implements Runnable {

        private Scanner reader;
        
        public ClientListener(Socket s){
            try {
                reader = new Scanner(s.getInputStream());
            } catch (Exception e) {}
        }

        @Override
        public void run() {
            String text;
            while ((text = reader.nextLine()) != null) { //lendo dos clientes
                for (PrintWriter outputWriter : writersList) { //escrevendo para os clientes
                    System.out.println(text);
                    outputWriter.println(text);
                    outputWriter.flush();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
