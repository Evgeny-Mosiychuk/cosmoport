package com.space.util;

import com.space.model.ShipFilters;
import com.space.model.ShipType;
import com.space.model.dao.Ship;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public final class ShipSpecificationHelper {
  private static final String NAME_FIELD_NAME = "name";
  private static final String PLANET_FIELD_NAME = "planet";
  private static final String SHIP_TYPE_FIELD_NAME = "shipType";
  private static final String PROD_DATE_FIELD_NAME = "prodDate";
  private static final String IS_USED_FIELD_NAME = "isUsed";
  private static final String SPEED_FIELD_NAME = "speed";
  private static final String CREW_SIZE_FIELD_NAME = "crewSize";
  private static final String RATING_FIELD_NAME = "rating";

  private ShipSpecificationHelper() {
    throw new IllegalStateException(
        String.format("Class %s is not to be instantiated!", ShipSpecificationHelper.class));
  }

  public static Specification<Ship> nameLike(@NonNull String name) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
        criteriaBuilder.lower(root.get(NAME_FIELD_NAME).as(String.class)).as(String.class),
        "%".concat(name).concat("%").toLowerCase()
    );
  }

  public static Specification<Ship> planetLike(@NonNull String planet) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
        criteriaBuilder.lower(root.get(PLANET_FIELD_NAME).as(String.class)).as(String.class),
        "%".concat(planet).concat("%").toLowerCase()
    );
  }

  public static Specification<Ship> shipTypeEqual(@NonNull ShipType shipType) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(
        criteriaBuilder.lower(root.get(SHIP_TYPE_FIELD_NAME).as(String.class)).as(String.class),
        shipType.toString().toLowerCase()
    );
  }

  public static Specification<Ship> greaterThanOrEqualToProdDate(@NonNull Long after) {
    Date afterDate = new Date(after);
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
        root.get(PROD_DATE_FIELD_NAME),
        afterDate
    );
  }

  public static Specification<Ship> lessThanOrEqualToProdDate(@NonNull Long before) {
    Date beforeDate = new Date(before);
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
        root.get(PROD_DATE_FIELD_NAME),
        beforeDate
    );
  }

  public static Specification<Ship> isUsedEqual(@NonNull Boolean isUsed) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
        .equal(root.get(IS_USED_FIELD_NAME), isUsed);
  }

  public static Specification<Ship> greaterThanOrEqualToSpeed(@NonNull Double minSpeed) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
        root.get(SPEED_FIELD_NAME),
        minSpeed
    );
  }

  public static Specification<Ship> lessThanOrEqualToSpeed(@NonNull Double maxSpeed) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
        root.get(SPEED_FIELD_NAME),
        maxSpeed
    );
  }

  public static Specification<Ship> greaterThanOrEqualToCrewSize(@NonNull Integer minCrewSize) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
        root.get(CREW_SIZE_FIELD_NAME),
        minCrewSize
    );
  }

  public static Specification<Ship> lessThanOrEqualToCrewSize(@NonNull Integer maxCrewSize) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
        root.get(CREW_SIZE_FIELD_NAME),
        maxCrewSize
    );
  }

  public static Specification<Ship> greaterThanOrEqualToRating(@NonNull Double minRating) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(
        root.get(RATING_FIELD_NAME),
        minRating
    );
  }

  public static Specification<Ship> lessThanOrEqualToRating(@NonNull Double maxRating) {
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(
        root.get(RATING_FIELD_NAME),
        maxRating
    );
  }

  public static Specification<Ship> buildPpeSpecification(
      @NonNull ShipFilters shipFilters) {
    Optional<Specification<Ship>> nameSpec = Optional.ofNullable(shipFilters.getName()).map(
        ShipSpecificationHelper::nameLike);
    Optional<Specification<Ship>> planetSpec = Optional.ofNullable(shipFilters.getPlanet()).map(
        ShipSpecificationHelper::planetLike);
    Optional<Specification<Ship>> shipTypeSpec = Optional.ofNullable(shipFilters.getShipType())
        .map(ShipSpecificationHelper::shipTypeEqual);
    Optional<Specification<Ship>> minProdDateSpec = Optional.ofNullable(shipFilters.getAfter())
        .map(ShipSpecificationHelper::greaterThanOrEqualToProdDate);
    Optional<Specification<Ship>> maxProdDateSpec = Optional.ofNullable(shipFilters.getBefore())
        .map(ShipSpecificationHelper::lessThanOrEqualToProdDate);
    Optional<Specification<Ship>> isUsedSpec = Optional.ofNullable(shipFilters.getIsUsed())
        .map(ShipSpecificationHelper::isUsedEqual);
    Optional<Specification<Ship>> minSpeedSpec = Optional.ofNullable(shipFilters.getMinSpeed())
        .map(ShipSpecificationHelper::greaterThanOrEqualToSpeed);
    Optional<Specification<Ship>> maxSpeedSpec = Optional.ofNullable(shipFilters.getMaxSpeed())
        .map(ShipSpecificationHelper::lessThanOrEqualToSpeed);
    Optional<Specification<Ship>> minCrewSizeSpec =
        Optional.ofNullable(shipFilters.getMinCrewSize())
            .map(ShipSpecificationHelper::greaterThanOrEqualToCrewSize);
    Optional<Specification<Ship>> maxCrewSizeSpec =
        Optional.ofNullable(shipFilters.getMaxCrewSize())
            .map(ShipSpecificationHelper::lessThanOrEqualToCrewSize);
    Optional<Specification<Ship>> minRatingSpec = Optional.ofNullable(shipFilters.getMinRating())
        .map(ShipSpecificationHelper::greaterThanOrEqualToRating);
    Optional<Specification<Ship>> maxRatingSpec = Optional.ofNullable(shipFilters.getMaxRating())
        .map(ShipSpecificationHelper::lessThanOrEqualToRating);

    Optional<Specification<Ship>> finalSpec = Stream.of(
        nameSpec,
        planetSpec,
        shipTypeSpec,
        minProdDateSpec,
        maxProdDateSpec,
        isUsedSpec,
        minSpeedSpec,
        maxSpeedSpec,
        minCrewSizeSpec,
        maxCrewSizeSpec,
        minRatingSpec,
        maxRatingSpec
    )
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(Specification::and);

    return finalSpec.orElse(Specification.where(null));
  }
}
