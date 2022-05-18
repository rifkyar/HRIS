/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrAnnouncement;
import java.util.List;

/**
 *
 * @author HARRY-PC
 */
public interface AnnouncementServiceInterface {
    
    List<Object[]> selectAll();
    List<TbTrAnnouncement> findAnnouncementById(int id);
    TbTrAnnouncement editAnnouncementById(int id);
    TbTrAnnouncement save(TbTrAnnouncement announcement);
    
}
