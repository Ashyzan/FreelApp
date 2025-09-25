package com.freelapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.freelapp.model.Cliente;
import com.freelapp.model.Tickets;

public interface TicketsRepository extends JpaRepository <Tickets, Integer>{

}
