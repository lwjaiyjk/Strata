/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.swap;

import static com.opengamma.strata.basics.PayReceive.PAY;
import static com.opengamma.strata.basics.PayReceive.RECEIVE;
import static com.opengamma.strata.basics.currency.Currency.GBP;
import static com.opengamma.strata.basics.currency.Currency.USD;
import static com.opengamma.strata.basics.date.DayCounts.ACT_365F;
import static com.opengamma.strata.collect.TestHelper.date;
import static com.opengamma.strata.product.swap.SwapLegType.FIXED;
import static com.opengamma.strata.product.swap.SwapLegType.IBOR;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.PayReceive;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.index.Index;
import com.opengamma.strata.basics.market.ReferenceData;
import com.opengamma.strata.product.rate.FixedRateObservation;

/**
 * Mock.
 */
@BeanDefinition
public final class MockSwapLeg implements SwapLeg, ImmutableBean, Serializable {

  public static final SwapLeg MOCK_GBP1 = new MockSwapLeg(FIXED, PAY, date(2012, 1, 15), date(2012, 8, 15), GBP);
  public static final ResolvedSwapLeg MOCK_EXPANDED_GBP1 = ResolvedSwapLeg.builder()
      .type(FIXED)
      .payReceive(PAY)
      .paymentPeriods(RatePaymentPeriod.builder()
          .paymentDate(date(2012, 8, 15))
          .accrualPeriods(RateAccrualPeriod.builder()
              .startDate(date(2012, 1, 15))
              .endDate(date(2012, 8, 15))
              .rateObservation(FixedRateObservation.of(0.012d))
              .build())
          .dayCount(ACT_365F)
          .notional(1_000_000d)
          .currency(GBP)
          .build())
      .build();
  public static final SwapLeg MOCK_GBP2 = new MockSwapLeg(FIXED, PAY, date(2012, 1, 15), date(2012, 6, 15), GBP);
  public static final SwapLeg MOCK_USD1 = new MockSwapLeg(IBOR, RECEIVE, date(2012, 1, 15), date(2012, 8, 15), USD);
  public static final ResolvedSwapLeg MOCK_EXPANDED_USD1 = ResolvedSwapLeg.builder()
      .type(IBOR)
      .payReceive(RECEIVE)
      .paymentPeriods(RatePaymentPeriod.builder()
          .paymentDate(date(2012, 8, 15))
          .accrualPeriods(RateAccrualPeriod.builder()
              .startDate(date(2012, 1, 15))
              .endDate(date(2012, 8, 15))
              .rateObservation(FixedRateObservation.of(0.012d))
              .build())
          .dayCount(ACT_365F)
          .notional(1_000_000d)
          .currency(USD)
          .build())
      .build();

  @PropertyDefinition(overrideGet = true)
  private final SwapLegType type;
  @PropertyDefinition(overrideGet = true)
  private final PayReceive payReceive;
  @PropertyDefinition(overrideGet = true)
  private final LocalDate startDate;
  @PropertyDefinition(overrideGet = true)
  private final LocalDate endDate;
  @PropertyDefinition(overrideGet = true)
  private final Currency currency;

  public static MockSwapLeg of(
      SwapLegType type,
      PayReceive payReceive,
      LocalDate startDate,
      LocalDate endDate,
      Currency currency) {
    return new MockSwapLeg(type, payReceive, startDate, endDate, currency);
  }

  @Override
  public void collectIndices(ImmutableSet.Builder<Index> builder) {
  }

