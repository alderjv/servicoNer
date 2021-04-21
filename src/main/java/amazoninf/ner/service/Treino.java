package amazoninf.ner.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import amazoninf.ner.models.IaModel;
import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

@Service
public class Treino {

	public IaModel treinar(IaModel model) {

		ObjectStream sampleStream = null;

		// 01 Ler dados de treinamento
		if (model.getTrainingParameters().getApplyLemmatizer()) {
			sampleStream = obterCorpusArquivo(model.getCorpus().getCorpusTextLemma());
		} else {
			sampleStream = obterCorpusArquivo(model.getCorpus().getCorpusText());
		}

		// 02 Configurar os parâmetros
		// Ajustes de acordo com o algorítimo
		// https://github.com/apache/opennlp/tree/master/opennlp-tools/lang/ml
		TrainingParameters params = new TrainingParameters();

		// params.put(TrainingParameters.ALGORITHM_PARAM, GISTrainer.MAXENT_VALUE);
		// params.put(TrainingParameters.ITERATIONS_PARAM, 70);
		// params.put(TrainingParameters.CUTOFF_PARAM, 5);
		// params.put(TrainingParameters.THREADS_PARAM, 4);

		params.put(TrainingParameters.ALGORITHM_PARAM, model.getTrainingParameters().getAlgorithm().getAlgorithmName());
		params.put(TrainingParameters.ITERATIONS_PARAM, model.getTrainingParameters().getIterations());
		params.put(TrainingParameters.CUTOFF_PARAM, model.getTrainingParameters().getCutoff());
		params.put(TrainingParameters.THREADS_PARAM, model.getTrainingParameters().getThreads());

		// 03 Treinando o modelo
		TokenNameFinderModel nameFinderModel = null;
		try {
			nameFinderModel = NameFinderME.train("pt-BR", null, sampleStream, params,
					TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		File file = null;
		try {
			file = File.createTempFile("bin", null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//FileOutputStream outputStream = null;
		//FileInputStream fileIn = null;

		try {
			//outputStream = new FileOutputStream(file);
			//nameFinderModel.serialize(outputStream);
			nameFinderModel.serialize(file);
			//fileIn = new FileInputStream(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String md5 = null;
		byte[] blob = null;

		try {
			md5 = DigestUtils.md5DigestAsHex(FileUtils.readFileToByteArray(file));
			//blob = BlobProxy.generateProxy(fileIn, 7).getBinaryStream().readAllBytes();
			blob = FileUtils.readFileToByteArray(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.setModelName(file.getName());
		model.setModelBin(blob);
		model.setMd5Model(md5);
		model.setModifiedDate(new Date());

		return model;
	}

	private ObjectStream obterCorpusArquivo(String corpusTxt) {
		InputStreamFactory in = null;

		File file = null;
		try {
			file = File.createTempFile("corpus", null);
			Files.write(file.toPath(), corpusTxt.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in = new MarkableFileInputStreamFactory(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}

		ObjectStream sampleStream = null;
		try {

			sampleStream = new NameSampleDataStream(new PlainTextByLineStream(in, StandardCharsets.ISO_8859_1));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		file.deleteOnExit();
		return sampleStream;
	}
	
	public IaModel gravarBin(IaModel model) {

		File file = null;
		try {
			file = new File("C:\\Users\\Administrator\\AppData\\Local\\Temp\\2\\bin8251646983421679805.tmp");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//FileOutputStream outputStream = null;
		FileInputStream fileIn = null;
		
		try {
			//outputStream = new FileOutputStream(file);
			//nameFinderModel.serialize(outputStream);
			fileIn = new FileInputStream(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		String md5 = null;
		byte[] blob = null;
		String teste = null;
		
		try {
			md5 = DigestUtils.md5DigestAsHex(fileIn);
			//blob = BlobProxy.generateProxy(fileIn, fileIn.getChannel().size()).getBinaryStream().readAllBytes();
			blob = FileUtils.readFileToByteArray(file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// model.setModelName("");
		model.setModelBin(blob);
		model.setMd5Model(md5);
		model.setModifiedDate(new Date());

		return model;
	}
}
