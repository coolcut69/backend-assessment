package nl.wolfpackit.backend_assessment.persistence;

import nl.wolfpackit.backend_assessment.model.Gender;
import nl.wolfpackit.backend_assessment.model.PackEntity;
import nl.wolfpackit.backend_assessment.model.WolfEntity;
import nl.wolfpackit.backend_assessment.web.DateUtilities;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(WolfRepository wolfRepository, PackRepository packRepository) {
        return args -> {
            WolfEntity chrisBorghmans = new WolfEntity();
            chrisBorghmans.setBirthDate(DateUtilities.createDate(1978, 8, 25));
            chrisBorghmans.setGender(Gender.MALE);
            chrisBorghmans.setName("Chris Borghmans");
            log.info("Preloading " + wolfRepository.save(chrisBorghmans));

            PackEntity pack = new PackEntity();
            pack.setName("Best pack ever");
//            pack.getWolves().add(chrisBorghmans);
            log.info("Preloading " + packRepository.save(pack));
        };
    }

}
