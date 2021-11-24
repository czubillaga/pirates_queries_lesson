package com.example.codeclan.pirateservice;

import com.example.codeclan.pirateservice.models.Pirate;
import com.example.codeclan.pirateservice.models.Raid;
import com.example.codeclan.pirateservice.models.Ship;
import com.example.codeclan.pirateservice.repository.PirateRepository;
import com.example.codeclan.pirateservice.repository.RaidRepository;
import com.example.codeclan.pirateservice.repository.ShipRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PirateserviceApplicationTests {

	@Autowired
	PirateRepository pirateRepository;

	@Autowired
	ShipRepository shipRepository;

	@Autowired
	RaidRepository raidRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createPirateAndShipThenSave(){

		Ship dutchman = new Ship("The Flying Dutchman");
		shipRepository.save(dutchman);
		Pirate jack = new Pirate("jack", "sparrow", 32, dutchman);
		pirateRepository.save(jack);
	}

	@Test
	public void createPirateAndRaidThenSave(){
		Ship dutchman = new Ship("The Flying Dutchman");
		shipRepository.save(dutchman);
		Pirate jack = new Pirate("jack", "sparrow", 32, dutchman);
		pirateRepository.save(jack);

		Raid raid = new Raid("Tortuga", 100);
		raidRepository.save(raid);

		jack.addRaid(raid);
		raid.addPirate(jack);
		raidRepository.save(raid);
		
	}

	@Test
	public void canFindPiratesOver30() {
		List<Pirate> found = pirateRepository.findByAgeGreaterThan(30);
		assertEquals(9, found.size());
	}

	@Test
	public void canFindRaidByLocation() {
		List<Raid> found = raidRepository.findRaidByLocation("Treasure Island");
		assertEquals("Treasure Island", found.get(0).getLocation());
	}

	@Test
	public void canFindPirateByFirstName() {
		Pirate found = pirateRepository.findPirateByFirstName("John");
		assertEquals("John", found.getFirstName());
	}

	@Test
	public void canFindPiratesByRaidId() {
		List<Pirate> foundPirates = pirateRepository.findByRaidsId(1L);
		assertEquals(1, foundPirates.size());
		assertEquals("Jack", foundPirates.get(0).getFirstName());
	}

	@Test
	public void canFindShipsByPirateFirstName() {
		List<Ship> foundShips = shipRepository.findShipsByPiratesFirstName("John");
		assertEquals(1, foundShips.size());
		assertEquals("The Flying Dutchman", foundShips.get(0).getName());
	}

	@Test
	public void canFindRaidsByShip() {
		Ship foundShip = shipRepository.getOne(3L);
		List<Raid> foundRaids = raidRepository.findByPiratesShip(foundShip);
		assertEquals(2, foundRaids.size());
	}
}
