package fordsoft.tech.mydesk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Odofin Timothy
 *
 */

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String fullname;

	
	private LocalDateTime datecreated;
	
	private String city;
	
	private String country;
	
	private String email;
	
	private String password;

	@Override
	public String toString() {
		return "User [id=" + id + ", Fullname=" + fullname + ", lastName=" +  ", Date created=" + datecreated + ", email="
				+ email + "]";
	}

	
}
