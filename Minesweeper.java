//mlester: this is the minesweeper game
//Feb 2, 2015

import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class Minesweeper
{

    //fields
    private boolean[][] matrix;
    private JButton[][] board;
    private JPanel panel;

    //default constructor
    public Minesweeper(){
	panel = new JPanel();
        panel.setLayout(new GridLayout(40,40,1,1));
	panel.setBackground(Color.BLACK);
	matrix = new boolean[40][40];
	Random r = new Random();
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		if (r.nextInt(10)>7){
		    matrix[i][j]=true;
		}
		else
		    matrix[i][j]=false;	
	    }
	}

	board = new JButton[40][40];
	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		board[i][j] = new JButton();
		panel.add(board[i][j]);
	    }
	}	
	

    }

    //method to help the board wrap around
    public static int mod(int x, int d){
	if ((x%d) < 0)
	    return 40+(x%d);
	else
	    return x%d;
    }

    //method to count neighbors
    public int neighborCounter(int i, int j){
	int counter = 0;
	if (matrix[mod(i-1,40)][mod(j-1,40)])
	    counter++;
	if (matrix[mod(i-1,40)][j])
	    counter++;
	if (matrix[mod(i-1,40)][mod(j+1,40)])
	    counter++;
	if (matrix[i][mod(j-1,40)])
	    counter++;
	if (matrix[i][mod(j+1,40)])
	    counter++;
	if (matrix[mod(i+1,40)][mod(j-1,40)])
	    counter++;
	if (matrix[mod(i+1,40)][j])
	    counter++;
	if (matrix[mod(i+1,40)][mod(j+1,40)])
	    counter++;
	return counter;
    }

    /*method that maps the true/false values of matrix to blue/white state 
      of buttons in board*/
    //why do I have to do everything twice??
    public void render(int i, int j) { 
		if (matrix[i][j]){
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(true);
		    board[i][j].setBackground(Color.BLUE);
		    board[i][j].setMargin(new Insets(0,0,0,0));
		    board[i][j].setBorder(null);
		    board[i][j].setText("*");
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(true);
		    board[i][j].setBackground(Color.BLUE);
		    board[i][j].setMargin(new Insets(0,0,0,0));
		    board[i][j].setBorder(null);
		    board[i][j].setText("*");
		    
		}
		else{
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(true);
		    board[i][j].setBackground(Color.WHITE);
		    board[i][j].setMargin(new Insets(0,0,0,0));
		    board[i][j].setBorder(null);
		    board[i][j].setText(Integer.toString(neighborCounter(i,j)));
		    board[i][j].setOpaque(true);
		    board[i][j].setBorderPainted(true);
		    board[i][j].setBackground(Color.WHITE);
		    board[i][j].setMargin(new Insets(0,0,0,0));
		    board[i][j].setBorder(null);
		    board[i][j].setText(Integer.toString(neighborCounter(i,j)));
		}

    }

    //not working fully
    //wont go far enough

    public void renderNeighbors(int i, int j) {
	int x,y;
	render(i,j);
	for (x=-1;x<2;x++){
	    for (y=-1;y<2;y++){
		if (neighborCounter(mod(i+x,40),mod(j+y,40)) == 0 && board[mod(i+x,40)][mod(j+y,40)].isOpaque() == false) {
	    renderNeighbors(mod(i+x,40),mod(j+y,40));
		}
	    }
	}

	render(mod(i-1,40),mod(j-1,40));
	render(mod(i-1,40),j);
	render(mod(i-1,40),mod(j+1,40));
	render(i,mod(j-1,40));
	render(i,mod(j+1,40));
	render(mod(i+1,40),mod(j-1,40));
	render(mod(i+1,40),j);
	render(mod(i+1,40),mod(j+1,40));

    }

    public static void main(String [] args) {
	
	//creates minesweeper model object from default constructor
	final Minesweeper mm = new Minesweeper();
	final JFrame youLose = new JFrame();

	youLose.setSize(200,100);
	youLose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	youLose.setTitle("YOU LOSE");
	youLose.setVisible(false);

	
	
	//when clicked the truth value of the cell will switch
	class ClickListener implements ActionListener{
	    int x,y;

	    public ClickListener(int xval, int yval){
		x=xval;
		y=yval;
	    }


//need a way to render more squares if click on a square touching 0 mines
//some kind of loop that keeps rendering adjacent squares until it gets all the adjacent ones that have 0, like in minesweeper
	    public void actionPerformed(ActionEvent event){
		mm.render(x,y);

		if (mm.matrix[x][y] == true) {
		    youLose.setVisible(true);
		}
		
		else if (mm.neighborCounter(x,y) == 0) {
		    mm.renderNeighbors(x,y);
		    }
	    }
	}

	for (int i=0; i<40; i++){
	    for (int j=0; j<40; j++){
		mm.board[i][j].addActionListener(new ClickListener(i,j));
	    }
	}


	//creates a JFrame
	JFrame frame = new JFrame();
	frame.setSize(600,600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	frame.setTitle("Minesweeper");

	frame.add(mm.panel);
	frame.setVisible(true);
    }

}

