package amazoninf.ner.models;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "TrainingParameters")
public class TrainingParameters implements Serializable{
	
	private static final long serialVersionUID = 2399945111063825513L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "TrainingParametersID", columnDefinition = "uniqueidentifier")
	private String trainingParametersID;
	
	//@Column(name = "AlgorithmID", nullable = false)
	//private String AlgorithmID;
	
	@Column(name = "Iterations", nullable = false)
	private Integer Iterations;
	
	@Column(name = "Cutoff", nullable = false)
	private Integer Cutoff;
	
	@Column(name = "Threads", nullable = false)
	private Integer threads;
	
	@Column(name = "ApplyLemmatizer", nullable = false)
	private Boolean applyLemmatizer;
	
	@Column(name = "RemoveStopWords", nullable = false)
	private Boolean removeStopWords;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AlgorithmID", referencedColumnName = "AlgorithmID")
	private Algorithm algorithm;
	
	

}
