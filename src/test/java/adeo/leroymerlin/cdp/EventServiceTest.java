package adeo.leroymerlin.cdp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    
	@InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void testDeleteEvent() {
        // Créez un ID fictif pour l'événement à supprimer
        Long eventId = 1L;

        // Supposons que la méthode eventRepository.delete retourne void (ne fait rien)
        doNothing().when(eventRepository).delete(eventId);

        // Appelez la méthode delete de EventService
        eventService.delete(eventId);

        // Vérifiez si eventRepository.delete a été appelé avec le bon ID
        verify(eventRepository, times(1)).delete(eventId);
    }

    @Test
    public void testGetFilteredEvents() {
        List<Event> events = initEvents();

        // Configurez le mock eventRepository pour renvoyer les données fictives
        when(eventRepository.findAllBy()).thenReturn(events);

        // Appelez la méthode getFilteredEvents avec une requête fictive
        List<Event> filteredEvents = eventService.getFilteredEvents("sem");

        // Vérifiez le résultat
        assertEquals(2, filteredEvents.size()); // Deux événements doivent correspondre
        
        Event filteredEvent = filteredEvents.get(0);
        assertEquals("Event 1 [1]", filteredEvent.getTitle()); // Titre filtré attendu
        assertEquals(1, filteredEvent.getBands().size()); // Un seul groupe doit correspondre
        
        Band filteredBand = filteredEvent.getBands().iterator().next();
        assertEquals("Band 1 [2]", filteredBand.getName()); // Nom du groupe filtré attendu
    }

    /**
     * 
     * Créez des données fictives d'événements, de groupes et de membres
     * 
     * @return List<Event>
     */
	private List<Event> initEvents() {
		// Init event1
        Event event1 = new Event();
        event1.setTitle("Event 1");
        
        Band band1 = new Band();
        band1.setName("Band 1");
        
        Member member11 = new Member("Bassem1");
        Member member12 = new Member("Bassem2");
        
        Set<Member> membersBand1 = new HashSet<>();
        membersBand1.add(member11);
        membersBand1.add(member12);
        band1.setMembers(membersBand1);
        
        Set<Band> bandsEvent1 = new HashSet<>();
        bandsEvent1.add(band1);
        event1.setBands(bandsEvent1);

		// Init event2
        Event event2 = new Event();
        event2.setTitle("Event 2");
        
        Band band2 = new Band();
        band2.setName("Band 2");
        
        Member member21 = new Member("Alice");
        Member member22 = new Member("Semantha");
        
        Set<Member> membersBand2 = new HashSet<>();
        membersBand2.add(member21);
        membersBand2.add(member22);
        band2.setMembers(membersBand2);
        
        Set<Band> bandsEvent2 = new HashSet<>();
        bandsEvent2.add(band2);
        event2.setBands(bandsEvent2);

        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
		return events;
	}
}
