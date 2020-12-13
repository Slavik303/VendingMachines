package machines;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement(name = "productReportKey")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductReportKey implements Serializable {

	private static final long serialVersionUID = 6120100114717352895L;

	@Column(name = "report_id")
	private Long reportId;

	@Column(name = "product_id")
	private Long productId;

	public ProductReportKey() {
		
	}
	
	public ProductReportKey(Long productId) {
		this.productId = productId;
	}

	public ProductReportKey(Long reportId, Long productId) {
		this.reportId = reportId;
		this.productId = productId;
	}
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		ProductReportKey that = (ProductReportKey) o;
		return Objects.equals(reportId, that.reportId) &&
				Objects.equals(productId, that.productId);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(reportId, productId);
	}
	
}
