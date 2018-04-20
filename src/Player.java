
public class Player extends Thread {
    
    private int m_id, m_waitingTime;
    private boolean m_isOnBoard = false, m_isRideComplete = false;
    public boolean isSleep = true;
    private Operator m_operator;
    
    
    public Player(int id, int waitingTime, Operator operator) {
        m_id = id;
        m_waitingTime = waitingTime;
        m_operator = operator;
    }
    
    public void run() {
        try {
            sleep(m_waitingTime);
            System.out.println("Player wake up: " + m_id);
            isSleep = false;
            m_operator.m_queue.add(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isOnBoard() {
        return m_isOnBoard;
    }
    
    public void setRideComplete(boolean isRideComplete) {
        m_isRideComplete = isRideComplete;
    }
    
    public void setOnBoard(boolean isOnBoard) {
        m_isOnBoard = isOnBoard;
    }
    
    public boolean isRideComplete() {
        return m_isRideComplete;
    }
    
    public int getPlayerId() {
        return m_id;
    }
    

}
