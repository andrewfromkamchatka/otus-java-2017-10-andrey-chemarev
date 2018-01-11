package ru.chemarev.andrey.core;

import org.h2.engine.User;

import javax.persistence.*;

@Entity
@Table(name = "\"Phone\"")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(UserDataSet user, String number) {
        this.user = user;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + getId() +
                ", number='" + number + '\'' +
                '}';
    }

}
