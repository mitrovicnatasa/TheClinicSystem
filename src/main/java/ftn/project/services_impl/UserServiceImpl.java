package ftn.project.services_impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.project.dto.UserDto;
import ftn.project.mapper.UserMapper;
import ftn.project.model.Clinic;
import ftn.project.model.User;
import ftn.project.model.VerificationToken;
import ftn.project.repository.ClinicRepository;
import ftn.project.repository.UserRepository;
import ftn.project.repository.VerificationTokenRepository;
import ftn.project.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	// user service
	@Autowired
	private UserRepository userRepository;

	// dodala
	
	private final ClinicRepository clinicRepository;

	@Autowired
	private UserMapper userMapper;

	// spring security
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	// email generate
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	public void createUser(UserDto userDto) {
		userRepository.save(userMapper.DtoToUser(userDto));
	}

	@Override
	public void deleteUser(Long idDto) {
		userRepository.deleteById(idDto);
	}

	public UserDto getUserById(Long idDto) {

		return userMapper.UserToDto(userRepository.findAllById(idDto));

	}

	public Set<UserDto> allUsers() {
		return userMapper.UserToDtoSet(userRepository.findAll());
	}

	@Override
	public Set<UserDto> allMedicalStaff() {
		// TODO Auto-generated method stub
		return userMapper.UserToDtoSet(userRepository.findAllByRole("doktor"));
	}

	@Override

	public Set<UserDto> allNurse() {

		return userMapper.UserToDtoSet(userRepository.findAllByRole("med. sestra"));
	}

	@Override
	public void editUser(Long idDto) {
		// TODO Auto-generated method stub

	}

	public UserDto getUserById(String username) {

		// TODO Auto-generated method stub
		return null;
	}

	public UserDto getUserByRole(String role) {
		return userMapper.UserToDto(userRepository.findByRole(role));
	}

	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. can't change Password!");

			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		User user = (User) loadUserByUsername(username);

		// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
		// ne zelimo da u bazi cuvamo lozinke u plain text formatu
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

	}

	@Override
	public void createVerificationToken(User user, String token) {
		// TODO Auto-generated method stub

	}

	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String verificationToken) {
		return verificationTokenRepository.findByToken(verificationToken).getUser();

	}

	@Override
	public String autentification(UserDto userDto) {
		for (User u : userRepository.findAll()) {
			if (u.getUsername().equals(userDto.getUsernameDto()) && u.getPassword().equals(userDto.getPasswordDto())) {
				if (u.getRole().equals("Clinic Centar Administrator")) {
					return "administrators";
				} else if (u.getRole().equals("Clinic Administrator")) {
					return "doctors";
				} else if (u.getRole().equals("pacijent")) {
					return "patientHome";
				} else if (u.getRole().equals("med. sestra")) {
					return "nurseProfile";
				}

			}
		}
		return "badUser";
	}

	@Override
	public Set<UserDto> allUserByRole(String role) {
		Set<User> users = userRepository.findAllByRole(role);

		return userMapper.UserToDtoSet(users);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public UserDto getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<UserDto> searchPatient(String nameDto, String surnameDto, String insuranceNumberDto) {
		// TODO Auto-generated method stub
		Set<UserDto> pacijenti = null;
		if (nameDto != "" & surnameDto != "" & insuranceNumberDto != "") {
			Set<User> pacijentiTemp = userRepository.findByNameAndSurnameAndInsuranceNumber(nameDto, surnameDto,
					insuranceNumberDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
			return pacijenti;
		} else if (nameDto != "" & surnameDto != "" & insuranceNumberDto == "") {
			Set<User> pacijentiTemp = userRepository.findByNameAndSurname(nameDto, surnameDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
			return pacijenti;
		}

		else if (nameDto != "" & surnameDto == "" & insuranceNumberDto != "") {
			Set<User> pacijentiTemp = userRepository.findByNameAndInsuranceNumber(nameDto, insuranceNumberDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
			return pacijenti;
		} else if (nameDto == "" & surnameDto != "" & insuranceNumberDto != "") {
			Set<User> pacijentiTemp = userRepository.findBySurnameAndInsuranceNumber(surnameDto, insuranceNumberDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
			return pacijenti;
		} else if (nameDto == "" & surnameDto == "" & insuranceNumberDto == "") {

			Set<User> pacijentiTemp = userRepository.findAllByRole("pacijent");
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
			return pacijenti;
		} else if (nameDto != "" & surnameDto == "" & insuranceNumberDto == "") {
			Set<User> pacijentiTemp = userRepository.findByName(nameDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
		} else if (nameDto == "" & surnameDto != "" & insuranceNumberDto == "") {
			Set<User> pacijentiTemp = userRepository.findBySurname(surnameDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
		} else if (nameDto == "" & surnameDto == "" & insuranceNumberDto != "") {
			Set<User> pacijentiTemp = userRepository.findByInsuranceNumber(insuranceNumberDto);
			pacijenti = userMapper.UserToDtoSet(pacijentiTemp);
		}
		return pacijenti;
	}

	public Set<UserDto> searchDoctor(String nameDto, String surnameDto, String markDto) {

		Set<UserDto> doktori = null;
		if (nameDto != "" & surnameDto == "" & markDto == "") {
			Set<User> doktoriTemp = userRepository.findByName(nameDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto == "" & surnameDto != "" & markDto == "") {
			Set<User> doktoriTemp = userRepository.findBySurname(surnameDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto == "" & surnameDto == "" & markDto != "") {
			Set<User> doktoriTemp = userRepository.findByMark(markDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto != "" & surnameDto != "" & markDto == "") {
			Set<User> doktoriTemp = userRepository.findByNameAndSurname(nameDto, surnameDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto != "" & surnameDto == "" & markDto != "") {
			Set<User> doktoriTemp = userRepository.findByNameAndMark(nameDto, markDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto == "" & surnameDto != "" & markDto != "") {
			Set<User> doktoriTemp = userRepository.findBySurnameAndMark(surnameDto, markDto);
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;

		} else if (nameDto == "" & surnameDto == "" & markDto == "") {
			Set<User> doktoriTemp = userRepository.findAllByRole("doktor");
			doktori = userMapper.UserToDtoSet(doktoriTemp);
			return doktori;
		}
		return doktori;
	}

	@Override
	public UserDto getUserProfile(String username) {
		return userMapper.UserToDto(userRepository.findByUsername(username));
	}



}
