/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.traininganddevelopment;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
//import static com.springboot.HRISNEW.controller.traininganddevelopment.TrainingController.myId;
import com.springboot.HRISNEW.entities.TrainingCatalogTrainers;
import com.springboot.HRISNEW.entities.TrainingCatalogTransactions;
import com.springboot.HRISNEW.entities.TrainingCatalogs;
import com.springboot.HRISNEW.entities.TrainingCategories;
import com.springboot.HRISNEW.implementservices.TCSSImpl;
import com.springboot.HRISNEW.implementservices.TCTSImpl;
import com.springboot.HRISNEW.implementservices.TFeedbackServiceImplement;
import com.springboot.HRISNEW.implementservices.TrainingCatalogTransactionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author User
 */
@Controller
public class ManageDataMasterController {

//    @Autowired
//    private TrainingCatalogTrainersRepo trainingCatalogTrainersRepo;
//
//    @Autowired
//    private TrainingCatalogRepo trainingCatalogRepo;
    @Autowired
    private TCTSImpl TCTSI;

    @Autowired
    private TCSSImpl TCSSI;

    @Autowired
    private TFeedbackServiceImplement TFSI;

    @Autowired
    private TrainingCatalogTransactionsServiceImpl TCTransactionsService;

    @Autowired
    private ServiceApi serviceAPI;

//    DISPLAY-------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/catalogTrainers")
    public String viewCatalogTrainers(Model model) {

        List<Object[]> listTrainers = new ArrayList<>();
        for (Object[] object : TCTSI.findtrainersbyactive(1)) {
            Object[] objects = new Object[6];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];

            listTrainers.add(objects);
        }

