package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private Socket sock = null;
    private Scanner user_input = null;
    private DataInputStream in_server = null;
    private DataOutputStream data_out = null;
    private String response = null;
    private String piece_name = null;
    private String x = null;
    private String y = null;

    private Main(String ip_addr,int port){
        try{
            sock = new Socket(ip_addr,port);
            user_input = new Scanner(System.in);
            data_out = new DataOutputStream(sock.getOutputStream());
            in_server = new DataInputStream(sock.getInputStream());
/*            //print the board
            this.response = in_server.readUTF();
            System.out.println(this.response);*/

        }catch (Exception ex){
            System.out.println("Error occured");
        }
    }

    private static void setColor(Player p){
        p.ask_color();
    }

    private void askX(Player p){
        p.ask_x();
    }

    private void askY(Player p){
        p.ask_y();
    }

    private void askPiece(Player p){
        p.ask_piece();
    }

    public static void main(String[] args) {
        Player thisPlayer = new Player();
        //first set the color we're going to play as
        setColor(thisPlayer);
        Main client = new Main("127.0.0.1",4269);
        //after we connect here we need to print the board
        try {
            //first send the color
            client.data_out.writeUTF(thisPlayer.color);
            client.response = client.in_server.readUTF();
            System.out.println(client.response);
        }catch (Exception ex){
            System.out.println("error occured trying to read board");
        }

    }
}
