package souk.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString(includeFieldNames=true)
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	@Setter
	private long id;
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private String phone;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String ip;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String last_name;
	
	public User(String Email,String Phone,String Name,String LastName,String Ip) {
		this.email=Email;
		this.phone=Phone;
		this.name=Name;
		this.last_name=LastName;
		this.ip=Ip;
	}
	
	

}
