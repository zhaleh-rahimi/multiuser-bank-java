package server;

import com.google.gson.Gson;
import exceptions.FindDepositByIDException;
import exceptions.InitialBalanceException;
import exceptions.UpperBoundException;
import logger.ServerLogger;
import server.parser.Core;
import server.parser.Deposit;
import server.response.Response;
import server.response.TransactionList;
import terminal.ThreadedTerminal;
import terminal.request.Request;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.*;

/**
 * Created by DOTIN SCHOOL 4 on 9/24/2016.
 */
public class MyServerSocket extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());
    private static File coreFile = new File("src\\main\\java\\files\\core.json");
    private static Map<String, BigDecimal> depositBalance = createMap();
    private Socket terminalSocket;

    public MyServerSocket() {
    }

    MyServerSocket(Socket terminalSocket) throws IOException {
        this.terminalSocket = terminalSocket;
    }

    private static Map<String, BigDecimal> createMap() {
        Gson gson = new Gson();
        try {
            Core core = gson.fromJson(new FileReader(coreFile), Core.class);
            depositBalance = new HashMap<String, BigDecimal>();
            for (Deposit deposit : core.getDeposits()) {
                depositBalance.put(deposit.getId(), null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return depositBalance;
    }

    public void run() {
        doLog();
        try {
            ObjectOutputStream out = new ObjectOutputStream(terminalSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(terminalSocket.getInputStream());
            TransactionList responseList = new TransactionList();
            responseList.setResponses(new ArrayList<Response>());
            Response response = new Response();
            Request request = null;
            try {
                request = (Request) in.readObject();
                System.out.println(request.toString());
                LOGGER.log(Level.FINE, "Server received a request from terminal " + request.getTerminalType() + " with id #" + request.getTerminalID());
                responseList.setTerminalID(request.getTerminalID());
                System.out.println("request" + request.getId() + " received");
                setResponse(request, response);
                out.writeObject(response);
                out.flush();
                LOGGER.log(Level.FINE, response.toString());
                LOGGER.log(Level.FINE, responseList.toString());
            } catch (EOFException e) {
                out.close();
                in.close();
                terminalSocket.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doLog() {
        Handler fileHandler = null;
        Formatter simpleFormatter = null;


        try {
            fileHandler = new FileHandler("src\\main\\java\\files\\server.out");
            simpleFormatter = new SimpleFormatter();
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(simpleFormatter);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }
        System.out.println("New Communication Thread Started");
        LOGGER.log(Level.FINE, "New Communication Thread Started");
    }

    private Response setResponse(Request request, Response response) throws IOException {
        response.setId(request.getId());
        response.setDeposit(request.getDeposit());
        response.setType(request.getType());
        try {
            updateDepositBalance(request);
            response.setDepositBalance(depositBalance.get(request.getDeposit()));
            response.setMessage("Transaction successfully done");
        } catch (FindDepositByIDException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            response.setMessage(e.getMessage());

        } catch (InitialBalanceException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            response.setMessage(e.getMessage());
        } catch (UpperBoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private Core readJSONCoreFile() throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(coreFile), Core.class);
    }

    private List<Deposit> extractDepositList() throws IOException {
        return readJSONCoreFile().getDeposits();
    }

    private Deposit FindDepositById(String id) throws IOException {
        for (Deposit deposit : extractDepositList()) {
            if (deposit.getId().equals(id)) {
                return deposit;
            }
        }
        throw new FindDepositByIDException("ID #" + id + " does'nt exist in database");
    }

    private void updateDepositBalance(Request request) throws IOException {
        if (request.getType().equals("deposit")) {
            deposit(request);
        } else if (request.getType().equals("withdraw")) {
            withdraw(request);
        } else {
            throw new FindDepositByIDException("no id");
        }
    }

    private void withdraw(Request request) throws IOException {
        Deposit deposit = FindDepositById(request.getDeposit());
        BigDecimal updatedDeposit = null;
        if (deposit != null) {

            if (depositBalance.get(request.getDeposit()) == null) {
                depositBalance.put(request.getDeposit(), deposit.getInitialBalance());
            }
            synchronized (depositBalance.get(request.getDeposit())) {
            updatedDeposit = depositBalance.get(request.getDeposit()).subtract(request.getAmount());
            try {
                    Thread.sleep(5);
                    if (ThreadedTerminal.cyclicBarrier.await() == 0) {
                        ThreadedTerminal.cyclicBarrier.reset();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
            if (updatedDeposit.compareTo(BigDecimal.ZERO) >= 0) {
                depositBalance.put(request.getDeposit(), updatedDeposit);
            } else {
                throw new InitialBalanceException("initial amount is lower than withdraw requested amount.");
            }
        } else
        {
            throw new FindDepositByIDException("id does'n exist.");
        }
    }

    private void deposit(Request request) throws IOException {
        Deposit deposit = FindDepositById(request.getDeposit());
        if (deposit != null) {
            BigDecimal upperBound = new BigDecimal(String.valueOf(deposit.getUpperBound()));
            BigDecimal updatedDeposit = null;

            if(depositBalance.get(request.getDeposit()) == null){
                depositBalance.put(request.getDeposit(), deposit.getInitialBalance());
            }
            synchronized (depositBalance.get(request.getDeposit())) {
                updatedDeposit = depositBalance.get(request.getDeposit()).add(request.getAmount());
            /*try {
                // Thread.sleep(5);
                if (ThreadedTerminal.cyclicBarrier.await() == 0) {
                    ThreadedTerminal.cyclicBarrier.reset();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }*/
            }
            if (updatedDeposit.compareTo(upperBound) <= 0) {
                depositBalance.put(request.getDeposit(), updatedDeposit);
            } else {
                throw new UpperBoundException("upper bound is lower than deposit requested amount.");
            }
        } else {
            throw new FindDepositByIDException("id does'n exist.");
        }
    }
}
