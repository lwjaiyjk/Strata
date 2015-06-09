/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.function.marketdata.scenarios.curves;

import static com.opengamma.strata.collect.Guavate.toImmutableList;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.basics.value.ValueAdjustment;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.engine.marketdata.scenarios.Perturbation;
import com.opengamma.strata.market.curve.Curve;
import com.opengamma.strata.market.curve.CurveParameterMetadata;
import com.opengamma.strata.market.curve.NodalCurve;
import com.opengamma.strata.market.curve.ShiftType;

/**
 * A perturbation that applies different shifts to specific points on a curve.
 * <p>
 * This class contains a set of shifts, each one associated with a different point on the curve.
 * The shift is linked to the point using an identifier which must be equal to the identifier of
 * the curve node metadata.
 * <p>
 * A shift is not applied if there is no point on the curve with a matching identifier.
 * <p>
 * This shift can only be applied to an instance of {@link NodalCurve} which contains parameter
 * metadata. The {@link #apply} method will throw an exception for any other curves.
 *
 * @see CurveParameterMetadata#getIdentifier()
 */
@BeanDefinition(builderScope = "private")
public final class CurvePointShift implements Perturbation<Curve>, ImmutableBean {

  /**
   * The type of shift applied to the curve rates.
   */
  @PropertyDefinition(validate = "notNull")
  private final ShiftType shiftType;
  /**
   * The shift to apply to the rates.
   * The key is typically the node {@linkplain CurveParameterMetadata#getIdentifier() identifier}.
   * The key may also be the node {@linkplain CurveParameterMetadata#getLabel() label}.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableMap<Object, Double> shifts;

  //-------------------------------------------------------------------------
  /**
   * Returns a new mutable builder for building instances of {@code CurvePointShift}.
   *
   * @param shiftType  the type of shift to apply to the rates
   * @return a new mutable builder for building instances of {@code CurvePointShift}
   */
  public static CurvePointShiftBuilder builder(ShiftType shiftType) {
    return new CurvePointShiftBuilder(shiftType);
  }

  /**
   * Package-private constructor used by {@link CurvePointShiftBuilder}.
   *
   * @param shiftType  the type of shift to apply to the rates
   * @param shifts  the shift amounts, keyed by the node {@link CurveParameterMetadata#getIdentifier identifier}
   */
  @ImmutableConstructor
  CurvePointShift(ShiftType shiftType, Map<Object, Double> shifts) {
    this.shiftType = ArgChecker.notNull(shiftType, "shiftType");
    this.shifts = ImmutableMap.copyOf(shifts);
  }

  //-------------------------------------------------------------------------
  @Override
  public Curve apply(Curve curve) {
    Optional<List<CurveParameterMetadata>> optionalNodeMetadata = curve.getMetadata().getParameters();

    // If there is no metadata for the curve nodes there is no way to find the nodes and apply the shifts
    if (!optionalNodeMetadata.isPresent()) {
      throw new IllegalArgumentException(
          Messages.format(
              "Unable to apply point shifts to curve '{}' because it has no parameter metadata",
              curve.getName()));
    }
    if (!(curve instanceof NodalCurve)) {
      throw new IllegalArgumentException(
          Messages.format(
              "Point shifts can only be applied to NodalCurve implementations, the class of curve '{}' is {}",
              curve.getName(),
              curve.getClass().getName()));
    }
    List<ValueAdjustment> valueAdjustments = optionalNodeMetadata.get().stream()
        .map(this::valueAdjustmentForNode)
        .collect(toImmutableList());

    return ((NodalCurve) curve).shiftedBy(valueAdjustments);
  }

  // find the adjustment applicable for the node
  private ValueAdjustment valueAdjustmentForNode(CurveParameterMetadata node) {
    Double shiftAmount = shiftAmountForNode(node);
    return shiftAmount != null ? shiftType.toValueAdjustment(shiftAmount) : ValueAdjustment.NONE;
  }

  // find the shift amount applicable for the node, null if none
  private Double shiftAmountForNode(CurveParameterMetadata node) {
    Double shiftAmount = shifts.get(node.getIdentifier());
    return shiftAmount != null ? shiftAmount : shifts.get(node.getLabel());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CurvePointShift}.
   * @return the meta-bean, not null
   */
  public static CurvePointShift.Meta meta() {
    return CurvePointShift.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CurvePointShift.Meta.INSTANCE);
  }

  @Override
  public CurvePointShift.Meta metaBean() {
    return CurvePointShift.Meta.INSTANCE;
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
   * Gets the type of shift applied to the curve rates.
   * @return the value of the property, not null
   */
  public ShiftType getShiftType() {
    return shiftType;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the shift to apply to the rates.
   * The key is typically the node {@linkplain CurveParameterMetadata#getIdentifier() identifier}.
   * The key may also be the node {@linkplain CurveParameterMetadata#getLabel() label}.
   * @return the value of the property, not null
   */
  public ImmutableMap<Object, Double> getShifts() {
    return shifts;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CurvePointShift other = (CurvePointShift) obj;
      return JodaBeanUtils.equal(getShiftType(), other.getShiftType()) &&
          JodaBeanUtils.equal(getShifts(), other.getShifts());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getShiftType());
    hash = hash * 31 + JodaBeanUtils.hashCode(getShifts());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("CurvePointShift{");
    buf.append("shiftType").append('=').append(getShiftType()).append(',').append(' ');
    buf.append("shifts").append('=').append(JodaBeanUtils.toString(getShifts()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CurvePointShift}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code shiftType} property.
     */
    private final MetaProperty<ShiftType> shiftType = DirectMetaProperty.ofImmutable(
        this, "shiftType", CurvePointShift.class, ShiftType.class);
    /**
     * The meta-property for the {@code shifts} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Object, Double>> shifts = DirectMetaProperty.ofImmutable(
        this, "shifts", CurvePointShift.class, (Class) ImmutableMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "shiftType",
        "shifts");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return shiftType;
        case -903338959:  // shifts
          return shifts;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends CurvePointShift> builder() {
      return new CurvePointShift.Builder();
    }

    @Override
    public Class<? extends CurvePointShift> beanType() {
      return CurvePointShift.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code shiftType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ShiftType> shiftType() {
      return shiftType;
    }

    /**
     * The meta-property for the {@code shifts} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Object, Double>> shifts() {
      return shifts;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return ((CurvePointShift) bean).getShiftType();
        case -903338959:  // shifts
          return ((CurvePointShift) bean).getShifts();
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
   * The bean-builder for {@code CurvePointShift}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<CurvePointShift> {

    private ShiftType shiftType;
    private Map<Object, Double> shifts = ImmutableMap.of();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return shiftType;
        case -903338959:  // shifts
          return shifts;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          this.shiftType = (ShiftType) newValue;
          break;
        case -903338959:  // shifts
          this.shifts = (Map<Object, Double>) newValue;
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
    public CurvePointShift build() {
      return new CurvePointShift(
          shiftType,
          shifts);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("CurvePointShift.Builder{");
      buf.append("shiftType").append('=').append(JodaBeanUtils.toString(shiftType)).append(',').append(' ');
      buf.append("shifts").append('=').append(JodaBeanUtils.toString(shifts));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}