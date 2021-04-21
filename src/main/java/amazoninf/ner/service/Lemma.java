package amazoninf.ner.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import amazoninf.ner.models.IaModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.tokenize.SimpleTokenizer;

@Service
public class Lemma {
	private static final String NAO_ENCONTRADO = "O";

	public IaModel lemmatizer(IaModel model) throws Exception {
		SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;

		StringBuffer corpus = new StringBuffer(model.getCorpus().getCorpusText());
		String[] tokens = tokenizer.tokenize(corpus.toString());
		InputStream dictLemmatizer = getClass().getClassLoader().getResourceAsStream("pt-br-lemmatizer.dict.txt");
		DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		String tagV[] = { "V" };
		String tagN[] = { "N" };
		HashMap<String, String> lemas = new HashMap<String, String>();
		// StringBuilder sbRetorno = new StringBuilder("");
		// Matcher matcher = null;

		for (String token : tokens) {
			String[] result = lemmatizer.lemmatize(new String[] { token }, tagN);
			// sbRetorno.delete(0, sbRetorno.length());

			if (result[0].equalsIgnoreCase(NAO_ENCONTRADO)) {
				result = lemmatizer.lemmatize(new String[] { token }, tagV);

				if (result[0].equalsIgnoreCase(NAO_ENCONTRADO)) {
					result[0] = token;
				}
			}

			if (!token.equalsIgnoreCase(result[0])) {
				/*
				 * posIni = corpus.indexOf(token); posFim = posIni + token.length();
				 * 
				 * corpus.replace(posIni, posFim, result[0]);
				 */
				if (!lemas.containsKey(token)) {
					lemas.put(token, result[0]);

					/*
					 * matcher = Pattern.compile(BOUNDARY + token + BOUNDARY).matcher(corpus);
					 * 
					 * while (matcher.find()) { matcher.appendReplacement(sbRetorno, result[0]); }
					 * matcher.appendTail(sbRetorno); corpus = new StringBuilder(sbRetorno);
					 */
				}

			}

		}

		System.gc();

		/*
		 * String teste = lemas.entrySet().stream() .map(entry -> (Function<String,
		 * String>) data -> data.replaceAll(entry.getKey(), entry.getValue()))
		 * .reduce(Function.identity(), Function::compose).apply(corpus.toString());
		 */

		// Percorre as ocorrencias dos lemas e realiza a substituição no corpus

		/*
		 * while (it.hasNext()) { Map.Entry<String, String> obj = it.next();
		 * 
		 * int index = corpus.indexOf(obj.getKey()); while (index != -1) {
		 * corpus.replace(index, index + obj.getKey().length(), obj.getValue()); index
		 * += obj.getValue().length(); index = corpus.indexOf(obj.getKey(), index); } }
		 */

		// model.setCorpusTextLemma(aplicaLemmaThread(lemas, sbRetorno));
		model.getCorpus().setCorpusTextLemma(aplicaLemmaParalelo(lemas, corpus));

		System.out.println("############ fim " + model.getAnalysisID());
		System.gc();

		return model;
	}

	private String aplicaLemmaParalelo(HashMap<String, String> lemas, StringBuffer corpus) {

		lemas.entrySet().parallelStream().forEach(e -> process(e, corpus));

		/*
		 * ExecutorService executor = Executors.newFixedThreadPool(10);
		 * 
		 * lemas.entrySet().stream().map(i -> CompletableFuture.supplyAsync(() ->
		 * process(i, corpus), executor)).collect(
		 * .map(CompletableFuture::join).collect(Collectors.toList());
		 */

		return corpus.toString();
	}

	private Object process(Entry<String, String> lema, StringBuffer corpus) {

		int index = corpus.indexOf(lema.getKey());

		while (index != -1) {
			corpus.replace(index, index + lema.getKey().length(), lema.getValue());
			index += lema.getValue().length();
			index = corpus.indexOf(lema.getKey(), index);
		}

		return corpus;
	}

	private String aplicaLemmaThread(HashMap<String, String> lemas, StringBuffer corpus) {
		Iterator<Map.Entry<String, String>> it = lemas.entrySet().iterator();

		Runnable runnable = () -> {

			while (it.hasNext()) {
				Map.Entry<String, String> obj = it.next();

				int index = corpus.indexOf(obj.getKey());

				while (index != -1) {
					corpus.replace(index, index + obj.getKey().length(), obj.getValue());
					index += obj.getValue().length();
					index = corpus.indexOf(obj.getKey(), index);
				}
			}

		};

		Thread thread = new Thread(runnable);
		thread.start();

		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return corpus.toString();
	}
	
}
