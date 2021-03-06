package ftn.project.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="VremePregleda",nullable=true)
	private String vremePregleda;
	
	@Column(name = "Username", nullable = false)
	private String username;

	@Column(name = "Password", nullable = false)
	private String password;

	@Column(name = "Name", nullable = true)
	private String name;

	
	@Column(name = "Surname", nullable = true)
	private String surname;

	@Column(name = "Adress", nullable = true)
	private String adress;

	@Column(name = "City", nullable = true)
	private String city;

	@Column(name="DatumPregleda" , nullable = true)
	private String datumPregleda;
	
	@Column(name="TipPregleda" , nullable=true)
	private String tipPregleda;
	
	
	@Column(name="PocetakGodisnjeg",nullable=true)
	private String  pocetakGodisnjeg;
	
	@Column(name="KrajGodisnjeg", nullable = true) 
	private String  krajGodisnjeg;
	
	
	@Column(name = "Country", nullable = true)
	private String country;

	@Column(name = "Phone", nullable = true)
	private String phone;

	@Column(name = "Email", nullable = true)
	private String email;

	@Column(name = "Enabled")
	private boolean enabled;
	
	@Column(name="Clinic")
    private Long clinic;

	// polje za pacijenta
	@Column(name = "InsuranceNumber", nullable = true)
	private String insuranceNumber;

	// polja za doktora
	@Column(name = "Biography", nullable = true)
	private String biography;

	@Column(name = "Mark", nullable = true)
	private String mark;
	

	

	//polje za doktora,sestru i administratora klinike
	@Column(name="PrviLogin" , nullable=true)
	private Boolean prviLogin;
	
	@Column(name="PomocnaSifra" , nullable=true)
	private String pomocnaSifra;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinTable(name="AdministratorsClinics",joinColumns = {
//	@JoinColumn(name = "admin_id", referencedColumnName = "id" ) }, inverseJoinColumns = {
//			@JoinColumn(name = "clinic_id", referencedColumnName = "id") })
//	private Clinic clinic;

	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "PacientRecords", joinColumns = {
			@JoinColumn(name = "pacient_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "records_id", referencedColumnName = "id") })
	private MedicalRecord medicalRecord;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private Set<Authority> authorities;

	private String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	
	/*public void setGodisnji (Set<String> godisnji) {
		pomocnaGodisnji.clear();
		for(String god : godisnji) {
			pomocnaGodisnji.add(god);
			
		}
	}*/
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
}
