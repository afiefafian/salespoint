package org.salespointframework.core.money;

import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.salespointframework.core.quantity.Metric;
import org.salespointframework.core.quantity.Quantity;
import org.salespointframework.core.quantity.rounding.RoundDownStrategy;
import org.salespointframework.core.quantity.rounding.RoundingStrategy;
import org.salespointframework.util.Objects;

/**
 * The <code>Money</code> class represents the Euro currency. A suitable
 * <code>Metric</code> and <code>RoundingStrategy</code> is created
 * automatically. A user-defined <code>Metric</code> can be supplied. The
 * default <code>Metric</code> is Euro. Arithmetic functions are used from the
 * <code>Quantity</code> class. The internal representation uses four digits
 * after the decimal separator.
 * 
 * @author hannesweisbach
 * 
 */
// @Entity
public class Money extends Quantity {
	// @Embedded
	public static final Metric EURO = new Metric("Euro", "€", "");
	public static final RoundingStrategy MONETARY_ROUNDING = new RoundDownStrategy(
			4);
	public static final Money ZERO = new Money(0);

	/**
	 * Parameterless constructor, do not use. This constructor is required by
	 * the persistence layer and may not be used by application developers.
	 */
	/*
	 * @Deprecated protected Money() { }
	 */
	/**
	 * Creates a new <code>Money</code> instance from an amount and a metric.
	 * 
	 * @param amount
	 *            amount of the money represented by this instance
	 * @param metric
	 *            metric (currency) to be used for this instance.
	 */
	public Money(BigDecimal amount, Metric metric) {
		super(Objects.requireNonNull(amount, "amount"), Objects.requireNonNull(
				metric, "metric"), MONETARY_ROUNDING);
	}

	/**
	 * Creates a new <code>Money</code> instance from an amount and a metric.
	 * 
	 * @param amount
	 *            amount of the money represented by this instance
	 * @param metric
	 *            metric (currency) to be used for this instance.
	 */
	public Money(int amount, Metric metric) {
		this(BigDecimal.valueOf(amount), Objects.requireNonNull(metric,
				"metric"));
	}

	/**
	 * Creates a new <code>Money</code> instance from an amount and a metric.
	 * 
	 * @param amount
	 *            amount of the money represented by this instance
	 * @param metric
	 *            metric (currency) to be used for this instance.
	 */
	public Money(double amount, Metric metric) {
		this(BigDecimal.valueOf(amount), Objects.requireNonNull(metric,
				"metric"));
		// this(BigDecimal.valueOf(amount), metric);
	}

	/**
	 * Constructs a new <code>Money</code> object from a <code>BigDecimal</code>
	 * .
	 * 
	 * @param amount
	 *            the amount of money represented by <code>this</code>
	 */
	public Money(BigDecimal amount) {
		this(Objects.requireNonNull(amount, "amount"), EURO);
	}

	/**
	 * Constructs a new <code>Money</code> object from a <code>long</code> value
	 * 
	 * @param amount
	 *            the amount of money represented by <code>this</code>
	 */
	public Money(long amount) {
		this(BigDecimal.valueOf(amount));
	}

	/**
	 * Constructs a new <code>Money</code> object from a <code>double</code>
	 * value
	 * 
	 * @param amount
	 *            the amount of money represented by <code>this</code>¸
	 */
	public Money(double amount) {
		this(BigDecimal.valueOf(amount));
	}

	/**
	 * String representation of the monetary value represented by
	 * <code>this</code>. The numerical value is truncated after two digits
	 * after the decimal separator and followed by a whitespace and the symbol
	 * stored in <code>metric</code>.
	 */
	public String toString() {
		return amount.setScale(2) + " " + metric.getSymbol();
	}
}
