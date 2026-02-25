package souk.demo.controller;
import souk.demo.entity.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import souk.demo.repository.*;

@RestController
@RequestMapping(path = "/User")

public class Cuser {
	
	
	private final UserInterface UserInterface;
	Cuser(UserInterface repo){
		this.UserInterface=repo;
	}
	@GetMapping
	public List<User> Index() {
				
		return this.UserInterface.findAll();}

}
