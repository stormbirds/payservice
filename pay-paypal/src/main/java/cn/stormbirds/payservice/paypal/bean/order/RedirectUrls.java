package cn.stormbirds.payservice.paypal.bean.order;


import com.alibaba.fastjson.annotation.JSONField;

public class RedirectUrls{
	/**
	 * Url where the payer would be redirected to after approving the payment. **Required for PayPal account payments.**
	 */
	@JSONField(name = "return_url")
	private String returnUrl;
	/**
	 * Url where the payer would be redirected to after canceling the payment. **Required for PayPal account payments.**
	 */
	@JSONField(name = "cancel_url")
	private String cancelUrl;

	/**
	 * Default Constructor
	 */
	public RedirectUrls() {
	}

	/**
	 * Url where the payer would be redirected to after approving the payment. **Required for PayPal account payments.**
	 */
	@SuppressWarnings("all")
	public String getReturnUrl() {
		return this.returnUrl;
	}

	/**
	 * Url where the payer would be redirected to after canceling the payment. **Required for PayPal account payments.**
	 */
	@SuppressWarnings("all")
	public String getCancelUrl() {
		return this.cancelUrl;
	}

	/**
	 * Url where the payer would be redirected to after approving the payment. **Required for PayPal account payments.**
	 * @return this
	 */
	@SuppressWarnings("all")
	public RedirectUrls setReturnUrl(final String returnUrl) {
		this.returnUrl = returnUrl;
		return this;
	}

	/**
	 * Url where the payer would be redirected to after canceling the payment. **Required for PayPal account payments.**
	 * @return this
	 */
	@SuppressWarnings("all")
	public RedirectUrls setCancelUrl(final String cancelUrl) {
		this.cancelUrl = cancelUrl;
		return this;
	}

	@Override
	@SuppressWarnings("all")
	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof RedirectUrls)) return false;
		final RedirectUrls other = (RedirectUrls) o;
		if (!other.canEqual((Object) this)) return false;
		if (!super.equals(o)) return false;
		final Object this$returnUrl = this.getReturnUrl();
		final Object other$returnUrl = other.getReturnUrl();
		if (this$returnUrl == null ? other$returnUrl != null : !this$returnUrl.equals(other$returnUrl)) return false;
		final Object this$cancelUrl = this.getCancelUrl();
		final Object other$cancelUrl = other.getCancelUrl();
		if (this$cancelUrl == null ? other$cancelUrl != null : !this$cancelUrl.equals(other$cancelUrl)) return false;
		return true;
	}

	@SuppressWarnings("all")
	protected boolean canEqual(final Object other) {
		return other instanceof RedirectUrls;
	}

	@Override
	@SuppressWarnings("all")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		result = result * PRIME + super.hashCode();
		final Object $returnUrl = this.getReturnUrl();
		result = result * PRIME + ($returnUrl == null ? 43 : $returnUrl.hashCode());
		final Object $cancelUrl = this.getCancelUrl();
		result = result * PRIME + ($cancelUrl == null ? 43 : $cancelUrl.hashCode());
		return result;
	}
}
