import java.util.ArrayList;

public class Wheel extends Thread {
    
    private int m_capacity, m_onBoardCount, m_maxWaitTime;
    private ArrayList<Player> m_players;
    public boolean isSleep = true;
    private Operator m_operator;
    
    public Wheel(int capacity, int maxWaitTime ,Operator operator) {
        m_capacity = capacity; m_onBoardCount = 0; m_maxWaitTime = maxWaitTime;
        m_players = new ArrayList<Player>();
        this.m_operator = operator;
    }
    
    public void run() {
                try {
                	
                	this.m_operator.result += "Wheel start sleep \n";
                    System.out.println("Wheel start sleep");
                    System.out.println();
                    sleep(m_maxWaitTime);
                    isSleep = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }
    
    /**
     * adds a player thread to the list of on-board players.
     * @param id
     * @param waitingTime
     */
    public boolean load_player(Player player) {
        if(m_onBoardCount < m_capacity) {
            m_players.add(player);
            player.setOnBoard(true);
            m_onBoardCount++;
            
            this.m_operator.result += "Player " + player.getPlayerId() + " on board, capacity: " + m_onBoardCount +"\n";
            System.out.println("Player " + player.getPlayerId() + " on board, capacity: " + m_onBoardCount);
            return true;
        } else {
            isSleep = false;
            return false;
        }
    }
    
    /**
     * updates the state of on-board threads to ride-complete.
     */
    public void run_ride() {
    	
    	this.m_operator.result += "Threads in this ride are: \n";
        System.out.println("Threads in this ride are: ");
        for(Player player : m_players) {
            if(player.isOnBoard()) {
            	
            	this.m_operator.result += "" + player.getPlayerId() + ", \n";
                System.out.print("" + player.getPlayerId() + ", ");
            }
        }
        
        this.m_operator.result += "\n \n";
        System.out.println();
        System.out.println();
    }
    
    /**
     * empties the list of on-board players and puts the wheel to sleep until the next ride is run.
     */
    public void end_ride() {
        for(Player player : m_players) {
            if(player.isOnBoard()) {
                player.setRideComplete(true);
            }
        }
        m_players = new ArrayList<Player>();
        m_onBoardCount = 0;
        try {
            sleep(m_maxWaitTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public int getBoardCount() {
        return m_onBoardCount;
    }
    
    public void increaseBoardCount() {
        m_onBoardCount++;
    }

}
