package it.xpug.woodysmart.orders;

public class Order {
	private String orderCode;
	private String articleCode;
	private String address;
	private boolean isShipped;

	public Order(String orderCode, String articleCode, String address) {
	    this.orderCode = orderCode;
	    this.articleCode = articleCode;
	    this.address = address;
    }

	public Order(String orderCode) {
		this(orderCode, null, null);
    }

	@Override
	public String toString() {
	    return String.format("Order(%s,%s,%s)", orderCode, articleCode, address);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
	    return this.toString().equals(other.toString());
	}

	@Override
	public int hashCode() {
	    return toString().hashCode();
	}

	public boolean isShipped() {
	    return isShipped;
    }

	public void ship() {
		isShipped = true;
    }

	public String getCode() {
	    return this.orderCode;
    }

	public String getArticleCode() {
	    return this.articleCode;
    }

	public String getAddress() {
	    return this.address;
    }

}