  @Override
  public ResolvedSwapLeg resolve(ReferenceData refData) {
    if (this == MOCK_GBP1) {
      return MOCK_EXPANDED_GBP1;
    }
    if (this == MOCK_USD1) {
      return MOCK_EXPANDED_USD1;
    }
    throw new UnsupportedOperationException();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MockSwapLeg}.
   * @return the meta-bean, not null
   */
  public static MockSwapLeg.Meta meta() {
    return MockSwapLeg.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(MockSwapLeg.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static MockSwapLeg.Builder builder() {
    return new MockSwapLeg.Builder();
  }

  private MockSwapLeg(
      SwapLegType type,
      PayReceive payReceive,
      LocalDate startDate,
      LocalDate endDate,
      Currency currency) {
    this.type = type;
    this.payReceive = payReceive;
    this.startDate = startDate;
    this.endDate = endDate;
    this.currency = currency;
  }

  @Override
  public MockSwapLeg.Meta metaBean() {
    return MockSwapLeg.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the type.
   * @return the value of the property
   */
  @Override
  public SwapLegType getType() {
    return type;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the payReceive.
   * @return the value of the property
   */
  @Override
  public PayReceive getPayReceive() {
    return payReceive;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the startDate.
   * @return the value of the property
   */
  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the endDate.
   * @return the value of the property
   */
  @Override
  public LocalDate getEndDate() {
    return endDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property
   */
  @Override
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MockSwapLeg other = (MockSwapLeg) obj;
      return JodaBeanUtils.equal(type, other.type) &&
          JodaBeanUtils.equal(payReceive, other.payReceive) &&
          JodaBeanUtils.equal(startDate, other.startDate) &&
          JodaBeanUtils.equal(endDate, other.endDate) &&
          JodaBeanUtils.equal(currency, other.currency);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(type);
    hash = hash * 31 + JodaBeanUtils.hashCode(payReceive);
    hash = hash * 31 + JodaBeanUtils.hashCode(startDate);
    hash = hash * 31 + JodaBeanUtils.hashCode(endDate);
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("MockSwapLeg{");
    buf.append("type").append('=').append(type).append(',').append(' ');
    buf.append("payReceive").append('=').append(payReceive).append(',').append(' ');
    buf.append("startDate").append('=').append(startDate).append(',').append(' ');
    buf.append("endDate").append('=').append(endDate).append(',').append(' ');
    buf.append("currency").append('=').append(JodaBeanUtils.toString(currency));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MockSwapLeg}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code type} property.
     */
    private final MetaProperty<SwapLegType> type = DirectMetaProperty.ofImmutable(
        this, "type", MockSwapLeg.class, SwapLegType.class);
    /**
     * The meta-property for the {@code payReceive} property.
     */
    private final MetaProperty<PayReceive> payReceive = DirectMetaProperty.ofImmutable(
        this, "payReceive", MockSwapLeg.class, PayReceive.class);
    /**
     * The meta-property for the {@code startDate} property.
     */
    private final MetaProperty<LocalDate> startDate = DirectMetaProperty.ofImmutable(
        this, "startDate", MockSwapLeg.class, LocalDate.class);
    /**
     * The meta-property for the {@code endDate} property.
     */
    private final MetaProperty<LocalDate> endDate = DirectMetaProperty.ofImmutable(
        this, "endDate", MockSwapLeg.class, LocalDate.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", MockSwapLeg.class, Currency.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "type",
        "payReceive",
        "startDate",
        "endDate",
        "currency");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3575610:  // type
          return type;
        case -885469925:  // payReceive
          return payReceive;
        case -2129778896:  // startDate
          return startDate;
        case -1607727319:  // endDate
          return endDate;
        case 575402001:  // currency
          return currency;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public MockSwapLeg.Builder builder() {
      return new MockSwapLeg.Builder();
    }

    @Override
    public Class<? extends MockSwapLeg> beanType() {
      return MockSwapLeg.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code type} property.
     * @return the meta-property, not null
     */
    public MetaProperty<SwapLegType> type() {
      return type;
    }

    /**
     * The meta-property for the {@code payReceive} property.
     * @return the meta-property, not null
     */
    public MetaProperty<PayReceive> payReceive() {
      return payReceive;
    }

    /**
     * The meta-property for the {@code startDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> startDate() {
      return startDate;
    }

    /**
     * The meta-property for the {@code endDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> endDate() {
      return endDate;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3575610:  // type
          return ((MockSwapLeg) bean).getType();
        case -885469925:  // payReceive
          return ((MockSwapLeg) bean).getPayReceive();
        case -2129778896:  // startDate
          return ((MockSwapLeg) bean).getStartDate();
        case -1607727319:  // endDate
          return ((MockSwapLeg) bean).getEndDate();
        case 575402001:  // currency
          return ((MockSwapLeg) bean).getCurrency();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code MockSwapLeg}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<MockSwapLeg> {

    private SwapLegType type;
    private PayReceive payReceive;
    private LocalDate startDate;
    private LocalDate endDate;
    private Currency currency;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(MockSwapLeg beanToCopy) {
      this.type = beanToCopy.getType();
      this.payReceive = beanToCopy.getPayReceive();
      this.startDate = beanToCopy.getStartDate();
      this.endDate = beanToCopy.getEndDate();
      this.currency = beanToCopy.getCurrency();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3575610:  // type
          return type;
        case -885469925:  // payReceive
          return payReceive;
        case -2129778896:  // startDate
          return startDate;
        case -1607727319:  // endDate
          return endDate;
        case 575402001:  // currency
          return currency;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3575610:  // type
          this.type = (SwapLegType) newValue;
          break;
        case -885469925:  // payReceive
          this.payReceive = (PayReceive) newValue;
          break;
        case -2129778896:  // startDate
          this.startDate = (LocalDate) newValue;
          break;
        case -1607727319:  // endDate
          this.endDate = (LocalDate) newValue;
          break;
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public MockSwapLeg build() {
      return new MockSwapLeg(
          type,
          payReceive,
          startDate,
          endDate,
          currency);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the type.
     * @param type  the new value
     * @return this, for chaining, not null
     */
    public Builder type(SwapLegType type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the payReceive.
     * @param payReceive  the new value
     * @return this, for chaining, not null
     */
    public Builder payReceive(PayReceive payReceive) {
      this.payReceive = payReceive;
      return this;
    }

    /**
     * Sets the startDate.
     * @param startDate  the new value
     * @return this, for chaining, not null
     */
    public Builder startDate(LocalDate startDate) {
      this.startDate = startDate;
      return this;
    }

    /**
     * Sets the endDate.
     * @param endDate  the new value
     * @return this, for chaining, not null
     */
    public Builder endDate(LocalDate endDate) {
      this.endDate = endDate;
      return this;
    }

    /**
     * Sets the currency.
     * @param currency  the new value
     * @return this, for chaining, not null
     */
    public Builder currency(Currency currency) {
      this.currency = currency;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("MockSwapLeg.Builder{");
      buf.append("type").append('=').append(JodaBeanUtils.toString(type)).append(',').append(' ');
      buf.append("payReceive").append('=').append(JodaBeanUtils.toString(payReceive)).append(',').append(' ');
      buf.append("startDate").append('=').append(JodaBeanUtils.toString(startDate)).append(',').append(' ');
      buf.append("endDate").append('=').append(JodaBeanUtils.toString(endDate)).append(',').append(' ');
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
