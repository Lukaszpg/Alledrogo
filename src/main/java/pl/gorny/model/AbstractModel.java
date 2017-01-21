package pl.gorny.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class AbstractModel {

    @Column(name = "create_date")
    protected LocalDateTime createDate;

    @Column(name = "update_date")
    protected LocalDateTime updateDate;

    @Column(name = "deleted")
    protected boolean deleted;

    @PrePersist
    protected void onCreate() {
        updateDate = createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }


}
