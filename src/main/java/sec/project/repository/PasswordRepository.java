package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Password;

public interface PasswordRepository extends JpaRepository<Password, Long> {

}
