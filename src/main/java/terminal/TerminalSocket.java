package terminal;

import logger.TerminalLogger;
import server.response.Response;
import server.response.TransactionList;
import terminal.parser.Terminal;
import terminal.parser.Transaction;
import terminal.request.Request;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by DOTIN SCHOOL 4 on 9/11/2016.
 */
public class TerminalSocket extends Thread {
    private static final Logger LOGGER = Logger.getLogger(TerminalLogger.class.getName());

    private String path;

    public TerminalSocket(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Marshaller marshaller = null;
        Terminal terminal = null;
        File file = new File(path);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Terminal.class);
            JAXBContext jaxbContext1 = JAXBContext.newInstance(TransactionList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext1.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            terminal = (Terminal) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        doLog(terminal.getPath());

        LOGGER.log(Level.SEVERE, "Sending request from " + terminal.getTerminalType() + " with id #" + terminal.getTerminalId() + " to Server");
        Request request = new Request();
        OutputStream os = null;
        try {
            os = new FileOutputStream("src\\main\\java\\files\\response.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TransactionList responses = new TransactionList();
        responses.setResponses(new ArrayList<Response>());
        Response response = null;


        for (Transaction transaction : terminal.getTransactions()) {
            try {
                echoSocket = new Socket(terminal.getIp(), terminal.getPort());
                out = new ObjectOutputStream(echoSocket.getOutputStream());
                in = new ObjectInputStream(echoSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            setRequest(transaction, terminal, request);
            System.out.println(request.toString());
            try {
                out.writeObject(request);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.log(Level.FINE, "request " + request.getId() + " sent to server");
            try {
                response = (Response) in.readObject();
                System.out.println("response" + response.getId() + " received");
                LOGGER.log(Level.FINE, "response " + response.getId() + " received from server");
                System.out.println(response.toString());
                responses.getResponses().add(response);
                out.close();
                in.close();
                echoSocket.close();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        responses.setTerminalID(request.getTerminalID());
        LOGGER.log(Level.INFO, responses.toString());
        try {
            marshaller.marshal(responses, os);
        } catch (JAXBException e) {
            System.exit(1);
        }
        LOGGER.log(Level.SEVERE, "got response for terminal " + terminal.getTerminalId() + " from Server.");
    }

    private void doLog(String path) {
        Handler fileHandler = null;
        Formatter simpleFormatter = null;
        try {

            fileHandler = new FileHandler("src\\main\\java\\files\\" + path);
            simpleFormatter = new SimpleFormatter();
            LOGGER.addHandler(fileHandler);
            fileHandler.setFormatter(simpleFormatter);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }
    }

    private void setRequest(Transaction transaction, Terminal terminal, Request request) {
        request.setId(transaction.getId());
        request.setDeposit(transaction.getDeposit());
        request.setType(transaction.getType());
        request.setAmount(transaction.getAmount());
        request.setTerminalID(terminal.getTerminalId());
        request.setTerminalType(terminal.getTerminalType());
    }

}

