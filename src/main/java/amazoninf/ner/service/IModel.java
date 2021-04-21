package amazoninf.ner.service;

import java.util.List;
import java.util.Optional;

import amazoninf.ner.models.IaModel;

public interface IModel {
	List<IaModel> getAllModels();

	Optional<IaModel> findById(String id);

	IaModel save(IaModel std);

	void deleteById(String id);
}
