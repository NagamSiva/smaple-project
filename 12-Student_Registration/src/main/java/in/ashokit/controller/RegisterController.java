package in.ashokit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.Student;
import in.ashokit.entity.StudentEntity;
import in.ashokit.repository.StudentRepo;

@Controller
public class RegisterController {
	
	@Autowired
	private StudentRepo repo;
	
	@GetMapping("/")
	public String loadJsp(Model model) {
	
		loadData(model);
		
		return "index";
	}

	private void loadData(Model model) {
		List<String> timingsList = new ArrayList<>();
		timingsList.add("morning");
		timingsList.add("noon");
		timingsList.add("Evening");
		
		List<String> coursesList = new ArrayList<>();
		coursesList.add("Python");
		coursesList.add("Java");
		coursesList.add("Devops");
		coursesList.add("AWS");
		
		model.addAttribute("courses", coursesList);
		model.addAttribute("timings", timingsList);
		model.addAttribute("student", new Student());
	}
	
	@PostMapping("/save")
	public String saveJsp(Student s, Model model) {
	
		loadData(model);
		
		StudentEntity entity= new StudentEntity();
		BeanUtils.copyProperties(s, entity);
		entity.setTimings(Arrays.toString(s.getTimings()));
		repo.save(entity);
		
		model.addAttribute("msg", "Record saved");
		System.out.println(s);
		
		return "index";
	}

	@GetMapping("/viewStudent")
	public String showStudent(Model model) {
		List<StudentEntity> list = repo.findAll();
		model.addAttribute("students", list);
		return "data";
	}
}
