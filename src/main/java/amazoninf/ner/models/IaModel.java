package amazoninf.ner.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "IAModel")
public class IaModel implements Serializable {

	private static final long serialVersionUID = -5754550900916562914L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "IAModelID", columnDefinition = "uniqueidentifier")
	private String iaModelID;

	@Column(name = "AnalysisID", nullable = true)
	private String analysisID;

	//@Column(name = "TrainingParametersID", nullable = true)
	//private String trainingParametersID;

	//@Column(name = "CorpusID", nullable = false)
	//private String corpusID;

	@NotNull(message = "ModifiedDate is required")
	@Column(name = "ModifiedDate", nullable = true)
	private Date modifiedDate;

	@Column(name = "DictionaryItensNotTraining", nullable = true)
	private String dictionaryItensNotTraining;

	@Column(name = "ModelName", nullable = true)
	private String modelName;

	@Column(name = "MD5Model", nullable = true)
	private String md5Model;

	@Lob
	@Column(name = "ModelBin", nullable = true)
	private byte[] modelBin;

	@Column(name = "LemmatizeBeforeApplyingModel", nullable = true)
	private Boolean lemmatizeBeforeApplyingModel;

	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CorpusID", referencedColumnName = "CorpusID")
	private Corpus corpus;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TrainingParametersID", referencedColumnName = "TrainingParametersID")
	private TrainingParameters trainingParameters;
}
