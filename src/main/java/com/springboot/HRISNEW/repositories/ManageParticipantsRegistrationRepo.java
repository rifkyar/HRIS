/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface ManageParticipantsRegistrationRepo extends CrudRepository<TrainingUserRegistration, Integer> {

    @Query(value = "SELECT TUR.id,\n"
            + "TUR.empl_nik,\n"
            + "TUR.customer_id,\n"
            + "TC.training_title,\n"
            + "TCT.trainer_name,\n"
            + "TMS.value,\n"
            + "TUR.training_transaction_id,TTS.training_time\n"
            + "FROM training_user_registration AS TUR\n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id\n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + "JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id\n"
            + "JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "WHERE TUR.training_transaction_id = ?1", nativeQuery = true)
    public List<Object[]> getDataParticipants(Integer id);

    @Query(value = "SELECT TUR.training_transaction_id\n"
            + " FROM training_user_registration AS TUR\n"
            + " JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
            + " JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id\n"
            + " JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + " JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id\n"
            + " JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "	WHERE TUR.empl_nik = ?1\n"
            + "	AND TUR.training_transaction_id = ?2\n"
            + " LIMIT 1", nativeQuery = true)
    public String getTransactionIdForCheck(Integer nik, Integer transaction_id);

    @Query(value = "SELECT TUR.empl_nik\n"
            + " FROM training_user_registration AS TUR\n"
            + " JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
            + " JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id\n"
            + " JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + " JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id\n"
            + " JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + " WHERE TUR.empl_nik = ?1\n"
            + " AND\n"
            + " TUR.training_transaction_id = ?2\n"
            + "	LIMIT 1", nativeQuery = true)
    public String getRegisteredUserNikForCheck(Integer nik, Integer transaction_id);

    @Query(value = "SELECT TUR.id,\n"
            + "TUR.empl_nik,\n"
            + "TC.training_title,\n"
            + "TMS.value,\n"
            + "TUR.training_transaction_id,\n"
            + "TTS.training_time FROM training_user_registration AS TUR\n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id\n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + "JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id\n"
            + "JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "WHERE TUR.training_transaction_id = ?1 AND TMS.id = 16\n"
            + "OR TUR.training_transaction_id = ?1 AND TMS.id = 17\n"
            + "OR TUR.training_transaction_id = ?1 AND TMS.id = 19\n"
            + "OR TUR.training_transaction_id = ?1 AND TMS.id = 24\n"
            + "OR TUR.training_transaction_id = ?1 AND TMS.id = 8", nativeQuery = true)
    public List<Object[]> getRegisteredUserForCancelation(Integer transaction_id);

    @Query(value = "SELECT TC.training_title,\n"
            + "TTS.training_time,\n"
            + "TUR.empl_nik\n"
            + "FROM training_user_registration TUR\n"
            + "JOIN training_transactions TTS ON TTS.id = TUR.training_transaction_id\n"
            + "JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id\n"
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id\n"
            + "JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id\n"
            + "WHERE TUR.m_other_id = 4 AND TUR.empl_nik = ?1", nativeQuery = true)
    public List<Object[]> getDataTrainingHistory(Integer id);

    @Query(value = "SELECT TUR.customer_id\n"
            + "FROM training_user_registration AS TUR \n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id \n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id \n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "WHERE TUR.training_transaction_id = ?1\n"
            + "AND\n"
            + "TUR.empl_nik = ?2\n"
            + "LIMIT 1", nativeQuery = true)
    public String getCustomerIdbyId(Integer id, Integer nik);

    @Query(value = "SELECT TCT.trainer_name\n"
            + "FROM training_user_registration AS TUR \n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id \n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id \n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "WHERE TUR.training_transaction_id = ?1\n"
            + "LIMIT 1", nativeQuery = true)
    public String getTrainingTrainerNamebyId(Integer id);

    @Query(value = "SELECT TTS.training_time\n"
            + "FROM training_user_registration AS TUR \n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id \n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id \n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n"
            + "WHERE TUR.training_transaction_id = ?1\n"
            + "LIMIT 1", nativeQuery = true)
    public String getTrainingSchedulebyId(Integer id);

//        @Query(value = "SELECT TUR.customer_id,\n" +
//            "TCT.trainer_name,\n" +
//            "TUR.training_transaction_id,\n" +
//            "TTS.training_time\n" +
//            "FROM training_user_registration AS TUR \n" +
//            "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id \n" +
//            "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id \n" +
//            "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id\n" +
//            "WHERE TUR.training_transaction_id = ?1", nativeQuery = true)
//    public String getDetailTrainingbyId(Integer id);
    @Query(value = "SELECT tc.training_title FROM `training_transactions` AS tcs\n"
            + "JOIN training_catalog_transactions AS tcts ON tcts.id = tcs.training_catalog_transaction_id\n"
            + "JOIN training_catalogs AS tc ON tc.id = tcts.training_catalog_id\n"
            + "WHERE tcs.id = ?1", nativeQuery = true)
    public String TrainingTitleForm(Integer id);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 17,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    public void approved(Integer id, Integer nik, Timestamp approvedDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 24,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    public void waitingList(Integer id, Integer nik, Timestamp waitDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 9,\n"
            + "updated_by = ?2,\n"
            + "remark = ?4,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    //Admin Reject
    public void rejected(Integer id, Integer nik, Timestamp rejectedDate, String reason);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 20,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    //User Accept 
    public void accepted(Integer id, Integer nik, Timestamp acceptedDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 18,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE	id = ?1", nativeQuery = true)
    @Transactional
    //User Decline
    public void decline(Integer id, Integer nik, Timestamp declinedDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 20,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    //User Check-In
    public void checkIn(Integer id, Integer nik, Timestamp checkInDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 21,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    //Admin Send-Feedback
    public void sentFeedback(Integer id, Integer nik, Timestamp sentDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 4,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE empl_nik = ?2 AND training_transaction_id = ?1", nativeQuery = true)
    @Transactional
    //User Submit Feedback
    public void submitFeedbackUpdateStatus(Integer transaction_id, Integer nik, Timestamp sentDate);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 19,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3,\n"
            + "UUID = ?4\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    //Admin Send-Detail
    public void sentDetail(Integer id, Integer nik, Timestamp sentDate, String uuid);
//CHECKBOX SELECT ALL
//    @Query(value = "SELECT me.name, me.email, tur.empl_nik, tur.id,tur.m_other_id FROM training_user_registration tur\n"
//            + "join sakura_db.master_employee me on me.empl_nik = tur.empl_nik\n"
//            + "WHERE tur.id in (?1)", nativeQuery = true)
//    public List<Object[]> getName(Integer[] id);
    @Query(value = "SELECT me.name, me.email, tur.empl_nik, tur.id,tur.m_other_id FROM training_user_registration tur\n"
            + "join sakura_db.master_employee me on me.empl_nik = tur.empl_nik\n"
            + "WHERE tur.training_transaction_id = ?1 And tur.empl_nik in (?2)", nativeQuery = true)
    public List<Object[]> getName(Integer transaction_id, Integer[] id);

    @Query(value = "SELECT TC.training_title FROM training_user_registration AS TUR\n"
            + "JOIN training_transactions AS TTS ON TTS.id = TUR.training_transaction_id\n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.id = TTS.training_catalog_transaction_id\n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + "JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id\n"
            + "JOIN tb_m_status AS TMS ON TMS.id = TUR.m_other_id WHERE TUR.training_transaction_id = ?1\n"
            + "LIMIT 1", nativeQuery = true)
    public String getTrainName(Integer id);

    @Query(value = "SELECT tts.training_time FROM `training_transactions` tts \n" 
            + "join training_catalog_transactions tct on tts.training_catalog_transaction_id = tct.id\n" 
            + "join training_catalogs tc on tct.training_catalog_id = tc.id\n" 
            + "where tts.id = ?1", nativeQuery = true)
    public String getTrainTime(Integer id);

//    @Modifying
//    @Query(value = "UPDATE training_user_registration\n"
//            + "SET m_other_id = 9,\n"
//            + "updated_by = ?2,\n"
//            + "updated_date = ?3\n"
//            + "WHERE id IN (?1)", nativeQuery = true)
//    @Transactional
//    //Admin Reject
//    public void bulkReject(Integer[] id, Integer nik, Timestamp sentDate);
    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 9,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE training_transaction_id = ?4 AND empl_nik IN (?1)", nativeQuery = true)
    @Transactional
    //Admin Reject
    public void bulkReject(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 17,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE training_transaction_id = ?4 AND empl_nik IN (?1)", nativeQuery = true)
    @Transactional
    //Admin Reject
    public void bulkAccept(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);

    @Modifying
    @Query(value = "UPDATE training_user_registration\n"
            + "SET m_other_id = 24,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE training_transaction_id = ?4 AND empl_nik IN (?1)", nativeQuery = true)
    @Transactional
    //Admin Reject
    public void bulkWaiting(Integer[] id, Integer nik, Timestamp sentDate, Integer transaction_id);
}
