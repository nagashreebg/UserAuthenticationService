package com.scaler.userauthenticationservice.repositories.oauth2;

import java.util.Optional;


import com.scaler.userauthenticationservice.models.oauth.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}