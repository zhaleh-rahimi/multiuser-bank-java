package server.parser;

import java.util.ArrayList;

/**
 * Created by DOTIN SCHOOL 4 on 9/13/2016.
 */
public class Core {

    public int port;
    public String outLog;
    public ArrayList<Deposit> deposits;

    public int getPort() {
        return port;
    }


    public String getOutLog() {
        return outLog;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    /*public void setDeposits(ArrayList<Deposit> deposits) {
        for (Deposit deposit : deposits) {
            this.deposits.put(deposit.getId(), deposit);
        }
    }*/

    @Override
    public String toString() {
        return port + ", " + deposits + ", " + outLog;
    }
}
