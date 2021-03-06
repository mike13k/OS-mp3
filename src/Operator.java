import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Operator {
    
    ArrayList<Player> m_players;
    public ArrayList<Player> m_queue;
    Wheel m_wheel;
    String result = "";
    BufferedWriter writer;
    
    
    public Operator(BufferedReader input) throws IOException {
        m_players = new ArrayList<Player>();
        m_queue = new ArrayList<Player>();
        
        String line = input.readLine();
        int waitingTime_wheel = Integer.parseInt(line);
        int numPlayers = Integer.parseInt(input.readLine());
        
        while(((line = input.readLine()) != null)) {
            if(!(line.length() < 1)) {
                int playerID = Integer.parseInt(((line.split(","))[0]).trim());
                int playerWait = Integer.parseInt(((line.split(","))[1]).trim());
                m_players.add(new Player(playerID, playerWait, this));
            }
        }
        
        int wheelCapacity = 5;

        m_wheel = new Wheel(wheelCapacity , waitingTime_wheel , this);
        
        m_wheel.start();
        
        for(Player player : m_players) {
            player.start();
        }
            
        while(m_players.size() > 0) {
            while(m_players.size() > 0) {
                for(Player player : m_players) {
                    if(m_queue.size() > 0) {
                    	
                    	result += "Passing player: " + m_queue.get(0).getPlayerId() + " to operator \n";
                        System.out.println("Passing player: " + m_queue.get(0).getPlayerId() + " to operator");
                        
                        boolean isInserted = m_wheel.load_player(m_queue.get(0));
                        if(isInserted) {
                            m_queue.get(0).setOnBoard(true);
                            m_queue.remove(0);
                        }
                        else {
                            break;
                        }
                        if(m_wheel.getBoardCount() == wheelCapacity) {
                        	
                        	result += "\n Wheel is full, Let's go for a ride \n";
                            System.out.println();
                            System.out.println("Wheel is full, Let's go for a ride");
                            
                            m_wheel.isSleep = false;
                            break;
                        }
                    } else if (!m_wheel.isSleep) {
                        break;
                    }
                }
                if (!m_wheel.isSleep || m_wheel.getBoardCount() == wheelCapacity) {
                    m_wheel.isSleep = false;
                    break;
                }
            }
            
            m_wheel.run_ride();
            
            result += "Wheel start sleep \n";
            System.out.println("Wheel start sleep");
            
            m_wheel.end_ride();
            
            result += "Wheel end sleep \n \n";
            System.out.println("Wheel end sleep");
            System.out.println();
                        
            ArrayList<Player> temp = new ArrayList<Player>();
            for(Player player : m_players) {
                if(!player.isRideComplete()) {
                    temp.add(player);
                }
            }
            m_players = temp;
        }
        
        File output = new File("output.txt");
        this.writer = new BufferedWriter(new FileWriter(output));
        String lines[] = result.split("\n");
        
        for (String string : lines) {
        	if(string != null) {
        		this.writer.write(string);
//        		System.out.println("TEST "+ string);
        		this.writer.newLine();
        	}
        	
        }      
        
        
        
        writer.close();
    }
    
    
    public static void main(String[] args) {
        
        File file = new File("input-1.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            Operator operator = new Operator(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
