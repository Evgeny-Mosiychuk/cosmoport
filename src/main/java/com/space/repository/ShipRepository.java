package com.space.repository;

import com.space.model.dao.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ShipRepository
    extends PagingAndSortingRepository<Ship, Long>, JpaSpecificationExecutor<Ship>,
    JpaRepository<Ship, Long> {

  Long deleteById(long id);
}
