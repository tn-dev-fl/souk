package souk.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ahmed")

public class Cuser {
	@GetMapping
	public String Index() {return "/ahmed ba3basou endpoint working";}

}
