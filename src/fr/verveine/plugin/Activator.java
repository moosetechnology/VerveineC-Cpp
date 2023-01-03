package fr.verveine.plugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	static private BundleContext context;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Activating VerveineC");
		Activator.context = bundleContext;
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		Activator.context = null;
		System.out.println("VerveineC done");
	}

}
