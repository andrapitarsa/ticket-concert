package com.andra.ticketconcert.repository;

import com.andra.ticketconcert.model.Concert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Integer> {

    Page<Concert> findByDateGreaterThan(LocalDateTime dateTime, Pageable pageable);
    @Query(value = "SELECT * FROM concert c LEFT JOIN venue v on c.venue_id = v.id WHERE LOWER(c.name) LIKE %:name% OR LOWER(v.location) LIKE %:location%", nativeQuery = true)
    List<Concert> findByNameOrLocation(String name, String location, Pageable pageable);
}
