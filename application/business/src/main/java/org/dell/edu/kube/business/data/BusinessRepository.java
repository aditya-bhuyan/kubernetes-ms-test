package org.dell.edu.kube.business.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusinessRepository extends CrudRepository<Business,Long> {
    @Query("select b from Business b where b.category = ?1")
    List<Business> findByCategory(Long category);

    @Query("select b from Business b where b.owner = ?1")
    List<Business> findByOwner(String owner);


}