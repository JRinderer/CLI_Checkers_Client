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

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getServer_response() {
        return server_response;
    }

    public void setServer_response(String server_response) {
        this.server_response = server_response;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getX_input() {
        return x_input;
    }

    public void setX_input(String x_input) {
        this.x_input = x_input;
    }

    public String getY_input() {
        return y_input;
    }

    public void setY_input(String y_input) {
        this.y_input = y_input;
    }

    public String getPiece_input() {
        return piece_input;
    }

    public void setPiece_input(String piece_input) {
        this.piece_input = piece_input;
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
