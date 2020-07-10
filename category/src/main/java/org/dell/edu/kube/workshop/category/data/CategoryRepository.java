package org.dell.edu.kube.workshop.category.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {

    @Query("select c from Category c where c.type = ?1")
    List<Category> findByType(String type);
}
