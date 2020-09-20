package com.space.service.impl;

import static java.lang.String.format;


import com.space.controller.ShipOrder;
import com.space.model.ShipFilters;
import com.space.model.dao.Ship;
import com.space.model.dto.ShipDto;
import com.space.model.exception.BusinessException;
import com.space.model.exception.EntityNotFoundException;
import com.space.model.exception.ValidationException;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import com.space.util.ShipSpecificationHelper;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ShipServiceImpl implements ShipService {

  private final ShipRepository shipRepository;

  ShipServiceImpl(ShipRepository shipRepository) {
    this.shipRepository = shipRepository;
  }

  @Override
  public Page<ShipDto> getAllShips(ShipFilters filters, int pageNumber, int pageSize,
                                   ShipOrder order) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort
        .by(Sort.Direction.ASC, order.getFieldName()));
    Specification<Ship> spec = ShipSpecificationHelper.buildPpeSpecification(filters);
    return shipRepository.findAll(spec, pageable).map(ShipDto::new);
  }

  @Override
  public Optional<ShipDto> getShipById(String id) throws BusinessException {
    validateId(id);

    return shipRepository.findById(Long.parseLong(id)).map(ShipDto::new);
  }

  @Override
  public ShipDto updateShip(String id, ShipDto shipDto) throws BusinessException {

    validateId(id);

    validateInputShipDto(shipDto, RequestMode.UPDATE);

    Optional<Ship> shipEntity = shipRepository.findById(Long.parseLong(id));

    if (!shipEntity.isPresent()) {
      throw new EntityNotFoundException(format("Ship with id = %s doesn't exist!", id));
    }

    Ship ship = shipEntity.get();

    Optional.ofNullable(shipDto.getName()).ifPresent(ship::setName);
    Optional.ofNullable(shipDto.getPlanet()).ifPresent(ship::setPlanet);
    Optional.ofNullable(shipDto.getShipType()).ifPresent(ship::setShipType);
    Optional.ofNullable(shipDto.getProdDate()).ifPresent(ship::setProdDate);
    Optional.ofNullable(shipDto.getIsUsed()).ifPresent(ship::setUsed);
    Optional.ofNullable(shipDto.getSpeed()).ifPresent(speed -> {
      double roundedSpeed = (Math.round(speed * 100)) / 100.0;
      ship.setSpeed(roundedSpeed);
    });
    Optional.ofNullable(shipDto.getCrewSize()).ifPresent(ship::setCrewSize);

    ship.setRating(evaluateRating(ship));

    return new ShipDto(shipRepository.save(ship));
  }

  @Override
  public ShipDto createShip(ShipDto shipDto) throws BusinessException {

    validateInputShipDto(shipDto, RequestMode.CREATE);

    Ship ship = new Ship();

    Optional.ofNullable(shipDto.getName()).ifPresent(ship::setName);
    Optional.ofNullable(shipDto.getPlanet()).ifPresent(ship::setPlanet);
    Optional.ofNullable(shipDto.getShipType()).ifPresent(ship::setShipType);
    Optional.ofNullable(shipDto.getProdDate()).ifPresent(ship::setProdDate);
    Optional.ofNullable(shipDto.getIsUsed()).ifPresent(ship::setUsed);
    Optional.ofNullable(shipDto.getSpeed()).ifPresent(speed -> {
      double roundedSpeed = (Math.round(speed * 100)) / 100.0;
      ship.setSpeed(roundedSpeed);
    });
    Optional.ofNullable(shipDto.getCrewSize()).ifPresent(ship::setCrewSize);

    ship.setRating(evaluateRating(ship));

    return new ShipDto(shipRepository.save(ship));
  }

  @Override
  public Long deleteShipById(String id) throws BusinessException {
    try {
      validateId(id);

      return shipRepository.deleteById(Long.parseLong(id));
    } catch (EmptyResultDataAccessException e) {
      throw new EntityNotFoundException(format("Ship with id = %s doesn't exist!", id));
    }
  }

  @Override
  public long getShipsCount(ShipFilters filters) {
    Specification<Ship> spec = ShipSpecificationHelper.buildPpeSpecification(filters);
    return shipRepository.count(spec);
  }

  private Double evaluateRating(Ship ship) {
    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    cal.setTime(ship.getProdDate());
    int prodYear = cal.get(Calendar.YEAR);
    int currentYear = 3019;

    return Math.round(
        80 * ship.getSpeed() * (ship.isUsed() ? 0.5 : 1) * 100 / (currentYear - prodYear + 1)) /
        100.0;
  }

  private void validateId(String id) throws ValidationException {
    try {
      long parsedId = Long.parseLong(id);
      if (parsedId <= 0) {
        throw new NumberFormatException("Id must be positive");
      }
    } catch (Exception e) {
      throw new ValidationException(format("Ship id is not valid %s", id));
    }
  }

  private void validateInputShipDto(ShipDto shipDto, RequestMode mode) throws ValidationException {
    if (mode == RequestMode.CREATE) {
      if (shipDto.getName() == null || shipDto.getName().isEmpty()) {
        throw new ValidationException("Ship name must be set!");
      }

      if (shipDto.getPlanet() == null || shipDto.getPlanet().isEmpty()) {
        throw new ValidationException("Planet name must be set!");
      }

      if (shipDto.getIsUsed() == null) {
        shipDto.setIsUsed(false);
      }

      if (shipDto.getProdDate() == null) {
        throw new ValidationException("Prod date must be set!");
      }

      if (shipDto.getCrewSize() == null) {
        throw new ValidationException("Crew size must be set!");
      }

      if (shipDto.getShipType() == null) {
        throw new ValidationException("Ship type must be set!");
      }

      if (shipDto.getSpeed() == null) {
        throw new ValidationException("Speed must be set!");
      }
    }

    if (shipDto.getName() != null && (shipDto.getName().length() > 50 || shipDto.getName().isEmpty())) {
      throw new ValidationException("Ship name must be shorter than 50 characters!");
    }

    if (shipDto.getPlanet() != null && (shipDto.getPlanet().length() > 50 || shipDto.getPlanet().isEmpty())) {
      throw new ValidationException("Planet name must be shorter than 50 characters!");
    }

    if (shipDto.getProdDate() != null) {
      Calendar cal = Calendar.getInstance(TimeZone.getDefault());
      cal.setTime(shipDto.getProdDate());
      int year = cal.get(Calendar.YEAR);

      if (year < 2800 || year > 3019) {
        throw new ValidationException(
            "Year of prod date must be in range from 2800 to 3019 inclusively!");
      }
    }

    if (shipDto.getSpeed() != null && (shipDto.getSpeed() < 0.005 || shipDto.getSpeed() > 0.995)) {
      throw new ValidationException("Speed must be in range from 0.01 to 0.99 inclusively!");
    }

    if (shipDto.getCrewSize() != null && (shipDto.getCrewSize() < 1 || shipDto.getCrewSize() > 9999)) {
      throw new ValidationException("Crew size must be in range from 1 to 9999 inclusively!");
    }
  }

  private enum RequestMode {
    CREATE, UPDATE
  }


}
