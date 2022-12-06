package hu.uni.miskolc.u678mf.storeData.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class BasketballPlayer implements Serializable {

    @PrimaryKey
    @NonNull
    private UUID id;
    private String firstName;
    private String lastName;
    private int jerseyNumber;
    private String team;

    public BasketballPlayer() {
        this.id= UUID.randomUUID();
    }

    public BasketballPlayer(String firstName, String lastName, int jerseyNumber, String team) {
        this.id= UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.jerseyNumber = jerseyNumber;
        this.team = team;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "BasketballPlayer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jerseyNumber=" + jerseyNumber +
                ", team='" + team + '\'' +
                '}';
    }
}
