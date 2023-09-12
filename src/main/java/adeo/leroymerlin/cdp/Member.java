package adeo.leroymerlin.cdp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Member {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;
    
    public Member(String name) {
		this.name = name;
	}
}
