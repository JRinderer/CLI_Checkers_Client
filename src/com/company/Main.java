package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/*
Server Response will be a string delimited by Comma
=======RESPONSE=========================
__0-0____R0-1___0-2____R0-3___0-4____R0-5___0-6____R0-7_
__R1-0___1-1____R1-2___1-3____R1-4___1-5____R1-6___1-7__
__2-0____R2-1___2-2____R2-3___2-4____R2-5___2-6____R2-7_
__3-0____3-1____3-2____3-3____3-4____3-5____3-6____3-7__
__4-0____4-1____4-2____4-3____4-4____4-5____4-6____4-7__
__W5-0___5-1____W5-2___5-3____W5-4___5-5____W5-6___5-7__
__6-0____W6-1___6-2____W6-3___6-4____W6-5___6-6____W6-7_
__W7-0___7-1____W7-2___7-3____W7-4___7-5____W7-6___7-7__,
Color,R

We will split this into an array by commas.
0 will always be the board


 */

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

    private String parseServerResponse(int posit){
        String[] tempArr= this.response.split(",");
        return tempArr[posit];

    }

    public static void main(String[] args) {
        Player thisPlayer = new Player();

        //first set the color we're going to play as
        setColor(thisPlayer);
        Main client = new Main("127.0.0.1",4269);
        try {
            //first send the color
            client.data_out.writeUTF(thisPlayer.color);
            //after we connect here we need to print the board
            client.response = client.in_server.readUTF();
            thisPlayer.setBoard(client.parseServerResponse(0));
            System.out.println(thisPlayer.getBoard());
            thisPlayer.setColor(client.parseServerResponse(2));
            System.out.println("The players color is: " + thisPlayer.getColor());
            client.sock.close();
        }catch (Exception ex){
            System.out.println("error occured trying to read board or send color");
        }

    }
}
