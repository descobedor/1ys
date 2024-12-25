package iys.customer.employee.interactions.assembler;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class InteractionModel extends RepresentationModel<InteractionModel> {

    private String code;
    private List<String> referenceCode;

    private boolean waitingForEmployee;

    private int numInteractions;

    private boolean bills;

    private int numBills;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(List<String> referenceCode) {
        this.referenceCode = referenceCode;
    }

    public boolean isWaitingForEmployee() {
        return waitingForEmployee;
    }

    public void setWaitingForEmployee(boolean waitingForEmployee) {
        this.waitingForEmployee = waitingForEmployee;
    }

    public int getNumInteractions() {
        return numInteractions;
    }

    public void setNumInteractions(int numInteractions) {
        this.numInteractions = numInteractions;
    }

    public boolean isBills() {
        return bills;
    }

    public void setBills(boolean bills) {
        this.bills = bills;
    }

    public int getNumBills() {
        return numBills;
    }

    public void setNumBills(int numBills) {
        this.numBills = numBills;
    }
}
