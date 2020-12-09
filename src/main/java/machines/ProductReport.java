package machines;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity(name = "ProductReport")
public class ProductReport {
	
	@EmbeddedId
	ProductReportKey id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("report_id")
	private DailyReport report;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("product_id")
	private Product product;

	private int quantity;
	
	private BigDecimal price;
	
	private ProductReport() {

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
	
}
