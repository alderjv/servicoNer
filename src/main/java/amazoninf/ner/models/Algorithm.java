package amazoninf.ner.models;

import java.io.Serializable;

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
@Table(name = "Algorithm")
public class Algorithm implements Serializable {

	private static final long serialVersionUID = -4027490038449243042L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "AlgorithmID", columnDefinition = "uniqueidentifier")
	private String algorithmID;

	@Column(name = "AlgorithmName", nullable = false)
	private String algorithmName;

	@Column(name = "AlgorithmDescription", nullable = false)
	private String algorithmDescription;
}
