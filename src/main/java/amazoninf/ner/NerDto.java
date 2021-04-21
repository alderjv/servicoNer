package amazoninf.ner;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NerDto {

	public NerDto() {

	}

	String param;
	String tagsNer;

	public NerDto(String param) {
		this.param = param;
	}

	@JsonProperty("retorno1")
	public String getRetorno1() {
		return "valor retorno 1";
	}

	@JsonProperty("retorno2")
	public String getRetorno2() {
		return "valor retorno 2";
	}

	@JsonProperty("tagsNer")
	public String getRetornoParametro() {
		return tagsNer;
	}

	public final void setTagsNer(String tagsNer) {
		this.tagsNer = tagsNer;
	}

}
