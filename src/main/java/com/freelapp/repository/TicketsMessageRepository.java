package com.freelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelapp.model.TicketMessage;
import com.freelapp.model.Tickets;

public interface TicketsMessageRepository extends JpaRepository <TicketMessage, Integer>{

}
