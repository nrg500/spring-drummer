package nl.berwout;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrumPatternRepository extends JpaRepository<DrumPattern, Long> {

  DrumPattern findByName(String name);
}
