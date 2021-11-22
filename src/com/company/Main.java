package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

    private String parseServerResponse(String key){
        Map<String, String> map = new HashMap<>();
        String[] pairs= this.response.split(",");
        for(int i =0; i<pairs.length;i++){
            String pair = pairs[i];
            String[] keyVal = pair.split(":");
            map.put(keyVal[0],keyVal[1]);
        }
        return map.get(key);
    }

    public static void askForMove(Player thisPlayer, Main client){
        thisPlayer.ask_piece();
        thisPlayer.ask_x();
        thisPlayer.ask_y();
        thisPlayer.setPlayer_send_message(thisPlayer.getPiece_input() + "," + thisPlayer.getX_input() + "," + thisPlayer.getY_input() + ",color:" + thisPlayer.getColor());
        try {

        }catch (Exception ex){
            System.out.println("error dicks");
        }
    }

    public static void main(String[] args) {
        Player thisPlayer = new Player();
        String bs_message = "";
        //first set the color we're going to play as
        setColor(thisPlayer);
        String current_board = "";
        Main client = new Main("127.0.0.1",4269);
        try {
            //first send the color
            client.data_out.writeUTF(thisPlayer.color);
            //after we connect here we need to print the board
            client.response = client.in_server.readUTF();
            //parse the servers response for the board, always at position 0
            thisPlayer.setBoard(client.parseServerResponse("board"));
            //assign the board to this player
            System.out.println(thisPlayer.getBoard());
            //parse the servers response for the color, rethink ways to find this. right now position 2
            thisPlayer.setColor(client.parseServerResponse("color"));
            System.out.println("The players color is: " + thisPlayer.getColor());
            client.sock.close();
        }catch (Exception ex){
            System.out.println("error occured trying to read board or send color");
        }
        while(true){
            try {
                client = new Main("127.0.0.1", 4269);
                //retrieve what player is up next;
                client.data_out.writeUTF("type:get_turn");
                client.response = client.in_server.readUTF();
                //client.data_out.writeUTF("type:get_turn2");
                //System.out.println(client.response);
                System.out.println("Player " + client.parseServerResponse("player_turn") + " is up");
                System.out.println(client.parseServerResponse("board"));
                //how best to handle checking for turn?
                if((client.parseServerResponse("player_turn")).equals(thisPlayer.color)){
                    client.sock.close();
                    client = new Main("127.0.0.1", 4269);
                    thisPlayer.ask_piece();
                    thisPlayer.ask_x();
                    thisPlayer.ask_y();
                    thisPlayer.setPlayer_send_message(thisPlayer.getPiece_input() + "," + thisPlayer.getX_input() + "," + thisPlayer.getY_input() + ",color:" + thisPlayer.getColor());
                    bs_message = thisPlayer.player_send_message;
                    try {
                        client.data_out.writeUTF(bs_message);
                        client.response = client.in_server.readUTF();
                    }catch (Exception ex){
                        System.out.println("bull shit");
                    }
                    thisPlayer.setBoard(client.parseServerResponse("board"));
                    System.out.println(thisPlayer.getBoard());
                }else {
                    client.sock.close();
                    client = new Main("127.0.0.1", 4269);
                    client.data_out.writeUTF("type:return_board");
                    client.response = client.in_server.readUTF();
                    thisPlayer.setBoard(client.parseServerResponse("board"));
                    if(thisPlayer.getBoard().equals(current_board)) {
                        System.out.println(thisPlayer.getBoard());
                        System.out.println("Waiting on opponets move");

                    }
                }
            }catch (Exception ex){
                System.out.println(ex);
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            }catch (Exception ex){
                System.out.println("error in sleep");
            }
        }

    }
}
