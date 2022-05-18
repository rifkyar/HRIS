/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MReportovertime;
import com.springboot.HRISNEW.interfaceservices.ReportOvertimeServiceInterface;
import com.springboot.HRISNEW.repositories.ReportOvertimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class ReportOvertimeServiceImpl implements ReportOvertimeServiceInterface {

    @Autowired
    private ReportOvertimeRepo reportOvertimeRepo;

    @Override
    public MReportovertime saveMReportovertime(MReportovertime report) {
        return reportOvertimeRepo.save(report);
    }

}
