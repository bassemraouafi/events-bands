package adeo.leroymerlin.cdp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	private static final Logger logger = LoggerFactory.getLogger(EventService.class);

	private final EventRepository eventRepository;

	@Autowired
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public List<Event> getEvents() {
		logger.info("Recherche de tous les événements");
		
		return eventRepository.findAllBy();
	}

	/**
	 * Supprime un événement en fonction de son ID.
	 *
	 * @param id L'ID de l'événement à supprimer.
	 */
	@Transactional
	public void delete(Long id) {
		logger.info("Suppression de l'événement avec l'ID : {}", id);

		eventRepository.delete(id);

		logger.info("Événement supprimé avec succès avec l'ID : {}", id);
	}

	/**
	 * Filtre les événements en fonction d'une requête donnée.
	 *
	 * @param query La requête de filtrage.
	 * @return Une liste d'événements filtrés.
	 */
	public List<Event> getFilteredEvents(String query) {
		logger.info("Chercher les événements qui correspondent à la requête : {}", query);

		// Récupère tous les événements
		List<Event> events = eventRepository.findAllBy();

		// Initialise une liste pour stocker les événements filtrés
		List<Event> filteredEvents = new ArrayList<>();

		// Parcours tous les événements
		for (Event event : events) {

			// Initialise un ensemble pour stocker les groupes correspondants
			Set<Band> matchingBands = new HashSet<>();

			// Parcours tous les groupes de chaque événement
			for (Band band : event.getBands()) {

				// Initialise un ensemble pour stocker les membres correspondants
				Set<Member> matchingMembers = new HashSet<>();

				// Parcours tous les membres de chaque groupe
				for (Member member : band.getMembers()) {
					// Vérifie si le nom du membre correspond à la requête
					if (member.getName().toLowerCase().contains(query.toLowerCase())) {
						// Ajoute le membre correspondant à l'ensemble
						matchingMembers.add(member);
					}
				}

				// Si des membres correspondent, crée un groupe filtré
				if (!matchingMembers.isEmpty()) {
					Band filteredBand = new Band();
					filteredBand.setName(band.getName() + " [" + matchingMembers.size() + "]");
					filteredBand.setMembers(matchingMembers);
					matchingBands.add(filteredBand);
				}
			}

			// Si des groupes correspondent, crée un événement filtré
			if (!matchingBands.isEmpty()) {
				Event filteredEvent = new Event();
				filteredEvent.setTitle(event.getTitle() + " [" + matchingBands.size() + "]");
				filteredEvent.setImgUrl(event.getImgUrl());
				filteredEvent.setBands(matchingBands);
				filteredEvents.add(filteredEvent);
			}
		}

		logger.info("Résultat de la recherche de la requête - nombre : {}", filteredEvents.size());

		// Retourne la liste des événements filtrés
		return filteredEvents;
	}

	/**
	 * Met à jour les étoiles et le commentaire d'un événement.
	 *
	 * @param id    L'ID de l'événement à mettre à jour.
	 * @param event L'événement mis à jour.
	 */
	@Transactional
	public void updateStars(Long id, Event event) {
		if (event == null) {
			logger.error("Evénement null!");
			return;
		}

		logger.info("Mise à jour de l'événement avec l'ID : {}", id);
		
		Event existingEvent = eventRepository.findById(id);
		if (existingEvent == null) {
			logger.error("Pas d'évenement correspondant à l'ID : {}", id);
			return;
		}

		existingEvent.setNbStars(event.getNbStars());
		existingEvent.setComment(event.getComment());
		eventRepository.save(existingEvent);
		
		logger.info("Mise à jour de l'événement réussie avec l'ID : {}", id);
	}
}
