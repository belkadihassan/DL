package com.example.DL.repositories;

import com.example.DL.models.Contact;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    @Query("select c from Contact c where c.numTele1 like concat('%', :tel, '%')")
    List<Contact> searchByNumTele1(@Param("tel") String tel , Sort sort);

    @Query("select c from Contact c where c.numTele2 like concat('%', :tel, '%')")
    List<Contact> searchByNumTele2(@Param("tel") String tel , Sort sort);

    @Query("select c from Contact c where lower(c.nom) like lower(concat('%', :name, '%')) " +
            "or lower(c.prenom) like lower(concat('%', :name, '%')) " +
            "or lower(concat('%', c.nom, '%', c.prenom, '%')) like  lower(:name)" +
            "or lower(concat('%', c.prenom, '%', c.nom, '%')) like lower(:name) ")
    List<Contact> searchByName(@Param("name") String name , Sort sort);
    @Query("select c from Contact c where lower(c.nom) like lower(concat('%', :name, '%')) " +
            "or lower(c.prenom) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.nom, '%', c.prenom, '%')) " +
            "or lower(:name) like lower(concat('%', c.prenom, '%', c.nom, '%'))" +
            " and "+
            "c.numTele1 like concat('%', :tel, '%')"
    )
    List<Contact> searchByNameAndTele1(@Param("name") String name, @Param("tel") String tel , Sort sort);
    @Query("select c from Contact c where lower(c.nom) like lower(concat('%', :name, '%')) " +
            "or lower(c.prenom) like lower(concat('%', :name, '%')) " +
            "or lower(:name) like lower(concat('%', c.nom, '%', c.prenom, '%')) " +
            "or lower(:name) like lower(concat('%', c.prenom, '%', c.nom, '%'))" +
            " and " +
            "c.numTele1 like concat('%', :tel, '%')"
    )
    List<Contact> searchByNameAndTele2(@Param("name") String name , @Param("tel") String tel , Sort sort);
}
