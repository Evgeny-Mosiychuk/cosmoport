package com.space.controller;

import static java.lang.String.format;


import com.space.model.ShipFilters;
import com.space.model.dto.ShipDto;
import com.space.model.exception.BusinessException;
import com.space.model.exception.EntityNotFoundException;
import com.space.model.exception.ValidationException;
import com.space.service.ShipService;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
@Validated
public class ShipController {

  private final ShipService shipService;

  ShipController(ShipService shipService) {
    this.shipService = shipService;
  }

  @GetMapping("/ships")
  public ResponseEntity<List<ShipDto>> getAllShips(
      ShipFilters filters,
      @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
      @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
      @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order) {
    Page<ShipDto> result = shipService.getAllShips(filters, pageNumber, pageSize, order);
    return ResponseEntity.ok(result.getContent());
  }

  @GetMapping("/ships/{id}")
  public ResponseEntity<?> getShip(@PathVariable String id) {
    try {
      Optional<ShipDto> result = shipService.getShipById(id);

      if (!result.isPresent()) {
        throw new EntityNotFoundException("Ship with such id doesn't exist!");
      }

      return ResponseEntity.ok(result.get());

    } catch (BusinessException e) {
      return ResponseEntity.status(HttpStatus.valueOf(e.getErrorCode())).body(e.getMessage());
    }
  }

  @PostMapping("/ships/{id}")
  public ResponseEntity<?> updateShip(@PathVariable String id, @RequestBody ShipDto shipDto) {
    try {
      ShipDto result = shipService.updateShip(id, shipDto);

      return ResponseEntity.ok(result);

    } catch (BusinessException e) {
      return ResponseEntity.status(HttpStatus.valueOf(e.getErrorCode())).body(e.getMessage());
    }
  }

  @PostMapping("/ships")
  public ResponseEntity<?> createShip(@RequestBody ShipDto shipDto) {
    try {
      ShipDto result = shipService.createShip(shipDto);

      return ResponseEntity.ok(result);

    } catch (BusinessException e) {
      return ResponseEntity.status(HttpStatus.valueOf(e.getErrorCode())).body(e.getMessage());
    }
  }

  @GetMapping("/ships/count")
  public ResponseEntity<Long> getShipsCount(
      ShipFilters filters) {
    long result = shipService.getShipsCount(filters);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/ships/{id}")
  public ResponseEntity<String> deleteShip(@PathVariable String id) {
    try {

      shipService.deleteShipById(id);

      return ResponseEntity.ok(format("Ship with %s successfully deleted!", id));

    } catch (BusinessException e) {
      return ResponseEntity.status(HttpStatus.valueOf(e.getErrorCode())).body(e.getMessage());
    }
  }

}
