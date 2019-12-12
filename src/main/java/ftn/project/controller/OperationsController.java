package ftn.project.controller;

import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ftn.project.dto.OperationDto;
import ftn.project.model.Appointment;
import ftn.project.services.AppointmentService;
import ftn.project.services.OperationService;
import ftn.project.services.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/operations")
public class OperationsController {

	private final UserService userService;
	
	private final AppointmentService appointmentService;
	
	private final OperationService operationService;

	@GetMapping
	public String getPage(Model model) throws MailException, MessagingException {
		model.addAttribute("doctorsList",userService.allUserByRole("doktor"));
		Set<Appointment> appointments=appointmentService.allAppointments();
		model.addAttribute("appointmentsList",appointmentService.allAppointments());
		
		return "operations";
		
	}
	
	@PostMapping("/sendmail")
	public String getIds(@RequestBody OperationDto operationDto ) {
		operationService.scheduleOperation();
		return "operations";
	}
}