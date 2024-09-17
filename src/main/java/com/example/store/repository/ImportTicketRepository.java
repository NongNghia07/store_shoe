package com.example.store.repository;

import com.example.store.entity.Import_Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ImportTicketRepository extends JpaRepository<Import_Ticket, UUID> {

}
