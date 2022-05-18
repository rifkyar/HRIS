/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingTransactions;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface HistoryTrainingRepo extends CrudRepository<TrainingTransactions, Integer> {

//    @Query(value = "SELECT TTS.id, TCS.training_title, TTS.training_time FROM training_transactions TTS \n"
//            + "JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id \n"
//            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id \n"
//            + "JOIN training_user_registration TUR ON TUR.training_transaction_id = TTS.id \n"
//            + "JOIN tb_m_status MO on MO.id = TUR.m_other_id \n"
//            + "WHERE MO.id = 4 ORDER BY TTS.training_time", nativeQuery = true)
//    public List<Object[]> getDataTraining();

    @Query(value = "SELECT TTS.id, TC.training_title, TTS.training_time, TTS.training_quota, TCTS.trainer_name, TTS.training_location, TTS.training_needs\n"
            + "FROM training_transactions AS TTS\n"
            + "JOIN training_catalog_transactions AS TCT ON TCT.id = TTS.training_catalog_transaction_id\n"
            + "JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id\n"
            + "JOIN training_catalog_trainers AS TCTS ON TCTS.id = TCT.training_catalog_trainer_id\n"
            + "WHERE\n"
            + "TTS.is_active = 1", nativeQuery = true)
    public List<Object[]> getDetailTraining();

    @Query(value = "SELECT TUR.id, TUR.empl_nik, TUR.customer_id, MO.value \n"
            + "FROM training_user_registration TUR \n"
            + "JOIN tb_m_status MO ON MO.id = TUR.m_other_id \n"
            + "WHERE TUR.training_transaction_id = ?1 and MO.value = 'Done'", nativeQuery = true)
    public List<Object[]> getDataParticipants(Integer id);

//    @Query(value = "SELECT TUR.empl_nik, TC.training_title, TFQ.question_content, TFT.type, TFR.feedback_response, TFQ.question_type, TCTS.trainer_name\n"
//            + "FROM training_user_registration AS TUR\n"
//            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
//            + "JOIN training_catalog_transactions AS TCT ON TCT.id = TTS.training_catalog_transaction_id\n"
//            + "JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id\n"
//            + "JOIN training_catalog_trainers AS TCTS ON TCTS.id = TCT.training_catalog_trainer_id\n"
//            + "JOIN training_feedback_response AS TFR ON TFR.empl_nik = TUR.empl_nik\n"
//            + "JOIN training_feedback_questions AS TFQ ON TFQ.id = TFR.feedback_question_id\n"
//            + "JOIN training_feedback_type AS TFT ON TFT.id = TFQ.training_feedback_type_id\n"
//            + "JOIN tb_m_status AS MO ON MO.id = TUR.m_other_id\n"
//            + "WHERE\n"
//            + "TTS.training_catalog_transaction_id = TCT.id\n"
//            + "AND\n"
//            + "TUR.training_transaction_id = ?1\n"
//            + "AND\n"
//            + "TUR.empl_nik = ?2\n"
//            + "AND\n"
//            + "MO.id = 4", nativeQuery = true)
//    public List<Object[]> getUserFeedback(Integer id, Integer nik);
    
    @Query(value = "SELECT TFR.empl_nik, TC.training_title, TFQ.question_content, TFT.type, TFR.feedback_response, TFQ.question_type, TCTR.trainer_name "
            + "FROM training_feedback_response AS TFR JOIN training_transactions AS TT ON TT.id = TFR.training_transaction_id JOIN training_catalog_transactions AS TCT "
            + "ON TCT.id = TT.training_catalog_transaction_id JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers AS TCTR "
            + "ON TCTR.id = TCT.training_catalog_trainer_id JOIN training_feedback_questions AS TFQ ON TFQ.id = TFR.feedback_question_id JOIN training_feedback_type AS TFT "
            + "ON TFT.id = TFQ.training_feedback_type_id WHERE TFR.training_transaction_id = ?1 AND TFR.empl_nik = ?2", nativeQuery = true)
    public List<Object[]> getUserFeedback(Integer id, Integer nik);

//    @Query(value = "SELECT TC.training_title, TCTS.trainer_name, TURS.empl_nik, TURS.customer_id, TFR.feedback_response, TFQ.question_content, TFT.type\n"
//            + "FROM training_user_registration AS TURS\n"
//            + "JOIN training_transactions AS TTS ON TTS.id = TURS.training_transaction_id\n"
//            + "JOIN training_catalog_transactions AS TCT ON TCT.id = TTS.training_catalog_transaction_id\n"
//            + "JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id\n"
//            + "JOIN training_catalog_trainers AS TCTS ON TCTS.id = TCT.training_catalog_trainer_id\n"
//            + "JOIN training_feedback_response AS TFR ON TFR.empl_nik = TURS.empl_nik\n"
//            + "JOIN training_feedback_questions AS TFQ ON TFQ.id = TFR.feedback_question_id\n"
//            + "JOIN training_feedback_type AS TFT ON TFT.id = TFQ.training_feedback_type_id\n"
//            + "JOIN tb_m_status AS MO ON MO.id = TURS.m_other_id\n"
//            + "WHERE TURS.training_transaction_id = ?1\n"
//            + "AND MO.id = 4", nativeQuery = true)
//    public List<Object[]> getFeedbackSummary(Integer id);
//    @Query(value = "SELECT TC.training_title, TCTS.trainer_name, TURS.empl_nik, TURS.customer_id, TFR.feedback_response, TFQ.question_content, TFT.type\n"
//            + "FROM training_user_registration AS TURS\n"
//            + "JOIN training_transactions AS TTS ON TTS.id = TURS.training_transaction_id\n"
//            + "JOIN training_catalog_transactions AS TCT ON TCT.id = TTS.training_catalog_transaction_id\n"
//            + "JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id\n"
//            + "JOIN training_catalog_trainers AS TCTS ON TCTS.id = TCT.training_catalog_trainer_id\n"
//            + "JOIN training_feedback_response AS TFR ON TFR.empl_nik = TURS.empl_nik\n"
//            + "JOIN training_feedback_questions AS TFQ ON TFQ.id = TFR.feedback_question_id\n"
//            + "JOIN training_feedback_type AS TFT ON TFT.id = TFQ.training_feedback_type_id\n"
//            + "JOIN tb_m_status AS MO ON MO.id = TURS.m_other_id\n"
//            + "WHERE TURS.training_transaction_id = ?1\n"
//            + "AND MO.id = 4\n"
//            + "AND TURS.empl_nik = ?2", nativeQuery = true)
//    public List<Object[]> getFeedbackSummary(Integer id, Integer nik);
    
    @Query(value = "SELECT TC.training_title, TCTR.trainer_name, TFR.empl_nik, TFR.customer_id, TFR.feedback_response, TFQ.question_content, TFT.type "
            + "FROM training_feedback_response AS TFR JOIN training_transactions AS TT ON TT.id = TFR.training_transaction_id JOIN training_catalog_transactions AS TCT "
            + "ON TCT.id = TT.training_catalog_transaction_id JOIN training_catalogs AS TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers AS TCTR ON "
            + "TCTR.id = TCT.training_catalog_trainer_id JOIN training_feedback_questions AS TFQ ON TFQ.id = TFR.feedback_question_id JOIN training_feedback_type AS TFT ON "
            + "TFT.id = TFQ.training_feedback_type_id WHERE TFR.training_transaction_id = ?1 AND TFR.empl_nik = ?2", nativeQuery = true)
    public List<Object[]> getFeedbackSummary(Integer id, Integer nik);

    @Query(value = "SELECT empl_nik FROM `training_user_registration` WHERE training_transaction_id = ?1 AND m_other_id = 4", nativeQuery = true)
    public List<Object[]> getFeedbackSummaryParticipants(Integer id);

    @Query(value = "SELECT  TUR.id, TC.training_title, TCTS.trainer_name, TTS.training_time, TTS.training_location, MO.value, TUR.training_transaction_id\n"
            + "FROM training_user_registration TUR\n"
            + "JOIN training_transactions TTS ON TTS.id = TUR.training_transaction_id \n"
            + "JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id \n"
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id \n"
            + "JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id \n"
            + "JOIN tb_m_status MO ON MO.id = TUR.m_other_id \n"
            + "WHERE TTS.is_active = 1 AND TUR.empl_nik IN (SELECT nik FROM requester_information WHERE source_nik IN (SELECT source_nik FROM requester_information WHERE nik = ?1))", nativeQuery = true)
    public List<Object[]> getAllHistoryAttendance(Integer nik);

    @Query(value = "SELECT TUR.id, TC.training_title, TUR.created_date, MO.value \n"
            + "FROM training_user_requests TUR \n"
            + "JOIN training_catalogs TC ON TC.id = TUR.training_catalog_id \n"
            + "JOIN tb_m_status MO ON MO.id = TUR.m_other_id \n"
            + "WHERE TUR.empl_nik = ?1 AND MO.id = 9 OR MO.id = 8", nativeQuery = true)
    public List<Object[]> getAllhistoryRequested(Integer nik);
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_time,TCTG.category\n" 
            + "FROM training_transactions TTS \n" +
            "JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id \n" 
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id \n" 
            + "JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id \n" 
            + "JOIN training_categories TCTG ON TCTG.id = TC.training_category_id\n" 
            + "JOIN training_user_registration TUR ON TUR.training_transaction_id = TTS.id \n" 
            + "JOIN tb_m_status MO on MO.id = TUR.m_other_id WHERE MO.id = 4 AND TTS.is_active = 1 "
            + "GROUP BY TC.training_title ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTraining();
}
