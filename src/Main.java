import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main { // Controller

    public static class Model {
        ArrayList<Integer> playerPositions = new ArrayList<Integer>();
        ArrayList<Integer> compositions = new ArrayList<Integer>();



      char [][] gameBoard = {
              {' ', '|', ' ', '|', ' '},
              {'-', '+','-', '+', '-'},
              {' ', '|', ' ', '|', ' '},
              {'-', '+', '-', '+', '-'},
              {' ', '|', ' ', '|', ' '}
      };

      //this is a getter for the board in case any other class needed it
      public char[][] getBoard() {
        return gameBoard;
      }

      //check winner
      public String checkwinner()
      {
          List topRow = Arrays.asList(1, 2, 3);
          List midRow = Arrays.asList(4, 5, 6);
          List botRow = Arrays.asList(7, 8, 9);
          List leftCol = Arrays.asList(1, 4, 7);
          List midCol = Arrays.asList(2, 5, 8);
          List rightCol = Arrays.asList(3, 6, 9);
          List rightcross = Arrays.asList(1, 5, 9);
          List leftcross = Arrays.asList(7, 5, 3);

          List<List> winning = new ArrayList<List>();
          winning.add(topRow);
          winning.add(midRow);
          winning.add(botRow);
          winning.add(leftCol);
          winning.add(midCol);
          winning.add(topRow);
          winning.add(rightCol);
          winning.add(rightcross);
          winning.add(leftcross);

          for(List l : winning) {

              if(playerPositions.containsAll(l)){
                  return "Congrats U won!! ";
              }else if(compositions.containsAll(l)){
                  return "The Machine won! Sorry... ";
              }else if(playerPositions.size() + compositions.size() == 9) {
                  return " It't a Tie!!";
              }
          }
          return"";

      }

      public void placepiece(int pos, String user)
      {
          char symbol = ' ';
          if ( user.equals("player")){
              symbol = 'X';
              playerPositions.add(pos);
          }else if (user.equals("comp")) {
              symbol = 'O';
              compositions.add(pos);
          }
          switch(pos){
              case 1:
                  gameBoard[0][0] = symbol;
                  break;
              case 2:
                  gameBoard[0][2] = symbol;
                  break;
              case 3:
                  gameBoard[0][4] = symbol;
                  break;
              case 4:
                  gameBoard[2][0] = symbol;
                  break;
              case 5:
                  gameBoard[2][2] = symbol;
                  break;
              case 6:
                  gameBoard[2][4] = symbol;
                  break;
              case 7:
                  gameBoard[4][0] = symbol;
                  break;
              case 8:
                  gameBoard[4][2] = symbol;
                  break;
              case 9:
                  gameBoard[4][4] = symbol;
                  break;
              default:
                  break;

          }
      }

      public boolean placementPossible(int playerpos) {
          return !(playerPositions.contains(playerpos) || compositions.contains(playerpos));
      }

      public void randomCPUMove() {
          Random rand = new Random();
          int compPos = rand.nextInt(9) + 1;
          while(playerPositions.contains(compPos) || compositions.contains(compPos)){
              compPos = rand.nextInt(9) + 1;
          }
          placepiece(compPos, "comp");
      }


    }

    //The View Class
    public static class View {
        //printing the gameboard
        public void PrintGameBoard(char[][] gameBoard)
        {
            for(char[] row : gameBoard) {
                for(char c : row){
                    System.out.print(c);
                }System.out.println();

            }
        }

        public int getInput(String prompt) {
            Scanner scan = new Scanner(System.in);
            System.out.println(prompt);
            int playerpos = scan.nextInt();
            System.out.println(playerpos);
            return playerpos;
        }
    }
    
    public static void main(String[] args) {

        View view = new View();
        Model model = new Model();

        while(true) {
            view.PrintGameBoard(model.getBoard());
            int choice = view.getInput("Input a number from 1…9:");
            while (!model.placementPossible(choice)) {
                choice = view.getInput("Slot occupied. Try again:");
            }
            model.placepiece(choice, "player");

            String result = model.checkwinner();
            if (result.length() > 0) {
                System.out.print(result);
                break;
            }

            model.randomCPUMove();
            view.PrintGameBoard(model.getBoard());

            result = model.checkwinner();
            if (result.length() > 0) {
                System.out.print(result);
                break;
            }
        }
    }
}

