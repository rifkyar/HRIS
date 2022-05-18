/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrAnnouncement;
import com.springboot.HRISNEW.interfaceservices.AnnouncementServiceInterface;
import com.springboot.HRISNEW.repositories.AnnouncementRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementServiceInterface{

    @Autowired
    AnnouncementRepo announcementRepo;

    @Override
    public List<Object[]> selectAll() {
        return announcementRepo.selectAll();
    }

    @Override
    public TbTrAnnouncement save(TbTrAnnouncement announcement) {
        return announcementRepo.save(announcement);
    }

    @Override
    public List<TbTrAnnouncement> findAnnouncementById(int id) {
        return announcementRepo.findAnnouncementById(id);
    }

    @Override
    public TbTrAnnouncement editAnnouncementById(int id) {
        return announcementRepo.editAnnouncementById(id);
    }
    
    
}
