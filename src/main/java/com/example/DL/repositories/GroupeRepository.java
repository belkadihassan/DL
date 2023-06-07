package com.example.DL.repositories;

import com.example.DL.models.Contact;
import com.example.DL.models.Groupe;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe,Long> {
    Optional<Groupe> findByNom(String toLowerCase);
    @Query("select g from Groupe g where g.nom like concat('%', :nom, '%')")
    List<Groupe> searchBynom(@Param("nom") String nom , Sort sort);
}
