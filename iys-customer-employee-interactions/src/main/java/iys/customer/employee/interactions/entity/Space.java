package iys.customer.employee.interactions.entity;

import iys.customer.employee.interactions.common.exception.*;
import iys.customer.employee.interactions.entity.audit.AuditBase;
import iys.customer.employee.interactions.model.CustomerSpace;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Space extends AuditBase {

    @Column(name = "references")
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    List<Reference> externalReference;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "is_order")
    private boolean order = false;

    private int orderCount = 0;

    private boolean waitingBills = false;

    private int billsCount = 0;

    private boolean isActive = true;

    public Space() {
    }

    public Space(CustomerSpace customerSpace) {
        if (customerSpace.getReferenceCode() != null) {
            this.externalReference = new ArrayList<>();
            Reference reference = new Reference();
            reference.setCode(customerSpace.getReferenceCode());
            this.externalReference.add(reference);
        }
    }

    protected Space(boolean isActive, boolean isWaitingBills) {
        Space space = new Space();
        space.waitingBills = isWaitingBills;
        space.isActive = isActive;
    }

    public void addReference(String code) {
        if (this.isActive) {
            if (!this.waitingBills) {
                if (code != null) {
                    if (this.externalReference == null) {
                        this.externalReference = new ArrayList<>();
                    }
                    this.externalReference.add(new Reference(code));
                }
            }
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public void removeReference(String reference) {
        if (this.isActive) {
            if (!waitingBills) {
                if (reference != null) {
                    if (this.externalReference != null) {
                        this.externalReference.stream()
                                .filter(filterByCode -> filterByCode.getCode().equals(reference))
                                .findFirst()
                                .ifPresentOrElse(remove -> this.externalReference.remove(remove),
                                        ReferenceNotCreatedException::new);
                    }
                }
            }
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Reference> getExternalReference() {
        return this.externalReference;
    }

    public void setExternalReference(List<Reference> externalReference) {
        this.externalReference = externalReference;
    }

    public boolean isOrder() {
        return this.order;
    }

    public void order() {
        if (this.isActive) {
            if (this.waitingBills) {
                throw new RequestNotAllowedBillsIsWaiting("request not allowed because bills is waiting");
            }
            if (this.order) {
                throw new RequestHasAlreadyBeenMadeException("order request has already been made");
            }
            this.order = true;
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void removeThisSpace() {
        if (this.isActive) {
            this.isActive = false;
        }
    }

    public void cancelOrder() {
        if (this.isActive) {
            if (this.order) {
                this.order = false;
                this.orderCount++;
            }
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public void bills() {
        if (this.isActive) {
            if (this.waitingBills) {
                throw new RequestNotAllowedBillsIsWaiting("request not allowed because bills is waiting");
            }
            if (this.order) {
                this.order = false;
            }
            this.waitingBills = true;
            this.billsCount++;
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public void cancelBills() {
        if (this.isActive) {
            if (!this.waitingBills) {
                throw new RequestNotAllowedException("waiting bills not exists");
            }
            this.waitingBills = false;
        } else {
            throw new SpaceNotCreatedException("Space not created or not active");
        }
    }

    public boolean isWaitingBills() {
        return this.waitingBills;
    }

    public int getBillsCount() {
        return this.billsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return Objects.equals(id, space.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
