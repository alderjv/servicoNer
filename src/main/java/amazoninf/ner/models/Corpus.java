package amazoninf.ner.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Corpus")
public class Corpus implements Serializable {

	private static final long serialVersionUID = 5765299337731991979L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "CorpusID", columnDefinition = "uniqueidentifier")
	private String CorpusID;

	@Column(name = "CorpusName", nullable = false)
	private String corpusName;

	@Column(name = "CorpusText", nullable = false)
	private String corpusText;

	@Column(name = "CorpusTextLemma", nullable = false)
	private String corpusTextLemma;
}
