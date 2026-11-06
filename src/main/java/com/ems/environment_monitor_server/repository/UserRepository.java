package com.ems.environment_monitor_server.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.ems.environment_monitor_server.entity.AppUser;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Modifying
    @Transactional
    @Query(
        value = """
            INSERT INTO app_user (username, password_hash, last_login)
            VALUES (:username, :passwordHash, :lastLogin)
            ON CONFLICT (username)
            DO UPDATE SET
                password_hash = EXCLUDED.password_hash,
                last_login = EXCLUDED.last_login
        """,
        nativeQuery = true
    )
    void upsertAdmin(
        @Param("username") String username, 
        @Param("passwordHash") String passwordHash,
        @Param("lastLogin") Instant lastLogin
    );

    AppUser findByUsername(@Param("username") String username);
}
