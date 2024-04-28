package com.andra.ticketconcert.model.base;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedBy
    private String createBy;

    private LocalDateTime createAt;

    @LastModifiedBy
    private String modifyBy;

    @LastModifiedDate
    private LocalDateTime modifyAt;

    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        if (this.createAt == null)
            this.createAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public BaseModel(Integer id, LocalDateTime createAt, LocalDateTime modifyAt, Boolean isDeleted) {
        this.id = id;
        this.createAt = createAt;
        this.modifyAt = modifyAt;
        this.isDeleted = isDeleted;
    }
}
