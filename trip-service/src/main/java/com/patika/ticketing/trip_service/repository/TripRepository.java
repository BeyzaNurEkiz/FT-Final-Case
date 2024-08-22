    package com.patika.ticketing.trip_service.repository;

    import com.patika.ticketing.trip_service.entity.Trip;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

    public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
    }
