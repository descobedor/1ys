package iys.customer.employee.interactions.entity.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * The type Audit base.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditBase {

    /**
     * The Created by.
     */
    @CreatedBy
    protected String createdBy;

    /**
     * The Last modified by.
     */
    @LastModifiedBy
    protected String lastModifiedBy;

    /**
     * The Created date.
     */
    @CreatedDate
    protected LocalDateTime createdDate;

    /**
     * The Last modified date.
     */
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last modified by.
     *
     * @return the last modified by
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets last modified by.
     *
     * @param lastModifiedBy the last modified by
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets last modified date.
     *
     * @return the last modified date
     */
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets last modified date.
     *
     * @param lastModifiedDate the last modified date
     */
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
