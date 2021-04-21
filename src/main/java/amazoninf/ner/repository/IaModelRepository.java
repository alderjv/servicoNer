package amazoninf.ner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amazoninf.ner.models.IaModel;

@Repository
public interface IaModelRepository extends JpaRepository<IaModel, String> {

	Optional<IaModel> findById(String id);
}
