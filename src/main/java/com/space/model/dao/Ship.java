package com.space.model.dao;

import com.space.model.ShipType;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ship {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String planet;
  @Enumerated(EnumType.STRING)
  private ShipType shipType;
  private Date prodDate;
  private boolean isUsed;
  private double speed;
  private int crewSize;
  private double rating;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPlanet() {
    return planet;
  }

  public ShipType getShipType() {
    return shipType;
  }

  public Date getProdDate() {
    return prodDate;
  }

  public boolean isUsed() {
    return isUsed;
  }

  public double getSpeed() {
    return speed;
  }

  public int getCrewSize() {
    return crewSize;
  }

  public double getRating() {
    return rating;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPlanet(String planet) {
    this.planet = planet;
  }

  public void setShipType(ShipType shipType) {
    this.shipType = shipType;
  }

  public void setProdDate(Date prodDate) {
    this.prodDate = prodDate;
  }

  public void setUsed(boolean used) {
    isUsed = used;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public void setCrewSize(int crewSize) {
    this.crewSize = crewSize;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }
}
