package iys.customer.employee.interactions.entity;

import iys.customer.employee.interactions.entity.audit.AuditBase;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id"}), name = "external_reference")
public class Reference extends AuditBase {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String code;

    public Reference() {
    }

    public Reference(String code) {
        this.code = code;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return Objects.equals(id, reference.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
