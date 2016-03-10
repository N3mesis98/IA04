package tp2.multCt;

/**
 * Created by Mar on 07/03/2016.
 */

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class ReceiveBhv extends Behaviour {
    private MultAgt parentAgt;
    private int i = 0;

    private final long MAX_DELAY = 10000;
    private final long MIN_DELAY = 500;

    public ReceiveBhv(MultAgt parentAgt){
        this.parentAgt = parentAgt;
    }

    //@Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = this.parentAgt.receive(mt);
        if (message != null) {
            //System.out.println(this.parentAgt.getLocalName()+" : "+message.getContent());

            this.parentAgt.addBehaviour(new MultBhv(this.parentAgt,delayToWait(), message));
        }
        else {
            block();
        }
    }
    //@Override
    public boolean done() {
        return false;
    }


    /**
     * Delay to wait in milliseconds
     * @return long between 500 and 10000 milliseconds (0.5 and 10 seconds)
     */
    private long delayToWait(){
        long delay = 0;
        Random random = new Random();
        delay = (nextLong(random, MAX_DELAY-MIN_DELAY)-MIN_DELAY);
        return delay;
    }


    /**
     * See http://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
     * @param rng
     * @param n
     * @return between 0 and n
     */
    long nextLong(Random rng, long n) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }

}
