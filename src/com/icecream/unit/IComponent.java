package com.icecream.unit;

import com.icecream.exception.MissingComponentException;

public interface IComponent {
	/**
	 * Activate this component.
	 * @throws <code>MissingComponentException</code> when an essential <code>IComponent</code> has not been connected.
	 */
	public void activate() throws MissingComponentException;
	
	/**
	 * Validate if this component has all the essential components connected.
	 * @return True if validation is successful.
	 * @throws <code>MissingComponentException</code> when an essential <code>IComponent</code> has not been connected.
	 */
	public boolean validate() throws MissingComponentException;

	/**
	 * Initialize this component.
	 */
	public void initialize();

	/**
	 * Deactivate this component.
	 */
	public void deactivate();
	
	/**
	 * Connect this component with the given component.
	 * @param component The <code>IComponent</code> to connect to.
	 */
	public void connect(IComponent component);
	
	/**
	 * Check if this component is currently active.
	 * @return True if this <code>IComponent</code> is active. False otherwise.
	 */
	public boolean isActive();
}
