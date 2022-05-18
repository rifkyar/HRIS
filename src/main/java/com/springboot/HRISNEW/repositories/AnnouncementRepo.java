/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrAnnouncement;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface AnnouncementRepo extends CrudRepository<TbTrAnnouncement, Integer>{
    
    @Query(value = "SELECT * FROM `tb_tr_announcement` WHERE isactive = 1", nativeQuery = true)
    public List<Object[]> selectAll();
    
    @Query(value = "SELECT * FROM `tb_tr_announcement` WHERE id = ?", nativeQuery = true)
    public List<TbTrAnnouncement> findAnnouncementById(int id);
    
    @Query(value = "SELECT * FROM `tb_tr_announcement` WHERE id = ?1", nativeQuery = true)
    public TbTrAnnouncement editAnnouncementById(int id);
}
