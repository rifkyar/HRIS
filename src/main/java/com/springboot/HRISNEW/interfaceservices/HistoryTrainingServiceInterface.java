/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import java.util.List;

/**
 *
 * @author User
 */
public interface HistoryTrainingServiceInterface {

    public List<Object[]> getDataTraining();

    public List<Object[]> getDetailTraining();

    public List<Object[]> getDataParticipants(Integer id);

    public List<Object[]> getUserFeedback(Integer id, Integer nik);

    public List<Object[]> getFeedbackSummaryParticipants(Integer id);

    public List<Object[]> getFeedbackSummary(Integer id, Integer nik);

    public List<Object[]> getAllHistoryAttendance(Integer nik);

    public List<Object[]> getAllhistoryRequested(Integer nik);

}
