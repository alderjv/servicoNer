package amazoninf.ner.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amazoninf.ner.models.IaModel;
import amazoninf.ner.repository.IaModelRepository;

@Service
public class IaModelService implements IModel {

	@Autowired(required = true)
	private IaModelRepository iaModelRepository;

	@Autowired
	private Treino treino;

	@Autowired
	private Lemma lemma;

	@Override
	public List<IaModel> getAllModels() {
		// TODO Auto-generated method stub
		return iaModelRepository.findAll();
	}

	@Override
	public Optional<IaModel> findById(String id) {
		// TODO Auto-generated method stub
		return iaModelRepository.findById(id);
	}

	@Override
	public IaModel save(IaModel std) {
		// TODO Auto-generated method stub
		return iaModelRepository.save(std);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		iaModelRepository.deleteById(id);
	}

	public IaModel lemmatizer(String id) {
		IaModel model = findById(id).get();

		try {
			model = lemma.lemmatizer(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.setModifiedDate(new Date());

		save(model);

		return model;
	}

	public IaModel treinar(String id) {
		IaModel model = findById(id).get();

		model = treino.treinar(model);

		save(model);

		return model;
	}
	
	public IaModel gravarBin(String id) {
		IaModel model = findById(id).get();

		model = treino.gravarBin(model);

		save(model);

		return model;
	}

}