        model.addAttribute("trainersList", listTrainers);
        return "traininganddevelopment/managedata/catalogTrainers";
    }

    @GetMapping("/addNewTrainer")
    public String addNewTrainerMenu() {

        return "traininganddevelopment/managedata/addNewTrainer";
    }

    @GetMapping(value = "/catalogTraining")
    public String viewCatalogTraining(Model model) {
        List<Object[]> listTraining = new ArrayList<>();
        for (Object[] object : TCSSI.findalltraining()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            listTraining.add(objects);
        }

        model.addAttribute("trainingList", listTraining);
        return "traininganddevelopment/managedata/catalogTraining";
    }

    @GetMapping("/assignTrainer")
    public String ManageTrainingRequestMenu(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        model.addAttribute("empl_nik", id);

        List<Object[]> trainers = new ArrayList<>();
        for (Object[] object : TCTSI.findTrainerAssignment()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            trainers.add(objects);
        }

        List<Object[]> trainerLists = new ArrayList<>();
        for (Object[] object : TCTSI.findTrainerForAssignment()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            trainerLists.add(objects);
        }

        List<Object[]> trainersTraining = new ArrayList<>();
        for (Object[] object : TCTransactionsService.getAllAssignment()) {
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];

            trainersTraining.add(objects);
        }

        List<Object[]> listTraining = new ArrayList<>();
        for (Object[] object : TCSSI.findallactivetrainingForAssignment()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            listTraining.add(objects);
        }

        model.addAttribute("trainersTraining", trainersTraining);
        model.addAttribute("trainingList", listTraining);
        model.addAttribute("trainers", trainers);
        model.addAttribute("trainerLists", trainerLists);
        return "/traininganddevelopment/managedata/assignTrainer";
    }

    @RequestMapping(value = "/trainerSearchName")
    @ResponseBody
    public List<String> SearchTrainerName(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> TrainerName = new ArrayList<String>();
        try {
//            System.out.println("term: " + term);
            List<Object[]> listID = TCTSI.findTrainerForCheck(term);
//            System.out.println("test");
            System.out.println(listID);
            for (Object[] object : listID) {
                Object[] objects = new Object[1];
                objects[0] = object[0];

                TrainerName.add(object[0].toString());
//                System.out.println("data:");
//                System.out.println(object);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
        }

        return TrainerName;
    }

    @RequestMapping(value = "/trainingSearch")
    @ResponseBody
    public List<String> SearchTrainingTitle(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> trainingTitle = new ArrayList<String>();

        try {
//            System.out.println("term: " + term);
            List<Object[]> listTraining = TCSSI.searchByNameandActiveTrainingForCheck(term);
//            System.out.println("test");
//            System.out.println(listTraining);
            for (Object[] object : listTraining) {
                Object[] objects = new Object[4];
                objects[0] = object[0];
                objects[1] = object[1];
                objects[2] = object[2];
                objects[3] = object[3];

                trainingTitle.add(objects[1].toString());
//                System.out.println("data:");
//                System.out.println(object);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
        }

        return trainingTitle;
    }

    @GetMapping(value = "/manageFeedbackForm")
    public String viewFeedbackForm(Model model) {

        List<Object[]> questionLists = new ArrayList<>();
        for (Object[] object : TFSI.findallfeedback()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            questionLists.add(objects);
        }

        model.addAttribute("questions", questionLists);
        model.addAttribute("questionType", TFSI.findAll());
        return "traininganddevelopment/managedata/manageFeedbackForm";
    }

    @GetMapping("/addNewFeedback")
    public String addNewFeedbackForm() {
        return "traininganddevelopment/managedata/addNewFeedbackForm";
    }

    @GetMapping("/editFeedbackForm")
    public String editFeedbackForm(Model model) {

        List<Object[]> questionLists = new ArrayList<>();
        for (Object[] object : TFSI.findallfeedback()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            questionLists.add(objects);
        }

        model.addAttribute("questions", questionLists);
        model.addAttribute("questionType", TFSI.findAll());
        return "traininganddevelopment/managedata/editFeedbackForm";
    }

//    SAVE AND UPDATE--------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/save")
    public String saveTrainer(String myId, String id, Model model, @RequestParam("reqName") String name, @RequestParam("reqStats") String status, @RequestParam("reqTelp") String number, @RequestParam("reqEmail") String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);
        int admin = 0;
        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            admin = Integer.parseInt(id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            admin = Integer.parseInt(id);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int active = 1;
        boolean bool = (active == 1);

        TCTSI.save(new TrainingCatalogTrainers(name, status, number, email, bool, admin, timestamp, admin, timestamp));

        return "redirect:/catalogTrainers";
    }

    @PostMapping("/save/{id}")
    public String updateTrainer(String myId, String id, @PathVariable("id") Integer trainer_id, @RequestParam("origin") Timestamp created_date, @RequestParam("upName") String name, @RequestParam("upStats") String status, @RequestParam("upTelp") String number, @RequestParam("upEmail") String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);
        Integer admin = 0;
        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            admin = Integer.parseInt(id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            admin = Integer.parseInt(id);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        Integer admin = serviceAPI.EmployeeByNik(id).getempl_nik();

        int active = 1;
        boolean bool = (active == 1);

        System.out.println("trainer id : " + trainer_id);
        System.out.println("created date : " + created_date);

        TCTSI.save(new TrainingCatalogTrainers(trainer_id, name, status, number, email, bool, admin, created_date, admin, timestamp));

        return "redirect:/catalogTrainers";
    }

    @PostMapping("/saveTraining")
    public String saveTraining(@RequestParam("reqJudul") String judul, @RequestParam("reqCategory") String nCategory) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int available = 1;
        boolean bool = (available == 1);
        //convert 2x

        Integer cast;
        cast = Integer.valueOf(nCategory);

        TrainingCategories category;
        category = new TrainingCategories(cast);

        TCSSI.save(new TrainingCatalogs(judul, bool, timestamp, timestamp, category));

        return "redirect:/catalogTraining";
    }

    @PostMapping("/saveTraining/{id}")
    public String updateTraining(@PathVariable("id") int idEdit, @RequestParam("upJudul") String judul, 
            @RequestParam("upCategory") Integer nCategory) {
            Integer cast = nCategory;
            TrainingCategories category = new TrainingCategories(cast);
            System.out.println("ID EDIT = " + idEdit);
            System.out.println("RETURN = " + TCSSI.findDataById(idEdit));
            TrainingCatalogs catalogs = TCSSI.findDataById(idEdit);
            catalogs.setTrainingTitle(judul);
            catalogs.setTrainingCategoryId(category);
            TCSSI.save(catalogs);

//            boolean bool = true;
//
//            System.out.println("I Am True");
//            TCSSI.save(new TrainingCatalogs(id, judul, bool, timeAdd6Hours(), category));

//        String compare = "true";
//        if (status.equals(compare)) {
//
//        } 
//        else if (status.equals("false")) {
//            boolean bool = false;
//
//            System.out.println("I Am False");
//
//            Integer cast;
//            cast = nCategory;
//
//            TrainingCategories category;
//            category = new TrainingCategories(cast);
////            TCSSI.save(new TrainingCatalogs(id, judul, bool, timeAdd6Hours(), category));
//        }
        return "redirect:/catalogTraining";
    }

    @PostMapping("/saveAssignment/{admin}")
    public String assignTrainer(@RequestParam("training") Integer training_title, @RequestParam("trainer") Integer trainer_name, @PathVariable("admin") Integer admin) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int active = 1;
        boolean bool = (active == 1);

        TrainingCatalogTrainers trainer;
        trainer = new TrainingCatalogTrainers(trainer_name);

        TrainingCatalogs training;
        training = new TrainingCatalogs(training_title);

        TCTransactionsService.save(new TrainingCatalogTransactions(training, trainer, bool, admin, timestamp, timestamp));

        return "redirect:/assignTrainer";
    }

    @GetMapping("/updateAssignment/{id}/{admin}")
    public String updateAssignTrainer(@RequestParam("upTraining") Integer training_title, @RequestParam("upTrainer") Integer trainer_name, @PathVariable("admin") Integer admin, @PathVariable("id") Integer id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int active = 1;
        boolean bool = (active == 1);

        TCTransactionsService.updateAssignment(training_title, trainer_name, admin, timestamp, id);

        return "redirect:/assignTrainer";
    }

//    DELETE------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/deletebyid/{id}")
    public String deletebyid(String myId, String id, @PathVariable("id") Integer trainer_id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());

        TCTSI.disableTrainer(Integer.parseInt(id), updatedDate, trainer_id);
        return "redirect:/catalogTrainers";
    }

    @PostMapping(value = "/disabletrainingbyid/{id}")
    public String disableTraining(@PathVariable("id") Integer id) {
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        int active = 0;
//        boolean bool = (active == 0);

        TCSSI.disabletraining(id);

        return "redirect:/catalogTraining";
    }
    
    @PostMapping(value = "/activetrainingbyid/{id}")
    public String activeTraining(@PathVariable(value = "id") int id) {
        TrainingCatalogs catalogs = TCSSI.findDataById(id);
        catalogs.setIsAvailable(Boolean.TRUE);
        TCSSI.save(catalogs);
        return "redirect:/catalogTraining";
    }

    @GetMapping("/disableAssignment/{id}/{admin}")
    public String disableTrainerAssignment(@PathVariable("admin") Integer admin, @PathVariable("id") Integer id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        TCTransactionsService.disableAssignment(admin, timestamp, id);

        return "redirect:/assignTrainer";
    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, +7);
         System.out.println("now ==  " + now);
//        System.out.println("calendar = "+calendar);
        System.out.println("pengurangan " + calendar.getTime());
        return calendar.getTime();
    }
}
