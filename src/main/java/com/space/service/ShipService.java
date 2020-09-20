package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.ShipFilters;
import com.space.model.dto.ShipDto;
import com.space.model.exception.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ShipService {

  Page<ShipDto> getAllShips(ShipFilters filters, int pageNumber, int pageSize, ShipOrder order);

  Optional<ShipDto> getShipById(String id);

  ShipDto updateShip(String id, ShipDto shipDto);

  ShipDto createShip(ShipDto shipDto);

  Long deleteShipById(String id) throws EntityNotFoundException;

  long getShipsCount(ShipFilters filters);
}
