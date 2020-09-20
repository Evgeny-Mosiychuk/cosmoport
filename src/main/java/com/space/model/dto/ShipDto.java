package com.space.model.dto;

import com.space.model.ShipType;
import com.space.model.dao.Ship;
import java.util.Date;
import java.util.Optional;

public class ShipDto {
  private Long id;
  private String name;
  private String planet;
  private ShipType shipType;
  private Date prodDate;
  private Boolean isUsed;
  private Double speed;
  private Integer crewSize;
  private double rating;

  public ShipDto(Ship ship) {
    this.id = Optional.ofNullable(ship.getId()).orElse(null);
    this.name = Optional.ofNullable(ship.getName()).orElse(null);
    this.planet = Optional.ofNullable(ship.getPlanet()).orElse(null);
    this.shipType = Optional.ofNullable(ship.getShipType()).orElse(null);
    this.prodDate = Optional.ofNullable(ship.getProdDate()).orElse(null);
    this.isUsed = Optional.of(ship.isUsed()).orElse(null);
    this.speed = Optional.of(ship.getSpeed()).orElse(null);
    this.crewSize = Optional.of(ship.getCrewSize()).orElse(null);
    this.rating = Optional.of(ship.getRating()).orElse(null);
  }

  public ShipDto() {
  }

  public ShipDto(Long id, String name, String planet, ShipType shipType, Date prodDate,
                 Boolean isUsed, Double speed, Integer crewSize, double rating) {
    this.id = id;
    this.name = name;
    this.planet = planet;
    this.shipType = shipType;
    this.prodDate = prodDate;
    this.isUsed = isUsed;
    this.speed = speed;
    this.crewSize = crewSize;
    this.rating = rating;
  }

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

  public Boolean getIsUsed() {
    return isUsed;
  }

  public Double getSpeed() {
    return speed;
  }

  public Integer getCrewSize() {
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

  public void setIsUsed(Boolean used) {
    isUsed = used;
  }

  public void setSpeed(Double speed) {
    this.speed = speed;
  }

  public void setCrewSize(Integer crewSize) {
    this.crewSize = crewSize;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }
}
