package com.space.model;

public class ShipFilters {
  private String name;
  private String planet;
  private ShipType shipType;
  private Long after;
  private Long before;
  private Boolean isUsed;
  private Double minSpeed;
  private Double maxSpeed;
  private Integer minCrewSize;
  private Integer maxCrewSize;
  private Double minRating;
  private Double maxRating;

  public ShipFilters() {
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

  public Long getAfter() {
    return after;
  }

  public Long getBefore() {
    return before;
  }

  public Boolean getIsUsed() {
    return isUsed;
  }

  public Double getMinSpeed() {
    return minSpeed;
  }

  public Double getMaxSpeed() {
    return maxSpeed;
  }

  public Integer getMinCrewSize() {
    return minCrewSize;
  }

  public Integer getMaxCrewSize() {
    return maxCrewSize;
  }

  public Double getMinRating() {
    return minRating;
  }

  public Double getMaxRating() {
    return maxRating;
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

  public void setAfter(Long after) {
    this.after = after;
  }

  public void setBefore(Long before) {
    this.before = before;
  }

  public void setIsUsed(Boolean used) {
    isUsed = used;
  }

  public void setMinSpeed(Double minSpeed) {
    this.minSpeed = minSpeed;
  }

  public void setMaxSpeed(Double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  public void setMinCrewSize(Integer minCrewSize) {
    this.minCrewSize = minCrewSize;
  }

  public void setMaxCrewSize(Integer maxCrewSize) {
    this.maxCrewSize = maxCrewSize;
  }

  public void setMinRating(Double minRating) {
    this.minRating = minRating;
  }

  public void setMaxRating(Double maxRating) {
    this.maxRating = maxRating;
  }
}
