package com.company;

import java.util.Scanner;

public class Player {
    boolean turn;
    String server_response;
    String player_send_message;
    String color;
    String board;
    String x_input;
    String y_input;
    String piece_input;
    Scanner player_scanner = new Scanner(System.in);

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void ask_color(){
        System.out.print("Please enter a color to play as: ");
        this.color = String.valueOf("color set" + "," + player_scanner.nextLine());
    }

    public void ask_x(){
        System.out.print("Please enter an X coordinate: ");
        this.x_input = String.valueOf(player_scanner.nextLine());
    }

    public void ask_y(){
        System.out.print("Please enter an y coordinate: ");
        this.y_input = String.valueOf(player_scanner.nextLine());
    }

    public void ask_piece(){
        System.out.print("Please type a piece name: ");
        this.piece_input = player_scanner.nextLine();
    }

    public String getPlayer_send_message() {
        return player_send_message;
    }

    public void setPlayer_send_message(String player_send_message) {
        this.player_send_message = player_send_message;
    }

    public Player(){

    }

}
