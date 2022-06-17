/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextPane;

/**
 *
 * @author LAPTOP
 */
public class ChatMessengerSocket {
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private JTextPane jTextPane;

    public ChatMessengerSocket(Socket socket, JTextPane jTextPane, String name) throws IOException {
        this.socket = socket;
        this.jTextPane = jTextPane;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        receive(name);
    }
    
    
    private void receive(String name){
        Thread thread = new Thread(){
            public void run(){
                while(true){
                    try {
                        String line = bufferedReader.readLine();
                        if(line != null){
                            jTextPane.setText(jTextPane.getText()+ "\n"+name+" : " +line);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
    
    public void send(String text){
        String send_message = jTextPane.getText();
        jTextPane.setText(send_message +"\nSent: "+ text);
        printWriter.println(text);
        printWriter.flush();
    }
    
    public void close(){
        try {
            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
