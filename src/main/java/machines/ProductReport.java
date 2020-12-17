package machines;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "ProductReport")
@XmlRootElement(name = "productReport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductReport {
	
	@EmbeddedId
	ProductReportKey id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("report_id")
	@XmlTransient
	private DailyReport report;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("product_id")
	@XmlTransient
	private Product product;

	private int quantity;
	
	private BigDecimal price;
	
	public ProductReport() {

	}
	
	public ProductReport(DailyReport report, Product product) {
		this.report = report;
		this.product = product;
		id = new ProductReportKey(report.getId(), product.getId());
	}
	
	public ProductReportKey getId() {
		return id;
	}

	public void setId(ProductReportKey id) {
		this.id = id;
	}
	
	public DailyReport getReport() {
		return report;
	}

	public void setReport(DailyReport report) {
		this.report = report;
	}

	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public void unlink() {
		report = null;
		product = null;
	}
	
}
