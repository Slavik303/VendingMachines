package machines;

import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;

@Entity(name = "Error")
public class Error {
	
	@Id
	@GeneratedValue
	private int id;

	@Enumerated(EnumType.STRING)
	private ErrorType type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	private String description;
	
	private int importance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private DailyReport report; 
	
	public Error() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public DailyReport getReport() {
		return report;
	}

	public void setReport(DailyReport report) {
		this.report = report;
	}
	
}
