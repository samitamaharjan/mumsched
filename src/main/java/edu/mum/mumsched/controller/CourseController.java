package edu.mum.mumsched.controller;

import edu.mum.mumsched.model.Course;
import edu.mum.mumsched.model.Specialization;
import edu.mum.mumsched.service.ICourseService;
import edu.mum.mumsched.service.IFacultyService;
import edu.mum.mumsched.util.SpecializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IFacultyService iFacultyService;

    @GetMapping("/allCourse")
    public String findAll(Model model){
        model.addAttribute("allCourse", iCourseService.findAll());
        return "course/manage";
    }

    @GetMapping("/newCourse")
    public String addCourseForm(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("prerequisiteList", iCourseService.findAll());
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("specializationList", SpecializationUtil.getSpecializations());
        return "course/form";
    }

    @GetMapping("/updateCourse/{id}")
    public String updateCourseForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("course", iCourseService.findById(id));
        model.addAttribute("prerequisiteList", iCourseService.findAllExcept(id));
        model.addAttribute("facultyList", iFacultyService.findAll());
        model.addAttribute("specializationList", SpecializationUtil.getSpecializations());
        return "course/form";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute @Valid Course course, BindingResult bindingResult){
        bindingResult.hasErrors();
        iCourseService.save(course);
        return "redirect:/allCourse";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourseForm(@PathVariable("id") Integer id){
        iCourseService.delete(id);
        return "redirect:/allCourse";
    }

    @GetMapping("/detailsCourse/{id}")
    public String detailsCourseForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("course", iCourseService.findById(id));
        return "course/details";
    }

}
