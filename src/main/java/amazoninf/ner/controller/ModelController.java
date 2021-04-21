package amazoninf.ner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import amazoninf.ner.models.IaModel;
import amazoninf.ner.service.IaModelService;

@RestController
@EnableAutoConfiguration
public class ModelController {

	@Autowired(required = true)
	private IaModelService iaModelService;

	/*
	 * @RequestMapping(value = "/model/training", method = RequestMethod.POST,
	 * consumes = "application/json") public NerDto
	 * recuperarDto(@RequestBody(required = false) IaModel data) {
	 * 
	 * NerDto dto = new NerDto(); dto.setTagsNer(nerNegocio.training(data)); return
	 * dto; }
	 */

	// public ResponseEntity<IaModel> training(@PathVariable(value = "id") String
	// id) {
	// @PostMapping(value="/model/training")

	@RequestMapping(value = "/model/training", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<IaModel> training(@RequestBody IaModel std) {

		IaModel model = iaModelService.treinar(std.getIaModelID());

		return ResponseEntity.ok().body(std);
	}

	@RequestMapping(value = "/model/lemmatizer", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<IaModel> lemmatizer(@RequestBody IaModel std) {

		IaModel model = iaModelService.lemmatizer(std.getIaModelID());

		return ResponseEntity.ok().body(std);
	}

	@RequestMapping(value = "/model/gravarbin", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<IaModel> gravarBin(@RequestBody IaModel std) {

		IaModel model = iaModelService.gravarBin(std.getIaModelID());

		return ResponseEntity.ok().body(std);
	}

}
