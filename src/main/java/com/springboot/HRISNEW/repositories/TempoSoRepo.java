/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TempSo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface TempoSoRepo extends CrudRepository<TempSo, Integer>{
    
    @Query(value = "SELECT * FROM temp_so", nativeQuery = true)
    public List<TempSo> findAllSoPiloting();
}
