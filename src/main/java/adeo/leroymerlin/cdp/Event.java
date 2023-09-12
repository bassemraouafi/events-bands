package adeo.leroymerlin.cdp;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Event {
	
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    private String imgUrl;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<Band> bands;

    private Integer nbStars;

    private String comment;

    
}
