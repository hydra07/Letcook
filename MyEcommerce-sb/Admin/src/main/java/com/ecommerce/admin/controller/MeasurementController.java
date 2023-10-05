package com.ecommerce.admin.controller;

import com.ecommerce.library.model.Measurement;
import com.ecommerce.library.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @GetMapping("/measurements")
    public String measurements(Model model , Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        List<Measurement> measurements = measurementService.findAll();
        model.addAttribute("measurements", measurements);
        model.addAttribute("size", measurements.size());
        model.addAttribute("title", "Measurements");
        model.addAttribute("measurementNew", new Measurement());
        return "measurements";
    }

    @PostMapping("/add-measurement")
    public String add(@ModelAttribute("measurementNew") Measurement measurement, RedirectAttributes attributes) {
        try {
            measurementService.save(measurement);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/measurements";
    }

    @RequestMapping(value = "/findMeasurementById/{measurementId}", method = {RequestMethod.GET})
    @ResponseBody  //tra ve json
    public Optional<Measurement> getById(@PathVariable(name = "measurementId") Long measurementId){
        return measurementService.findById(measurementId);
    }

    @GetMapping("/update-measurement")
    public String update(Measurement measurement, RedirectAttributes attributes){
        try {
            measurementService.update(measurement);
            attributes.addFlashAttribute("success", "Updated successfully");
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }

        return "redirect:/measurements";
    }

    @RequestMapping(value = "/delete-measurement/{measurementId}", method = {RequestMethod.GET})
    public String delete(@PathVariable(name = "measurementId") Long measurementId, RedirectAttributes attributes){
        try {
            measurementService.deleteById(measurementId);
            attributes.addFlashAttribute("success", "Deleted successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/measurements";
    }

    @RequestMapping(value = "/enable-measurement/{measurementId}", method = {RequestMethod.GET})
    public String enable(@PathVariable(name = "measurementId") Long measurementId, RedirectAttributes attributes){
        try {
            measurementService.enableById(measurementId);
            attributes.addFlashAttribute("success", "Enabled successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/measurements";
    }
}
