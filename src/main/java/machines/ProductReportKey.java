package machines;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductReportKey implements Serializable {

	@Column(name = "report_id")
	private Long reportId;

	@Column(name = "product_id")
	private Long productId;

	private ProductReportKey() {
		
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
