package souk.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString(includeFieldNames=true)

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	@Setter
	private long Id;
	@Getter
	@Setter
	private String Email;
	@Getter
	@Setter
	private String Phone;
	@Getter
	@Setter
	private String Password;
	@Getter
	@Setter
	private String Ip;
	@Getter
	@Setter
	private String Name;
	@Getter
	@Setter
	private String LastName;
	
	public User(String Email,String Phone,String Name,String LastName,String Ip) {
		this.Email=Email;
		this.Phone=Phone;
		this.Name=Name;
		this.LastName=LastName;
		this.Ip=Ip;
	}
	
	

}
