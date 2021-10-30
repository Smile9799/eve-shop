package com.eve_coding.eveshop.repository;

import com.eve_coding.eveshop.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
