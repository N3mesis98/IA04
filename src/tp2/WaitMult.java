package tp2;

import jade.core.AID;
import jade.lang.acl.StringACLCodec;

/**
 * Created by Mar on 10/03/2016.
 */
public class WaitMult {

    private AID idReceiverMult = null;
    private int termA;
    private int termB;

    public WaitMult(AID idReceiverMult, int termA, int termB) {
        this.idReceiverMult = idReceiverMult;
        this.termA = termA;
        this.termB = termB;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaitMult waitMult = (WaitMult) o;

        return idReceiverMult != null ? idReceiverMult.equals(waitMult.idReceiverMult) : waitMult.idReceiverMult == null;

    }

    @Override
    public int hashCode() {
        return idReceiverMult != null ? idReceiverMult.hashCode() : 0;
    }

    public AID getIdReceiverMult() {
        return idReceiverMult;
    }

    public int getTermA() {
        return termA;
    }

    public int getTermB() {
        return termB;
    }


}
