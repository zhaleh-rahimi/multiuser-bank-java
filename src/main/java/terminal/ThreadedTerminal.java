package terminal;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by DOTIN SCHOOL 4 on 9/24/2016.
 */
public class ThreadedTerminal {
    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(1);

    public static void main(String[] args) throws IOException, JAXBException, ClassNotFoundException, InterruptedException {

        TerminalSocket terminal1 = new TerminalSocket("src\\main\\java\\files\\terminal.xml");
        TerminalSocket terminal2 = new TerminalSocket("src\\main\\java\\files\\terminal1.xml");
        terminal1.start();
        //countDownLatch.await();
        terminal2.start();
    }
}