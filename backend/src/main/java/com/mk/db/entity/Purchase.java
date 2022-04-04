package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mk.db.code.Code;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Entity
@NoArgsConstructor
public class Purchase extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Code division;

    private String orderItemName;

    @Enumerated(EnumType.STRING)
    private Code category;

    @Enumerated(EnumType.STRING)
    private Code status;

    private int price;

    private String description;

    private LocalDateTime regDate;

    private LocalDateTime rentStartDate;

    private LocalDateTime rentEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Purchase(Code division, String orderItemName, Code category, Code status, int price, String description, LocalDateTime regDate, LocalDateTime rentStartDate, LocalDateTime rentEndDate, User user) {
        this.division = division;
        this.orderItemName = orderItemName;
        this.category = category;
        this.status = status;
        this.price = price;
        this.description = description;
        this.regDate = regDate;
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.user = user;
    }

    public void setRentDate(LocalDateTime rentStartDate, LocalDateTime rentEndDate) {
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
    }


    public void setStatus(Code status) {
        this.status = status;
    }



}