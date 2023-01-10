package org.moosetechnology.verveineC.plugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	@SuppressWarnings("unused")
	static private BundleContext context;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		Activator.context = null;
		System.out.println("VerveineC done");
	}

}
